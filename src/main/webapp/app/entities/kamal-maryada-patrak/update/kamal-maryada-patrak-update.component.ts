import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { KamalMaryadaPatrakFormService, KamalMaryadaPatrakFormGroup } from './kamal-maryada-patrak-form.service';
import { IKamalMaryadaPatrak } from '../kamal-maryada-patrak.model';
import { KamalMaryadaPatrakService } from '../service/kamal-maryada-patrak.service';

@Component({
  selector: 'jhi-kamal-maryada-patrak-update',
  templateUrl: './kamal-maryada-patrak-update.component.html',
})
export class KamalMaryadaPatrakUpdateComponent implements OnInit {
  isSaving = false;
  kamalMaryadaPatrak: IKamalMaryadaPatrak | null = null;

  editForm: KamalMaryadaPatrakFormGroup = this.kamalMaryadaPatrakFormService.createKamalMaryadaPatrakFormGroup();

  constructor(
    protected kamalMaryadaPatrakService: KamalMaryadaPatrakService,
    protected kamalMaryadaPatrakFormService: KamalMaryadaPatrakFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ kamalMaryadaPatrak }) => {
      this.kamalMaryadaPatrak = kamalMaryadaPatrak;
      if (kamalMaryadaPatrak) {
        this.updateForm(kamalMaryadaPatrak);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const kamalMaryadaPatrak = this.kamalMaryadaPatrakFormService.getKamalMaryadaPatrak(this.editForm);
    if (kamalMaryadaPatrak.id !== null) {
      this.subscribeToSaveResponse(this.kamalMaryadaPatrakService.update(kamalMaryadaPatrak));
    } else {
      this.subscribeToSaveResponse(this.kamalMaryadaPatrakService.create(kamalMaryadaPatrak));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IKamalMaryadaPatrak>>): void {
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

  protected updateForm(kamalMaryadaPatrak: IKamalMaryadaPatrak): void {
    this.kamalMaryadaPatrak = kamalMaryadaPatrak;
    this.kamalMaryadaPatrakFormService.resetForm(this.editForm, kamalMaryadaPatrak);
  }
}
