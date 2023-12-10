import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IKarkhanaVasuliFile } from '../karkhana-vasuli-file.model';
import { KarkhanaVasuliFileService } from '../service/karkhana-vasuli-file.service';
import { KarkhanaVasuliFileFormService, KarkhanaVasuliFileFormGroup } from './karkhana-vasuli-file-form.service';

@Component({
  standalone: true,
  selector: 'jhi-karkhana-vasuli-file-update',
  templateUrl: './karkhana-vasuli-file-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class KarkhanaVasuliFileUpdateComponent implements OnInit {
  isSaving = false;
  karkhanaVasuliFile: IKarkhanaVasuliFile | null = null;

  editForm: KarkhanaVasuliFileFormGroup = this.karkhanaVasuliFileFormService.createKarkhanaVasuliFileFormGroup();

  constructor(
    protected karkhanaVasuliFileService: KarkhanaVasuliFileService,
    protected karkhanaVasuliFileFormService: KarkhanaVasuliFileFormService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ karkhanaVasuliFile }) => {
      this.karkhanaVasuliFile = karkhanaVasuliFile;
      if (karkhanaVasuliFile) {
        this.updateForm(karkhanaVasuliFile);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const karkhanaVasuliFile = this.karkhanaVasuliFileFormService.getKarkhanaVasuliFile(this.editForm);
    if (karkhanaVasuliFile.id !== null) {
      this.subscribeToSaveResponse(this.karkhanaVasuliFileService.update(karkhanaVasuliFile));
    } else {
      this.subscribeToSaveResponse(this.karkhanaVasuliFileService.create(karkhanaVasuliFile));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IKarkhanaVasuliFile>>): void {
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

  protected updateForm(karkhanaVasuliFile: IKarkhanaVasuliFile): void {
    this.karkhanaVasuliFile = karkhanaVasuliFile;
    this.karkhanaVasuliFileFormService.resetForm(this.editForm, karkhanaVasuliFile);
  }
}
