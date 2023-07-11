import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { CastCategoryMasterFormService, CastCategoryMasterFormGroup } from './cast-category-master-form.service';
import { ICastCategoryMaster } from '../cast-category-master.model';
import { CastCategoryMasterService } from '../service/cast-category-master.service';

@Component({
  selector: 'jhi-cast-category-master-update',
  templateUrl: './cast-category-master-update.component.html',
})
export class CastCategoryMasterUpdateComponent implements OnInit {
  isSaving = false;
  castCategoryMaster: ICastCategoryMaster | null = null;

  editForm: CastCategoryMasterFormGroup = this.castCategoryMasterFormService.createCastCategoryMasterFormGroup();

  constructor(
    protected castCategoryMasterService: CastCategoryMasterService,
    protected castCategoryMasterFormService: CastCategoryMasterFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ castCategoryMaster }) => {
      this.castCategoryMaster = castCategoryMaster;
      if (castCategoryMaster) {
        this.updateForm(castCategoryMaster);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const castCategoryMaster = this.castCategoryMasterFormService.getCastCategoryMaster(this.editForm);
    if (castCategoryMaster.id !== null) {
      this.subscribeToSaveResponse(this.castCategoryMasterService.update(castCategoryMaster));
    } else {
      this.subscribeToSaveResponse(this.castCategoryMasterService.create(castCategoryMaster));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICastCategoryMaster>>): void {
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

  protected updateForm(castCategoryMaster: ICastCategoryMaster): void {
    this.castCategoryMaster = castCategoryMaster;
    this.castCategoryMasterFormService.resetForm(this.editForm, castCategoryMaster);
  }
}
