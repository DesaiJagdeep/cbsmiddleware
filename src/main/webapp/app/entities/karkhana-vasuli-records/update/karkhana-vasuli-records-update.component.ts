import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IKarkhanaVasuliFile } from 'app/entities/karkhana-vasuli-file/karkhana-vasuli-file.model';
import { KarkhanaVasuliFileService } from 'app/entities/karkhana-vasuli-file/service/karkhana-vasuli-file.service';
import { IKarkhanaVasuliRecords } from '../karkhana-vasuli-records.model';
import { KarkhanaVasuliRecordsService } from '../service/karkhana-vasuli-records.service';
import { KarkhanaVasuliRecordsFormService, KarkhanaVasuliRecordsFormGroup } from './karkhana-vasuli-records-form.service';

@Component({
  standalone: true,
  selector: 'jhi-karkhana-vasuli-records-update',
  templateUrl: './karkhana-vasuli-records-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class KarkhanaVasuliRecordsUpdateComponent implements OnInit {
  isSaving = false;
  karkhanaVasuliRecords: IKarkhanaVasuliRecords | null = null;

  karkhanaVasuliFilesSharedCollection: IKarkhanaVasuliFile[] = [];

  editForm: KarkhanaVasuliRecordsFormGroup = this.karkhanaVasuliRecordsFormService.createKarkhanaVasuliRecordsFormGroup();

  constructor(
    protected karkhanaVasuliRecordsService: KarkhanaVasuliRecordsService,
    protected karkhanaVasuliRecordsFormService: KarkhanaVasuliRecordsFormService,
    protected karkhanaVasuliFileService: KarkhanaVasuliFileService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareKarkhanaVasuliFile = (o1: IKarkhanaVasuliFile | null, o2: IKarkhanaVasuliFile | null): boolean =>
    this.karkhanaVasuliFileService.compareKarkhanaVasuliFile(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ karkhanaVasuliRecords }) => {
      this.karkhanaVasuliRecords = karkhanaVasuliRecords;
      if (karkhanaVasuliRecords) {
        this.updateForm(karkhanaVasuliRecords);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const karkhanaVasuliRecords = this.karkhanaVasuliRecordsFormService.getKarkhanaVasuliRecords(this.editForm);
    if (karkhanaVasuliRecords.id !== null) {
      this.subscribeToSaveResponse(this.karkhanaVasuliRecordsService.update(karkhanaVasuliRecords));
    } else {
      this.subscribeToSaveResponse(this.karkhanaVasuliRecordsService.create(karkhanaVasuliRecords));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IKarkhanaVasuliRecords>>): void {
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

  protected updateForm(karkhanaVasuliRecords: IKarkhanaVasuliRecords): void {
    this.karkhanaVasuliRecords = karkhanaVasuliRecords;
    this.karkhanaVasuliRecordsFormService.resetForm(this.editForm, karkhanaVasuliRecords);

    this.karkhanaVasuliFilesSharedCollection =
      this.karkhanaVasuliFileService.addKarkhanaVasuliFileToCollectionIfMissing<IKarkhanaVasuliFile>(
        this.karkhanaVasuliFilesSharedCollection,
        karkhanaVasuliRecords.karkhanaVasuliFile,
      );
  }

  protected loadRelationshipsOptions(): void {
    this.karkhanaVasuliFileService
      .query()
      .pipe(map((res: HttpResponse<IKarkhanaVasuliFile[]>) => res.body ?? []))
      .pipe(
        map((karkhanaVasuliFiles: IKarkhanaVasuliFile[]) =>
          this.karkhanaVasuliFileService.addKarkhanaVasuliFileToCollectionIfMissing<IKarkhanaVasuliFile>(
            karkhanaVasuliFiles,
            this.karkhanaVasuliRecords?.karkhanaVasuliFile,
          ),
        ),
      )
      .subscribe((karkhanaVasuliFiles: IKarkhanaVasuliFile[]) => (this.karkhanaVasuliFilesSharedCollection = karkhanaVasuliFiles));
  }
}
