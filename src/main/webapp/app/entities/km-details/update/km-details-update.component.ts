import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IKmMaster } from 'app/entities/km-master/km-master.model';
import { KmMasterService } from 'app/entities/km-master/service/km-master.service';
import { IKmDetails } from '../km-details.model';
import { KmDetailsService } from '../service/km-details.service';
import { KmDetailsFormService, KmDetailsFormGroup } from './km-details-form.service';

@Component({
  standalone: true,
  selector: 'jhi-km-details-update',
  templateUrl: './km-details-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class KmDetailsUpdateComponent implements OnInit {
  isSaving = false;
  kmDetails: IKmDetails | null = null;

  kmMastersCollection: IKmMaster[] = [];

  editForm: KmDetailsFormGroup = this.kmDetailsFormService.createKmDetailsFormGroup();

  constructor(
    protected kmDetailsService: KmDetailsService,
    protected kmDetailsFormService: KmDetailsFormService,
    protected kmMasterService: KmMasterService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareKmMaster = (o1: IKmMaster | null, o2: IKmMaster | null): boolean => this.kmMasterService.compareKmMaster(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ kmDetails }) => {
      this.kmDetails = kmDetails;
      if (kmDetails) {
        this.updateForm(kmDetails);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const kmDetails = this.kmDetailsFormService.getKmDetails(this.editForm);
    if (kmDetails.id !== null) {
      this.subscribeToSaveResponse(this.kmDetailsService.update(kmDetails));
    } else {
      this.subscribeToSaveResponse(this.kmDetailsService.create(kmDetails));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IKmDetails>>): void {
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

  protected updateForm(kmDetails: IKmDetails): void {
    this.kmDetails = kmDetails;
    this.kmDetailsFormService.resetForm(this.editForm, kmDetails);

    this.kmMastersCollection = this.kmMasterService.addKmMasterToCollectionIfMissing<IKmMaster>(
      this.kmMastersCollection,
      kmDetails.kmMaster,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.kmMasterService
      .query({ 'kmDetailsId.specified': 'false' })
      .pipe(map((res: HttpResponse<IKmMaster[]>) => res.body ?? []))
      .pipe(
        map((kmMasters: IKmMaster[]) =>
          this.kmMasterService.addKmMasterToCollectionIfMissing<IKmMaster>(kmMasters, this.kmDetails?.kmMaster),
        ),
      )
      .subscribe((kmMasters: IKmMaster[]) => (this.kmMastersCollection = kmMasters));
  }
}
