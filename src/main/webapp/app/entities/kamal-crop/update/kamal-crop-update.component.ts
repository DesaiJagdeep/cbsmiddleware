import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { KamalCropFormService, KamalCropFormGroup } from './kamal-crop-form.service';
import { IKamalCrop } from '../kamal-crop.model';
import { KamalCropService } from '../service/kamal-crop.service';
import { IFarmerTypeMaster } from 'app/entities/farmer-type-master/farmer-type-master.model';
import { FarmerTypeMasterService } from 'app/entities/farmer-type-master/service/farmer-type-master.service';
import { ISeasonMaster } from 'app/entities/season-master/season-master.model';
import { SeasonMasterService } from 'app/entities/season-master/service/season-master.service';
import { ICropMaster } from 'app/entities/crop-master/crop-master.model';
import { CropMasterService } from 'app/entities/crop-master/service/crop-master.service';
import { IKamalSociety } from 'app/entities/kamal-society/kamal-society.model';
import { KamalSocietyService } from 'app/entities/kamal-society/service/kamal-society.service';

@Component({
  selector: 'jhi-kamal-crop-update',
  templateUrl: './kamal-crop-update.component.html',
})
export class KamalCropUpdateComponent implements OnInit {
  isSaving = false;
  kamalCrop: IKamalCrop | null = null;

  farmerTypeMastersSharedCollection: IFarmerTypeMaster[] = [];
  seasonMastersSharedCollection: ISeasonMaster[] = [];
  cropMastersSharedCollection: ICropMaster[] = [];
  kamalSocietiesSharedCollection: IKamalSociety[] = [];

  editForm: KamalCropFormGroup = this.kamalCropFormService.createKamalCropFormGroup();

  constructor(
    protected kamalCropService: KamalCropService,
    protected kamalCropFormService: KamalCropFormService,
    protected farmerTypeMasterService: FarmerTypeMasterService,
    protected seasonMasterService: SeasonMasterService,
    protected cropMasterService: CropMasterService,
    protected kamalSocietyService: KamalSocietyService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareFarmerTypeMaster = (o1: IFarmerTypeMaster | null, o2: IFarmerTypeMaster | null): boolean =>
    this.farmerTypeMasterService.compareFarmerTypeMaster(o1, o2);

  compareSeasonMaster = (o1: ISeasonMaster | null, o2: ISeasonMaster | null): boolean =>
    this.seasonMasterService.compareSeasonMaster(o1, o2);

  compareCropMaster = (o1: ICropMaster | null, o2: ICropMaster | null): boolean => this.cropMasterService.compareCropMaster(o1, o2);

  compareKamalSociety = (o1: IKamalSociety | null, o2: IKamalSociety | null): boolean =>
    this.kamalSocietyService.compareKamalSociety(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ kamalCrop }) => {
      this.kamalCrop = kamalCrop;
      if (kamalCrop) {
        this.updateForm(kamalCrop);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const kamalCrop = this.kamalCropFormService.getKamalCrop(this.editForm);
    if (kamalCrop.id !== null) {
      this.subscribeToSaveResponse(this.kamalCropService.update(kamalCrop));
    } else {
      this.subscribeToSaveResponse(this.kamalCropService.create(kamalCrop));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IKamalCrop>>): void {
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

  protected updateForm(kamalCrop: IKamalCrop): void {
    this.kamalCrop = kamalCrop;
    this.kamalCropFormService.resetForm(this.editForm, kamalCrop);

    this.farmerTypeMastersSharedCollection = this.farmerTypeMasterService.addFarmerTypeMasterToCollectionIfMissing<IFarmerTypeMaster>(
      this.farmerTypeMastersSharedCollection,
      kamalCrop.farmerTypeMaster
    );
    this.seasonMastersSharedCollection = this.seasonMasterService.addSeasonMasterToCollectionIfMissing<ISeasonMaster>(
      this.seasonMastersSharedCollection,
      kamalCrop.seasonMaster
    );
    this.cropMastersSharedCollection = this.cropMasterService.addCropMasterToCollectionIfMissing<ICropMaster>(
      this.cropMastersSharedCollection,
      kamalCrop.cropMaster
    );
    this.kamalSocietiesSharedCollection = this.kamalSocietyService.addKamalSocietyToCollectionIfMissing<IKamalSociety>(
      this.kamalSocietiesSharedCollection,
      kamalCrop.kamalSociety
    );
  }

  protected loadRelationshipsOptions(): void {
    this.farmerTypeMasterService
      .query()
      .pipe(map((res: HttpResponse<IFarmerTypeMaster[]>) => res.body ?? []))
      .pipe(
        map((farmerTypeMasters: IFarmerTypeMaster[]) =>
          this.farmerTypeMasterService.addFarmerTypeMasterToCollectionIfMissing<IFarmerTypeMaster>(
            farmerTypeMasters,
            this.kamalCrop?.farmerTypeMaster
          )
        )
      )
      .subscribe((farmerTypeMasters: IFarmerTypeMaster[]) => (this.farmerTypeMastersSharedCollection = farmerTypeMasters));

    this.seasonMasterService
      .query()
      .pipe(map((res: HttpResponse<ISeasonMaster[]>) => res.body ?? []))
      .pipe(
        map((seasonMasters: ISeasonMaster[]) =>
          this.seasonMasterService.addSeasonMasterToCollectionIfMissing<ISeasonMaster>(seasonMasters, this.kamalCrop?.seasonMaster)
        )
      )
      .subscribe((seasonMasters: ISeasonMaster[]) => (this.seasonMastersSharedCollection = seasonMasters));

    this.cropMasterService
      .query()
      .pipe(map((res: HttpResponse<ICropMaster[]>) => res.body ?? []))
      .pipe(
        map((cropMasters: ICropMaster[]) =>
          this.cropMasterService.addCropMasterToCollectionIfMissing<ICropMaster>(cropMasters, this.kamalCrop?.cropMaster)
        )
      )
      .subscribe((cropMasters: ICropMaster[]) => (this.cropMastersSharedCollection = cropMasters));

    this.kamalSocietyService
      .query()
      .pipe(map((res: HttpResponse<IKamalSociety[]>) => res.body ?? []))
      .pipe(
        map((kamalSocieties: IKamalSociety[]) =>
          this.kamalSocietyService.addKamalSocietyToCollectionIfMissing<IKamalSociety>(kamalSocieties, this.kamalCrop?.kamalSociety)
        )
      )
      .subscribe((kamalSocieties: IKamalSociety[]) => (this.kamalSocietiesSharedCollection = kamalSocieties));
  }
}
