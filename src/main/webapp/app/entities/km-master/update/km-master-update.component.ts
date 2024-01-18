import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { KmMasterFormService, KmMasterFormGroup } from './km-master-form.service';
import { IKmMaster } from '../km-master.model';
import { KmMasterService } from '../service/km-master.service';
import { IFarmerTypeMaster } from 'app/entities/farmer-type-master/farmer-type-master.model';
import { FarmerTypeMasterService } from 'app/entities/farmer-type-master/service/farmer-type-master.service';

@Component({
  selector: 'jhi-km-master-update',
  templateUrl: './km-master-update.component.html',
})
export class KmMasterUpdateComponent implements OnInit {
  isSaving = false;
  kmMaster: IKmMaster | null = null;

  farmerTypeMastersSharedCollection: IFarmerTypeMaster[] = [];

  editForm: KmMasterFormGroup = this.kmMasterFormService.createKmMasterFormGroup();

  constructor(
    protected kmMasterService: KmMasterService,
    protected kmMasterFormService: KmMasterFormService,
    protected farmerTypeMasterService: FarmerTypeMasterService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareFarmerTypeMaster = (o1: IFarmerTypeMaster | null, o2: IFarmerTypeMaster | null): boolean =>
    this.farmerTypeMasterService.compareFarmerTypeMaster(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ kmMaster }) => {
      this.kmMaster = kmMaster;
      if (kmMaster) {
        this.updateForm(kmMaster);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const kmMaster = this.kmMasterFormService.getKmMaster(this.editForm);
    if (kmMaster.id !== null) {
      this.subscribeToSaveResponse(this.kmMasterService.update(kmMaster));
    } else {
      this.subscribeToSaveResponse(this.kmMasterService.create(kmMaster));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IKmMaster>>): void {
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

  protected updateForm(kmMaster: IKmMaster): void {
    this.kmMaster = kmMaster;
    this.kmMasterFormService.resetForm(this.editForm, kmMaster);

    this.farmerTypeMastersSharedCollection = this.farmerTypeMasterService.addFarmerTypeMasterToCollectionIfMissing<IFarmerTypeMaster>(
      this.farmerTypeMastersSharedCollection,
      kmMaster.farmerTypeMaster
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
            this.kmMaster?.farmerTypeMaster
          )
        )
      )
      .subscribe((farmerTypeMasters: IFarmerTypeMaster[]) => (this.farmerTypeMastersSharedCollection = farmerTypeMasters));
  }
}
