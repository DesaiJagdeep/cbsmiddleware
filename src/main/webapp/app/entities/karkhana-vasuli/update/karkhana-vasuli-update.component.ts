import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { KarkhanaVasuliFormService, KarkhanaVasuliFormGroup } from './karkhana-vasuli-form.service';
import { IKarkhanaVasuli } from '../karkhana-vasuli.model';
import { KarkhanaVasuliService } from '../service/karkhana-vasuli.service';

@Component({
  selector: 'jhi-karkhana-vasuli-update',
  templateUrl: './karkhana-vasuli-update.component.html',
})
export class KarkhanaVasuliUpdateComponent implements OnInit {
  isSaving = false;
  karkhanaVasuli: IKarkhanaVasuli | null = null;

  editForm: KarkhanaVasuliFormGroup = this.karkhanaVasuliFormService.createKarkhanaVasuliFormGroup();

  constructor(
    protected karkhanaVasuliService: KarkhanaVasuliService,
    protected karkhanaVasuliFormService: KarkhanaVasuliFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ karkhanaVasuli }) => {
      this.karkhanaVasuli = karkhanaVasuli;
      if (karkhanaVasuli) {
        this.updateForm(karkhanaVasuli);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const karkhanaVasuli = this.karkhanaVasuliFormService.getKarkhanaVasuli(this.editForm);
    if (karkhanaVasuli.id !== null) {
      this.subscribeToSaveResponse(this.karkhanaVasuliService.update(karkhanaVasuli));
    } else {
      this.subscribeToSaveResponse(this.karkhanaVasuliService.create(karkhanaVasuli));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IKarkhanaVasuli>>): void {
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

  protected updateForm(karkhanaVasuli: IKarkhanaVasuli): void {
    this.karkhanaVasuli = karkhanaVasuli;
    this.karkhanaVasuliFormService.resetForm(this.editForm, karkhanaVasuli);
  }
}
