import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { CategoryMasterFormService, CategoryMasterFormGroup } from './category-master-form.service';
import { ICategoryMaster } from '../category-master.model';
import { CategoryMasterService } from '../service/category-master.service';

@Component({
  selector: 'jhi-category-master-update',
  templateUrl: './category-master-update.component.html',
})
export class CategoryMasterUpdateComponent implements OnInit {
  isSaving = false;
  categoryMaster: ICategoryMaster | null = null;

  editForm: CategoryMasterFormGroup = this.categoryMasterFormService.createCategoryMasterFormGroup();

  constructor(
    protected categoryMasterService: CategoryMasterService,
    protected categoryMasterFormService: CategoryMasterFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ categoryMaster }) => {
      this.categoryMaster = categoryMaster;
      if (categoryMaster) {
        this.updateForm(categoryMaster);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const categoryMaster = this.categoryMasterFormService.getCategoryMaster(this.editForm);
    if (categoryMaster.id !== null) {
      this.subscribeToSaveResponse(this.categoryMasterService.update(categoryMaster));
    } else {
      this.subscribeToSaveResponse(this.categoryMasterService.create(categoryMaster));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICategoryMaster>>): void {
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

  protected updateForm(categoryMaster: ICategoryMaster): void {
    this.categoryMaster = categoryMaster;
    this.categoryMasterFormService.resetForm(this.editForm, categoryMaster);
  }
}
