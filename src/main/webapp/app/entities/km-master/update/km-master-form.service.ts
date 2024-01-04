import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IKmMaster, NewKmMaster } from '../km-master.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IKmMaster for edit and NewKmMasterFormGroupInput for create.
 */
type KmMasterFormGroupInput = IKmMaster | PartialWithRequiredKeyOf<NewKmMaster>;

type KmMasterFormDefaults = Pick<NewKmMaster, 'id'>;

type KmMasterFormGroupContent = {
  id: FormControl<IKmMaster['id'] | NewKmMaster['id']>;
  branchCode: FormControl<IKmMaster['branchCode']>;
  farmerName: FormControl<IKmMaster['farmerName']>;
  farmerNameMr: FormControl<IKmMaster['farmerNameMr']>;
  farmerAddress: FormControl<IKmMaster['farmerAddress']>;
  farmerAddressMr: FormControl<IKmMaster['farmerAddressMr']>;
  gender: FormControl<IKmMaster['gender']>;
  caste: FormControl<IKmMaster['caste']>;
  casteMr: FormControl<IKmMaster['casteMr']>;
  pacsNumber: FormControl<IKmMaster['pacsNumber']>;
  areaHect: FormControl<IKmMaster['areaHect']>;
  aadharNo: FormControl<IKmMaster['aadharNo']>;
  aadharNoMr: FormControl<IKmMaster['aadharNoMr']>;
  panNo: FormControl<IKmMaster['panNo']>;
  panNoMr: FormControl<IKmMaster['panNoMr']>;
  mobileNo: FormControl<IKmMaster['mobileNo']>;
  mobileNoMr: FormControl<IKmMaster['mobileNoMr']>;
  kccNo: FormControl<IKmMaster['kccNo']>;
  kccNoMr: FormControl<IKmMaster['kccNoMr']>;
  savingNo: FormControl<IKmMaster['savingNo']>;
  savingNoMr: FormControl<IKmMaster['savingNoMr']>;
  entryFlag: FormControl<IKmMaster['entryFlag']>;
};

export type KmMasterFormGroup = FormGroup<KmMasterFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class KmMasterFormService {
  createKmMasterFormGroup(kmMaster: KmMasterFormGroupInput = { id: null }): KmMasterFormGroup {
    const kmMasterRawValue = {
      ...this.getFormDefaults(),
      ...kmMaster,
    };
    return new FormGroup<KmMasterFormGroupContent>({
      id: new FormControl(
        { value: kmMasterRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      branchCode: new FormControl(kmMasterRawValue.branchCode),
      farmerName: new FormControl(kmMasterRawValue.farmerName),
      farmerNameMr: new FormControl(kmMasterRawValue.farmerNameMr),
      farmerAddress: new FormControl(kmMasterRawValue.farmerAddress),
      farmerAddressMr: new FormControl(kmMasterRawValue.farmerAddressMr),
      gender: new FormControl(kmMasterRawValue.gender),
      caste: new FormControl(kmMasterRawValue.caste),
      casteMr: new FormControl(kmMasterRawValue.casteMr),
      pacsNumber: new FormControl(kmMasterRawValue.pacsNumber),
      areaHect: new FormControl(kmMasterRawValue.areaHect),
      aadharNo: new FormControl(kmMasterRawValue.aadharNo),
      aadharNoMr: new FormControl(kmMasterRawValue.aadharNoMr),
      panNo: new FormControl(kmMasterRawValue.panNo),
      panNoMr: new FormControl(kmMasterRawValue.panNoMr),
      mobileNo: new FormControl(kmMasterRawValue.mobileNo),
      mobileNoMr: new FormControl(kmMasterRawValue.mobileNoMr),
      kccNo: new FormControl(kmMasterRawValue.kccNo),
      kccNoMr: new FormControl(kmMasterRawValue.kccNoMr),
      savingNo: new FormControl(kmMasterRawValue.savingNo),
      savingNoMr: new FormControl(kmMasterRawValue.savingNoMr),
      entryFlag: new FormControl(kmMasterRawValue.entryFlag),
    });
  }

  getKmMaster(form: KmMasterFormGroup): IKmMaster | NewKmMaster {
    return form.getRawValue() as IKmMaster | NewKmMaster;
  }

  resetForm(form: KmMasterFormGroup, kmMaster: KmMasterFormGroupInput): void {
    const kmMasterRawValue = { ...this.getFormDefaults(), ...kmMaster };
    form.reset(
      {
        ...kmMasterRawValue,
        id: { value: kmMasterRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): KmMasterFormDefaults {
    return {
      id: null,
    };
  }
}
