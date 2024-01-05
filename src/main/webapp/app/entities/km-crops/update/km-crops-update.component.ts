import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IKmDetails } from 'app/entities/km-details/km-details.model';
import { KmDetailsService } from 'app/entities/km-details/service/km-details.service';
import { IKmCrops } from '../km-crops.model';
import { KmCropsService } from '../service/km-crops.service';
import { KmCropsFormService, KmCropsFormGroup } from './km-crops-form.service';

@Component({
  standalone: true,
  selector: 'jhi-km-crops-update',
  templateUrl: './km-crops-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class KmCropsUpdateComponent implements OnInit {
  isSaving = false;
  kmCrops: IKmCrops | null = null;

  kmDetailsSharedCollection: IKmDetails[] = [];

  editForm: KmCropsFormGroup = this.kmCropsFormService.createKmCropsFormGroup();

  constructor(
    protected kmCropsService: KmCropsService,
    protected kmCropsFormService: KmCropsFormService,
    protected kmDetailsService: KmDetailsService,
    protected activatedRoute: ActivatedRoute,
  ) {}

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

    this.kmDetailsSharedCollection = this.kmDetailsService.addKmDetailsToCollectionIfMissing<IKmDetails>(
      this.kmDetailsSharedCollection,
      kmCrops.kmDetails,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.kmDetailsService
      .query()
      .pipe(map((res: HttpResponse<IKmDetails[]>) => res.body ?? []))
      .pipe(
        map((kmDetails: IKmDetails[]) =>
          this.kmDetailsService.addKmDetailsToCollectionIfMissing<IKmDetails>(kmDetails, this.kmCrops?.kmDetails),
        ),
      )
      .subscribe((kmDetails: IKmDetails[]) => (this.kmDetailsSharedCollection = kmDetails));
  }
}
