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
  branchCodeMr: FormControl<IKmMaster['branchCodeMr']>;
  farmerName: FormControl<IKmMaster['farmerName']>;
  farmerNameMr: FormControl<IKmMaster['farmerNameMr']>;
  farmerAddress: FormControl<IKmMaster['farmerAddress']>;
  farmerAddressMr: FormControl<IKmMaster['farmerAddressMr']>;
  gender: FormControl<IKmMaster['gender']>;
  genderMr: FormControl<IKmMaster['genderMr']>;
  caste: FormControl<IKmMaster['caste']>;
  casteMr: FormControl<IKmMaster['casteMr']>;
  pacsNumber: FormControl<IKmMaster['pacsNumber']>;
  areaHector: FormControl<IKmMaster['areaHector']>;
  areaHectorMr: FormControl<IKmMaster['areaHectorMr']>;
  aadharNo: FormControl<IKmMaster['aadharNo']>;
  aadharNoMr: FormControl<IKmMaster['aadharNoMr']>;
  panNo: FormControl<IKmMaster['panNo']>;
  panNoMr: FormControl<IKmMaster['panNoMr']>;
  mobileNo: FormControl<IKmMaster['mobileNo']>;
  mobileNoMr: FormControl<IKmMaster['mobileNoMr']>;
  kccNo: FormControl<IKmMaster['kccNo']>;
  kccNoMr: FormControl<IKmMaster['kccNoMr']>;
  savingAcNo: FormControl<IKmMaster['savingAcNo']>;
  savingAcNoMr: FormControl<IKmMaster['savingAcNoMr']>;
  pacsMemberCode: FormControl<IKmMaster['pacsMemberCode']>;
  pacsMemberCodeMr: FormControl<IKmMaster['pacsMemberCodeMr']>;
  entryFlag: FormControl<IKmMaster['entryFlag']>;
  farmerTypeMaster: FormControl<IKmMaster['farmerTypeMaster']>;
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
        }
      ),
      branchCode: new FormControl(kmMasterRawValue.branchCode, {
        validators: [Validators.required, Validators.maxLength(255)],
      }),
      branchCodeMr: new FormControl(kmMasterRawValue.branchCodeMr),
      farmerName: new FormControl(kmMasterRawValue.farmerName, {
        validators: [Validators.required, Validators.maxLength(255)],
      }),
      farmerNameMr: new FormControl(kmMasterRawValue.farmerNameMr),
      farmerAddress: new FormControl(kmMasterRawValue.farmerAddress, {
        validators: [Validators.required, Validators.maxLength(255)],
      }),
      farmerAddressMr: new FormControl(kmMasterRawValue.farmerAddressMr),
      gender: new FormControl(kmMasterRawValue.gender, {
        validators: [Validators.required, Validators.maxLength(255)],
      }),
      genderMr: new FormControl(kmMasterRawValue.genderMr),
      caste: new FormControl(kmMasterRawValue.caste, {
        validators: [Validators.required, Validators.maxLength(255)],
      }),
      casteMr: new FormControl(kmMasterRawValue.casteMr),
      pacsNumber: new FormControl(kmMasterRawValue.pacsNumber),
      areaHector: new FormControl(kmMasterRawValue.areaHector, {
        validators: [Validators.required],
      }),
      areaHectorMr: new FormControl(kmMasterRawValue.areaHectorMr),
      aadharNo: new FormControl(kmMasterRawValue.aadharNo, {
        validators: [Validators.required, Validators.minLength(12), Validators.maxLength(12), Validators.pattern('^d{12}$')],
      }),
      aadharNoMr: new FormControl(kmMasterRawValue.aadharNoMr),
      panNo: new FormControl(kmMasterRawValue.panNo, {
        validators: [
          Validators.required,
          Validators.minLength(10),
          Validators.maxLength(10),
          Validators.pattern('^[A-Z]{5}[0-9]{4}[A-Z]{1}$'),
        ],
      }),
      panNoMr: new FormControl(kmMasterRawValue.panNoMr),
      mobileNo: new FormControl(kmMasterRawValue.mobileNo, {
        validators: [Validators.required, Validators.minLength(10), Validators.maxLength(10)],
      }),
      mobileNoMr: new FormControl(kmMasterRawValue.mobileNoMr),
      kccNo: new FormControl(kmMasterRawValue.kccNo, {
        validators: [Validators.required],
      }),
      kccNoMr: new FormControl(kmMasterRawValue.kccNoMr),
      savingAcNo: new FormControl(kmMasterRawValue.savingAcNo, {
        validators: [Validators.required],
      }),
      savingAcNoMr: new FormControl(kmMasterRawValue.savingAcNoMr),
      pacsMemberCode: new FormControl(kmMasterRawValue.pacsMemberCode, {
        validators: [Validators.required],
      }),
      pacsMemberCodeMr: new FormControl(kmMasterRawValue.pacsMemberCodeMr),
      entryFlag: new FormControl(kmMasterRawValue.entryFlag),
      farmerTypeMaster: new FormControl(kmMasterRawValue.farmerTypeMaster),
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
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): KmMasterFormDefaults {
    return {
      id: null,
    };
  }
}
