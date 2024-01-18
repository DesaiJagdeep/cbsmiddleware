import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { KmCropsFormService, KmCropsFormGroup } from './km-crops-form.service';
import { IKmCrops } from '../km-crops.model';
import { KmCropsService } from '../service/km-crops.service';
import { ICropMaster } from 'app/entities/crop-master/crop-master.model';
import { CropMasterService } from 'app/entities/crop-master/service/crop-master.service';
import { IKmDetails } from 'app/entities/km-details/km-details.model';
import { KmDetailsService } from 'app/entities/km-details/service/km-details.service';

@Component({
  selector: 'jhi-km-crops-update',
  templateUrl: './km-crops-update.component.html',
})
export class KmCropsUpdateComponent implements OnInit {
  isSaving = false;
  kmCrops: IKmCrops | null = null;

  cropMastersSharedCollection: ICropMaster[] = [];
  kmDetailsSharedCollection: IKmDetails[] = [];

  editForm: KmCropsFormGroup = this.kmCropsFormService.createKmCropsFormGroup();

  constructor(
    protected kmCropsService: KmCropsService,
    protected kmCropsFormService: KmCropsFormService,
    protected cropMasterService: CropMasterService,
    protected kmDetailsService: KmDetailsService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareCropMaster = (o1: ICropMaster | null, o2: ICropMaster | null): boolean => this.cropMasterService.compareCropMaster(o1, o2);

  compareKmDetails = (o1: IKmDetails | null, o2: IKmDetails | null): boolean => this.kmDetailsService.compareKmDetails(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ kmCrops }) => {
      this.kmCrops = kmCrops;
      if (kmCrops) {
        this.updateForm(kmCrops);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const kmCrops = this.kmCropsFormService.getKmCrops(this.editForm);
    if (kmCrops.id !== null) {
      this.subscribeToSaveResponse(this.kmCropsService.update(kmCrops));
    } else {
      this.subscribeToSaveResponse(this.kmCropsService.create(kmCrops));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IKmCrops>>): void {
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

  protected updateForm(kmCrops: IKmCrops): void {
    this.kmCrops = kmCrops;
    this.kmCropsFormService.resetForm(this.editForm, kmCrops);

    this.cropMastersSharedCollection = this.cropMasterService.addCropMasterToCollectionIfMissing<ICropMaster>(
      this.cropMastersSharedCollection,
      kmCrops.cropMaster
    );
    this.kmDetailsSharedCollection = this.kmDetailsService.addKmDetailsToCollectionIfMissing<IKmDetails>(
      this.kmDetailsSharedCollection,
      kmCrops.kmDetails
    );
  }

  protected loadRelationshipsOptions(): void {
    this.cropMasterService
      .query()
      .pipe(map((res: HttpResponse<ICropMaster[]>) => res.body ?? []))
      .pipe(
        map((cropMasters: ICropMaster[]) =>
          this.cropMasterService.addCropMasterToCollectionIfMissing<ICropMaster>(cropMasters, this.kmCrops?.cropMaster)
        )
      )
      .subscribe((cropMasters: ICropMaster[]) => (this.cropMastersSharedCollection = cropMasters));

    this.kmDetailsService
      .query()
      .pipe(map((res: HttpResponse<IKmDetails[]>) => res.body ?? []))
      .pipe(
        map((kmDetails: IKmDetails[]) =>
          this.kmDetailsService.addKmDetailsToCollectionIfMissing<IKmDetails>(kmDetails, this.kmCrops?.kmDetails)
        )
      )
      .subscribe((kmDetails: IKmDetails[]) => (this.kmDetailsSharedCollection = kmDetails));
  }
}
