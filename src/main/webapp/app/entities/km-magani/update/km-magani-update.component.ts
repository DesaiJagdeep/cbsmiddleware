import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { KmMaganiFormService, KmMaganiFormGroup } from './km-magani-form.service';
import { IKmMagani } from '../km-magani.model';
import { KmMaganiService } from '../service/km-magani.service';

@Component({
  selector: 'jhi-km-magani-update',
  templateUrl: './km-magani-update.component.html',
})
export class KmMaganiUpdateComponent implements OnInit {
  isSaving = false;
  kmMagani: IKmMagani | null = null;

  editForm: KmMaganiFormGroup = this.kmMaganiFormService.createKmMaganiFormGroup();

  constructor(
    protected kmMaganiService: KmMaganiService,
    protected kmMaganiFormService: KmMaganiFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ kmMagani }) => {
      this.kmMagani = kmMagani;
      if (kmMagani) {
        this.updateForm(kmMagani);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const kmMagani = this.kmMaganiFormService.getKmMagani(this.editForm);
    if (kmMagani.id !== null) {
      this.subscribeToSaveResponse(this.kmMaganiService.update(kmMagani));
    } else {
      this.subscribeToSaveResponse(this.kmMaganiService.create(kmMagani));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IKmMagani>>): void {
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

  protected updateForm(kmMagani: IKmMagani): void {
    this.kmMagani = kmMagani;
    this.kmMaganiFormService.resetForm(this.editForm, kmMagani);
  }
}
