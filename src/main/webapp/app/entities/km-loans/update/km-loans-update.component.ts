import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { KmLoansFormService, KmLoansFormGroup } from './km-loans-form.service';
import { IKmLoans } from '../km-loans.model';
import { KmLoansService } from '../service/km-loans.service';
import { ICropMaster } from 'app/entities/crop-master/crop-master.model';
import { CropMasterService } from 'app/entities/crop-master/service/crop-master.service';
import { IKmDetails } from 'app/entities/km-details/km-details.model';
import { KmDetailsService } from 'app/entities/km-details/service/km-details.service';

@Component({
  selector: 'jhi-km-loans-update',
  templateUrl: './km-loans-update.component.html',
})
export class KmLoansUpdateComponent implements OnInit {
  isSaving = false;
  kmLoans: IKmLoans | null = null;

  cropMastersSharedCollection: ICropMaster[] = [];
  kmDetailsSharedCollection: IKmDetails[] = [];

  editForm: KmLoansFormGroup = this.kmLoansFormService.createKmLoansFormGroup();

  constructor(
    protected kmLoansService: KmLoansService,
    protected kmLoansFormService: KmLoansFormService,
    protected cropMasterService: CropMasterService,
    protected kmDetailsService: KmDetailsService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareCropMaster = (o1: ICropMaster | null, o2: ICropMaster | null): boolean => this.cropMasterService.compareCropMaster(o1, o2);

  compareKmDetails = (o1: IKmDetails | null, o2: IKmDetails | null): boolean => this.kmDetailsService.compareKmDetails(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ kmLoans }) => {
      this.kmLoans = kmLoans;
      if (kmLoans) {
        this.updateForm(kmLoans);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const kmLoans = this.kmLoansFormService.getKmLoans(this.editForm);
    if (kmLoans.id !== null) {
      this.subscribeToSaveResponse(this.kmLoansService.update(kmLoans));
    } else {
      this.subscribeToSaveResponse(this.kmLoansService.create(kmLoans));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IKmLoans>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(kmLoans: IKmLoans): void {
    this.kmLoans = kmLoans;
    this.kmLoansFormService.resetForm(this.editForm, kmLoans);

    this.cropMastersSharedCollection = this.cropMasterService.addCropMasterToCollectionIfMissing<ICropMaster>(
      this.cropMastersSharedCollection,
      kmLoans.cropMaster
    );
    this.kmDetailsSharedCollection = this.kmDetailsService.addKmDetailsToCollectionIfMissing<IKmDetails>(
      this.kmDetailsSharedCollection,
      kmLoans.kmDetails
    );
  }

  protected loadRelationshipsOptions(): void {
    this.cropMasterService
      .query()
      .pipe(map((res: HttpResponse<ICropMaster[]>) => res.body ?? []))
      .pipe(
        map((cropMasters: ICropMaster[]) =>
          this.cropMasterService.addCropMasterToCollectionIfMissing<ICropMaster>(cropMasters, this.kmLoans?.cropMaster)
        )
      )
      .subscribe((cropMasters: ICropMaster[]) => (this.cropMastersSharedCollection = cropMasters));

    this.kmDetailsService
      .query()
      .pipe(map((res: HttpResponse<IKmDetails[]>) => res.body ?? []))
      .pipe(
        map((kmDetails: IKmDetails[]) =>
          this.kmDetailsService.addKmDetailsToCollectionIfMissing<IKmDetails>(kmDetails, this.kmLoans?.kmDetails)
        )
      )
      .subscribe((kmDetails: IKmDetails[]) => (this.kmDetailsSharedCollection = kmDetails));
  }
}
