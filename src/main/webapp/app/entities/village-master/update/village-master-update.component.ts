import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ITalukaMaster } from 'app/entities/taluka-master/taluka-master.model';
import { TalukaMasterService } from 'app/entities/taluka-master/service/taluka-master.service';
import { IVillageMaster } from '../village-master.model';
import { VillageMasterService } from '../service/village-master.service';
import { VillageMasterFormService, VillageMasterFormGroup } from './village-master-form.service';

@Component({
  standalone: true,
  selector: 'jhi-village-master-update',
  templateUrl: './village-master-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class VillageMasterUpdateComponent implements OnInit {
  isSaving = false;
  villageMaster: IVillageMaster | null = null;

  talukaMastersCollection: ITalukaMaster[] = [];

  editForm: VillageMasterFormGroup = this.villageMasterFormService.createVillageMasterFormGroup();

  constructor(
    protected villageMasterService: VillageMasterService,
    protected villageMasterFormService: VillageMasterFormService,
    protected talukaMasterService: TalukaMasterService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareTalukaMaster = (o1: ITalukaMaster | null, o2: ITalukaMaster | null): boolean =>
    this.talukaMasterService.compareTalukaMaster(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ villageMaster }) => {
      this.villageMaster = villageMaster;
      if (villageMaster) {
        this.updateForm(villageMaster);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const villageMaster = this.villageMasterFormService.getVillageMaster(this.editForm);
    if (villageMaster.id !== null) {
      this.subscribeToSaveResponse(this.villageMasterService.update(villageMaster));
    } else {
      this.subscribeToSaveResponse(this.villageMasterService.create(villageMaster));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVillageMaster>>): void {
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

  protected updateForm(villageMaster: IVillageMaster): void {
    this.villageMaster = villageMaster;
    this.villageMasterFormService.resetForm(this.editForm, villageMaster);

    this.talukaMastersCollection = this.talukaMasterService.addTalukaMasterToCollectionIfMissing<ITalukaMaster>(
      this.talukaMastersCollection,
      villageMaster.talukaMaster,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.talukaMasterService
      .query({ filter: 'villagemaster-is-null' })
      .pipe(map((res: HttpResponse<ITalukaMaster[]>) => res.body ?? []))
      .pipe(
        map((talukaMasters: ITalukaMaster[]) =>
          this.talukaMasterService.addTalukaMasterToCollectionIfMissing<ITalukaMaster>(talukaMasters, this.villageMaster?.talukaMaster),
        ),
      )
      .subscribe((talukaMasters: ITalukaMaster[]) => (this.talukaMastersCollection = talukaMasters));
  }
}