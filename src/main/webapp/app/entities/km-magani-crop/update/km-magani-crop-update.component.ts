import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { KmMaganiCropFormService, KmMaganiCropFormGroup } from './km-magani-crop-form.service';
import { IKmMaganiCrop } from '../km-magani-crop.model';
import { KmMaganiCropService } from '../service/km-magani-crop.service';
import { IKmMagani } from 'app/entities/km-magani/km-magani.model';
import { KmMaganiService } from 'app/entities/km-magani/service/km-magani.service';

@Component({
  selector: 'jhi-km-magani-crop-update',
  templateUrl: './km-magani-crop-update.component.html',
})
export class KmMaganiCropUpdateComponent implements OnInit {
  isSaving = false;
  kmMaganiCrop: IKmMaganiCrop | null = null;

  kmMaganisSharedCollection: IKmMagani[] = [];

  editForm: KmMaganiCropFormGroup = this.kmMaganiCropFormService.createKmMaganiCropFormGroup();

  constructor(
    protected kmMaganiCropService: KmMaganiCropService,
    protected kmMaganiCropFormService: KmMaganiCropFormService,
    protected kmMaganiService: KmMaganiService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareKmMagani = (o1: IKmMagani | null, o2: IKmMagani | null): boolean => this.kmMaganiService.compareKmMagani(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ kmMaganiCrop }) => {
      this.kmMaganiCrop = kmMaganiCrop;
      if (kmMaganiCrop) {
        this.updateForm(kmMaganiCrop);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const kmMaganiCrop = this.kmMaganiCropFormService.getKmMaganiCrop(this.editForm);
    if (kmMaganiCrop.id !== null) {
      this.subscribeToSaveResponse(this.kmMaganiCropService.update(kmMaganiCrop));
    } else {
      this.subscribeToSaveResponse(this.kmMaganiCropService.create(kmMaganiCrop));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IKmMaganiCrop>>): void {
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

  protected updateForm(kmMaganiCrop: IKmMaganiCrop): void {
    this.kmMaganiCrop = kmMaganiCrop;
    this.kmMaganiCropFormService.resetForm(this.editForm, kmMaganiCrop);

    this.kmMaganisSharedCollection = this.kmMaganiService.addKmMaganiToCollectionIfMissing<IKmMagani>(
      this.kmMaganisSharedCollection,
      kmMaganiCrop.kmMagani
    );
  }

  protected loadRelationshipsOptions(): void {
    this.kmMaganiService
      .query()
      .pipe(map((res: HttpResponse<IKmMagani[]>) => res.body ?? []))
      .pipe(
        map((kmMaganis: IKmMagani[]) =>
          this.kmMaganiService.addKmMaganiToCollectionIfMissing<IKmMagani>(kmMaganis, this.kmMaganiCrop?.kmMagani)
        )
      )
      .subscribe((kmMaganis: IKmMagani[]) => (this.kmMaganisSharedCollection = kmMaganis));
  }
}
