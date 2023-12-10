import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IKarkhanaVasuliRecords, NewKarkhanaVasuliRecords } from '../karkhana-vasuli-records.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IKarkhanaVasuliRecords for edit and NewKarkhanaVasuliRecordsFormGroupInput for create.
 */
type KarkhanaVasuliRecordsFormGroupInput = IKarkhanaVasuliRecords | PartialWithRequiredKeyOf<NewKarkhanaVasuliRecords>;

type KarkhanaVasuliRecordsFormDefaults = Pick<NewKarkhanaVasuliRecords, 'id' | 'status'>;

type KarkhanaVasuliRecordsFormGroupContent = {
  id: FormControl<IKarkhanaVasuliRecords['id'] | NewKarkhanaVasuliRecords['id']>;
  factoryMemberCode: FormControl<IKarkhanaVasuliRecords['factoryMemberCode']>;
  karkhanaMemberCodeMr: FormControl<IKarkhanaVasuliRecords['karkhanaMemberCodeMr']>;
  memberName: FormControl<IKarkhanaVasuliRecords['memberName']>;
  memberNameMr: FormControl<IKarkhanaVasuliRecords['memberNameMr']>;
  villageName: FormControl<IKarkhanaVasuliRecords['villageName']>;
  villageNameMr: FormControl<IKarkhanaVasuliRecords['villageNameMr']>;
  amount: FormControl<IKarkhanaVasuliRecords['amount']>;
  amountMr: FormControl<IKarkhanaVasuliRecords['amountMr']>;
  savingAccNo: FormControl<IKarkhanaVasuliRecords['savingAccNo']>;
  savingAccNoMr: FormControl<IKarkhanaVasuliRecords['savingAccNoMr']>;
  status: FormControl<IKarkhanaVasuliRecords['status']>;
  karkhanaVasuliFile: FormControl<IKarkhanaVasuliRecords['karkhanaVasuliFile']>;
};

export type KarkhanaVasuliRecordsFormGroup = FormGroup<KarkhanaVasuliRecordsFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class KarkhanaVasuliRecordsFormService {
  createKarkhanaVasuliRecordsFormGroup(
    karkhanaVasuliRecords: KarkhanaVasuliRecordsFormGroupInput = { id: null },
  ): KarkhanaVasuliRecordsFormGroup {
    const karkhanaVasuliRecordsRawValue = {
      ...this.getFormDefaults(),
      ...karkhanaVasuliRecords,
    };
    return new FormGroup<KarkhanaVasuliRecordsFormGroupContent>({
      id: new FormControl(
        { value: karkhanaVasuliRecordsRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      factoryMemberCode: new FormControl(karkhanaVasuliRecordsRawValue.factoryMemberCode),
      karkhanaMemberCodeMr: new FormControl(karkhanaVasuliRecordsRawValue.karkhanaMemberCodeMr),
      memberName: new FormControl(karkhanaVasuliRecordsRawValue.memberName),
      memberNameMr: new FormControl(karkhanaVasuliRecordsRawValue.memberNameMr),
      villageName: new FormControl(karkhanaVasuliRecordsRawValue.villageName),
      villageNameMr: new FormControl(karkhanaVasuliRecordsRawValue.villageNameMr),
      amount: new FormControl(karkhanaVasuliRecordsRawValue.amount),
      amountMr: new FormControl(karkhanaVasuliRecordsRawValue.amountMr),
      savingAccNo: new FormControl(karkhanaVasuliRecordsRawValue.savingAccNo),
      savingAccNoMr: new FormControl(karkhanaVasuliRecordsRawValue.savingAccNoMr),
      status: new FormControl(karkhanaVasuliRecordsRawValue.status),
      karkhanaVasuliFile: new FormControl(karkhanaVasuliRecordsRawValue.karkhanaVasuliFile),
    });
  }

  getKarkhanaVasuliRecords(form: KarkhanaVasuliRecordsFormGroup): IKarkhanaVasuliRecords | NewKarkhanaVasuliRecords {
    return form.getRawValue() as IKarkhanaVasuliRecords | NewKarkhanaVasuliRecords;
  }

  resetForm(form: KarkhanaVasuliRecordsFormGroup, karkhanaVasuliRecords: KarkhanaVasuliRecordsFormGroupInput): void {
    const karkhanaVasuliRecordsRawValue = { ...this.getFormDefaults(), ...karkhanaVasuliRecords };
    form.reset(
      {
        ...karkhanaVasuliRecordsRawValue,
        id: { value: karkhanaVasuliRecordsRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): KarkhanaVasuliRecordsFormDefaults {
    return {
      id: null,
      status: false,
    };
  }
}
