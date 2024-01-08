import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { KarkhanaVasuliFileFormService, KarkhanaVasuliFileFormGroup } from './karkhana-vasuli-file-form.service';
import { IKarkhanaVasuliFile } from '../karkhana-vasuli-file.model';
import { KarkhanaVasuliFileService } from '../service/karkhana-vasuli-file.service';
import { IFactoryMaster } from 'app/entities/factory-master/factory-master.model';
import { FactoryMasterService } from 'app/entities/factory-master/service/factory-master.service';

@Component({
  selector: 'jhi-karkhana-vasuli-file-update',
  templateUrl: './karkhana-vasuli-file-update.component.html',
})
export class KarkhanaVasuliFileUpdateComponent implements OnInit {
  isSaving = false;
  karkhanaVasuliFile: IKarkhanaVasuliFile | null = null;

  factoryMastersSharedCollection: IFactoryMaster[] = [];

  editForm: KarkhanaVasuliFileFormGroup = this.karkhanaVasuliFileFormService.createKarkhanaVasuliFileFormGroup();

  constructor(
    protected karkhanaVasuliFileService: KarkhanaVasuliFileService,
    protected karkhanaVasuliFileFormService: KarkhanaVasuliFileFormService,
    protected factoryMasterService: FactoryMasterService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareFactoryMaster = (o1: IFactoryMaster | null, o2: IFactoryMaster | null): boolean =>
    this.factoryMasterService.compareFactoryMaster(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ karkhanaVasuliFile }) => {
      this.karkhanaVasuliFile = karkhanaVasuliFile;
      if (karkhanaVasuliFile) {
        this.updateForm(karkhanaVasuliFile);
      }

      this.loadRelationshipsOptions();
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

    this.factoryMastersSharedCollection = this.factoryMasterService.addFactoryMasterToCollectionIfMissing<IFactoryMaster>(
      this.factoryMastersSharedCollection,
      karkhanaVasuliFile.factoryMaster
    );
  }

  protected loadRelationshipsOptions(): void {
    this.factoryMasterService
      .query()
      .pipe(map((res: HttpResponse<IFactoryMaster[]>) => res.body ?? []))
      .pipe(
        map((factoryMasters: IFactoryMaster[]) =>
          this.factoryMasterService.addFactoryMasterToCollectionIfMissing<IFactoryMaster>(
            factoryMasters,
            this.karkhanaVasuliFile?.factoryMaster
          )
        )
      )
      .subscribe((factoryMasters: IFactoryMaster[]) => (this.factoryMastersSharedCollection = factoryMasters));
  }
}
