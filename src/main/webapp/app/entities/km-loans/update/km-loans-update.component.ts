import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IKmDetails } from 'app/entities/km-details/km-details.model';
import { KmDetailsService } from 'app/entities/km-details/service/km-details.service';
import { IKmLoans } from '../km-loans.model';
import { KmLoansService } from '../service/km-loans.service';
import { KmLoansFormService, KmLoansFormGroup } from './km-loans-form.service';

@Component({
  standalone: true,
  selector: 'jhi-km-loans-update',
  templateUrl: './km-loans-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class KmLoansUpdateComponent implements OnInit {
  isSaving = false;
  kmLoans: IKmLoans | null = null;

  kmDetailsSharedCollection: IKmDetails[] = [];

  editForm: KmLoansFormGroup = this.kmLoansFormService.createKmLoansFormGroup();

  constructor(
    protected kmLoansService: KmLoansService,
    protected kmLoansFormService: KmLoansFormService,
    protected kmDetailsService: KmDetailsService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareKmDetails = (o1: IKmDetails | null, o2: IKmDetails | null): boolean => this.kmDetailsService.compareKmDetails(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ kmLoans }) => {
      this.kmLoans = kmLoans;
      if (kmLoans) {
        this.updateForm(kmLoans);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const kmLoans = this.kmLoansFormService.getKmLoans(this.editForm);
    if (kmLoans.id !== null) {
      this.subscribeToSaveResponse(this.kmLoansService.update(kmLoans));
    } else {
      this.subscribeToSaveResponse(this.kmLoansService.create(kmLoans));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IKmLoans>>): void {
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

  protected updateForm(kmLoans: IKmLoans): void {
    this.kmLoans = kmLoans;
    this.kmLoansFormService.resetForm(this.editForm, kmLoans);

    this.kmDetailsSharedCollection = this.kmDetailsService.addKmDetailsToCollectionIfMissing<IKmDetails>(
      this.kmDetailsSharedCollection,
      kmLoans.kmDetails,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.kmDetailsService
      .query()
      .pipe(map((res: HttpResponse<IKmDetails[]>) => res.body ?? []))
      .pipe(
        map((kmDetails: IKmDetails[]) =>
          this.kmDetailsService.addKmDetailsToCollectionIfMissing<IKmDetails>(kmDetails, this.kmLoans?.kmDetails),
        ),
      )
      .subscribe((kmDetails: IKmDetails[]) => (this.kmDetailsSharedCollection = kmDetails));
  }
}
