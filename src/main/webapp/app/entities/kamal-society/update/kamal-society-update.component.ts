import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { KamalSocietyFormService, KamalSocietyFormGroup } from './kamal-society-form.service';
import { IKamalSociety } from '../kamal-society.model';
import { KamalSocietyService } from '../service/kamal-society.service';

@Component({
  selector: 'jhi-kamal-society-update',
  templateUrl: './kamal-society-update.component.html',
})
export class KamalSocietyUpdateComponent implements OnInit {
  isSaving = false;
  kamalSociety: IKamalSociety | null = null;

  editForm: KamalSocietyFormGroup = this.kamalSocietyFormService.createKamalSocietyFormGroup();

  constructor(
    protected kamalSocietyService: KamalSocietyService,
    protected kamalSocietyFormService: KamalSocietyFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ kamalSociety }) => {
      this.kamalSociety = kamalSociety;
      if (kamalSociety) {
        this.updateForm(kamalSociety);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const kamalSociety = this.kamalSocietyFormService.getKamalSociety(this.editForm);
    if (kamalSociety.id !== null) {
      this.subscribeToSaveResponse(this.kamalSocietyService.update(kamalSociety));
    } else {
      this.subscribeToSaveResponse(this.kamalSocietyService.create(kamalSociety));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IKamalSociety>>): void {
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

  protected updateForm(kamalSociety: IKamalSociety): void {
    this.kamalSociety = kamalSociety;
    this.kamalSocietyFormService.resetForm(this.editForm, kamalSociety);
  }
}
