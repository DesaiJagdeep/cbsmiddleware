import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IKarkhanaVasuliFile, NewKarkhanaVasuliFile } from '../karkhana-vasuli-file.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IKarkhanaVasuliFile for edit and NewKarkhanaVasuliFileFormGroupInput for create.
 */
type KarkhanaVasuliFileFormGroupInput = IKarkhanaVasuliFile | PartialWithRequiredKeyOf<NewKarkhanaVasuliFile>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IKarkhanaVasuliFile | NewKarkhanaVasuliFile> = Omit<T, 'fromDate' | 'toDate'> & {
  fromDate?: string | null;
  toDate?: string | null;
};

type KarkhanaVasuliFileFormRawValue = FormValueOf<IKarkhanaVasuliFile>;

type NewKarkhanaVasuliFileFormRawValue = FormValueOf<NewKarkhanaVasuliFile>;

type KarkhanaVasuliFileFormDefaults = Pick<NewKarkhanaVasuliFile, 'id' | 'fromDate' | 'toDate'>;

type KarkhanaVasuliFileFormGroupContent = {
  id: FormControl<KarkhanaVasuliFileFormRawValue['id'] | NewKarkhanaVasuliFile['id']>;
  fileName: FormControl<KarkhanaVasuliFileFormRawValue['fileName']>;
  uniqueFileName: FormControl<KarkhanaVasuliFileFormRawValue['uniqueFileName']>;
  address: FormControl<KarkhanaVasuliFileFormRawValue['address']>;
  addressMr: FormControl<KarkhanaVasuliFileFormRawValue['addressMr']>;
  hangam: FormControl<KarkhanaVasuliFileFormRawValue['hangam']>;
  hangamMr: FormControl<KarkhanaVasuliFileFormRawValue['hangamMr']>;
  factoryName: FormControl<KarkhanaVasuliFileFormRawValue['factoryName']>;
  factoryNameMr: FormControl<KarkhanaVasuliFileFormRawValue['factoryNameMr']>;
  totalAmount: FormControl<KarkhanaVasuliFileFormRawValue['totalAmount']>;
  totalAmountMr: FormControl<KarkhanaVasuliFileFormRawValue['totalAmountMr']>;
  fromDate: FormControl<KarkhanaVasuliFileFormRawValue['fromDate']>;
  toDate: FormControl<KarkhanaVasuliFileFormRawValue['toDate']>;
  branchCode: FormControl<KarkhanaVasuliFileFormRawValue['branchCode']>;
  pacsName: FormControl<KarkhanaVasuliFileFormRawValue['pacsName']>;
  factoryMaster: FormControl<KarkhanaVasuliFileFormRawValue['factoryMaster']>;
};

export type KarkhanaVasuliFileFormGroup = FormGroup<KarkhanaVasuliFileFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class KarkhanaVasuliFileFormService {
  createKarkhanaVasuliFileFormGroup(karkhanaVasuliFile: KarkhanaVasuliFileFormGroupInput = { id: null }): KarkhanaVasuliFileFormGroup {
    const karkhanaVasuliFileRawValue = this.convertKarkhanaVasuliFileToKarkhanaVasuliFileRawValue({
      ...this.getFormDefaults(),
      ...karkhanaVasuliFile,
    });
    return new FormGroup<KarkhanaVasuliFileFormGroupContent>({
      id: new FormControl(
        { value: karkhanaVasuliFileRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      fileName: new FormControl(karkhanaVasuliFileRawValue.fileName),
      uniqueFileName: new FormControl(karkhanaVasuliFileRawValue.uniqueFileName),
      address: new FormControl(karkhanaVasuliFileRawValue.address),
      addressMr: new FormControl(karkhanaVasuliFileRawValue.addressMr),
      hangam: new FormControl(karkhanaVasuliFileRawValue.hangam),
      hangamMr: new FormControl(karkhanaVasuliFileRawValue.hangamMr),
      factoryName: new FormControl(karkhanaVasuliFileRawValue.factoryName),
      factoryNameMr: new FormControl(karkhanaVasuliFileRawValue.factoryNameMr),
      totalAmount: new FormControl(karkhanaVasuliFileRawValue.totalAmount),
      totalAmountMr: new FormControl(karkhanaVasuliFileRawValue.totalAmountMr),
      fromDate: new FormControl(karkhanaVasuliFileRawValue.fromDate),
      toDate: new FormControl(karkhanaVasuliFileRawValue.toDate),
      branchCode: new FormControl(karkhanaVasuliFileRawValue.branchCode),
      pacsName: new FormControl(karkhanaVasuliFileRawValue.pacsName),
      factoryMaster: new FormControl(karkhanaVasuliFileRawValue.factoryMaster),
    });
  }

  getKarkhanaVasuliFile(form: KarkhanaVasuliFileFormGroup): IKarkhanaVasuliFile | NewKarkhanaVasuliFile {
    return this.convertKarkhanaVasuliFileRawValueToKarkhanaVasuliFile(
      form.getRawValue() as KarkhanaVasuliFileFormRawValue | NewKarkhanaVasuliFileFormRawValue
    );
  }

  resetForm(form: KarkhanaVasuliFileFormGroup, karkhanaVasuliFile: KarkhanaVasuliFileFormGroupInput): void {
    const karkhanaVasuliFileRawValue = this.convertKarkhanaVasuliFileToKarkhanaVasuliFileRawValue({
      ...this.getFormDefaults(),
      ...karkhanaVasuliFile,
    });
    form.reset(
      {
        ...karkhanaVasuliFileRawValue,
        id: { value: karkhanaVasuliFileRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): KarkhanaVasuliFileFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      fromDate: currentTime,
      toDate: currentTime,
    };
  }

  private convertKarkhanaVasuliFileRawValueToKarkhanaVasuliFile(
    rawKarkhanaVasuliFile: KarkhanaVasuliFileFormRawValue | NewKarkhanaVasuliFileFormRawValue
  ): IKarkhanaVasuliFile | NewKarkhanaVasuliFile {
    return {
      ...rawKarkhanaVasuliFile,
      fromDate: dayjs(rawKarkhanaVasuliFile.fromDate, DATE_TIME_FORMAT),
      toDate: dayjs(rawKarkhanaVasuliFile.toDate, DATE_TIME_FORMAT),
    };
  }

  private convertKarkhanaVasuliFileToKarkhanaVasuliFileRawValue(
    karkhanaVasuliFile: IKarkhanaVasuliFile | (Partial<NewKarkhanaVasuliFile> & KarkhanaVasuliFileFormDefaults)
  ): KarkhanaVasuliFileFormRawValue | PartialWithRequiredKeyOf<NewKarkhanaVasuliFileFormRawValue> {
    return {
      ...karkhanaVasuliFile,
      fromDate: karkhanaVasuliFile.fromDate ? karkhanaVasuliFile.fromDate.format(DATE_TIME_FORMAT) : undefined,
      toDate: karkhanaVasuliFile.toDate ? karkhanaVasuliFile.toDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
