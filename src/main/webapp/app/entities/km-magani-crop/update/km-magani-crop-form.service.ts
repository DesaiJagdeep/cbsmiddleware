import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IKmMaganiCrop, NewKmMaganiCrop } from '../km-magani-crop.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IKmMaganiCrop for edit and NewKmMaganiCropFormGroupInput for create.
 */
type KmMaganiCropFormGroupInput = IKmMaganiCrop | PartialWithRequiredKeyOf<NewKmMaganiCrop>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IKmMaganiCrop | NewKmMaganiCrop> = Omit<T, 'cropDueDate' | 'cropVasuliPatraDate'> & {
  cropDueDate?: string | null;
  cropVasuliPatraDate?: string | null;
};

type KmMaganiCropFormRawValue = FormValueOf<IKmMaganiCrop>;

type NewKmMaganiCropFormRawValue = FormValueOf<NewKmMaganiCrop>;

type KmMaganiCropFormDefaults = Pick<NewKmMaganiCrop, 'id' | 'cropDueDate' | 'cropVasuliPatraDate'>;

type KmMaganiCropFormGroupContent = {
  id: FormControl<KmMaganiCropFormRawValue['id'] | NewKmMaganiCrop['id']>;
  cropName: FormControl<KmMaganiCropFormRawValue['cropName']>;
  cropBalance: FormControl<KmMaganiCropFormRawValue['cropBalance']>;
  cropDue: FormControl<KmMaganiCropFormRawValue['cropDue']>;
  cropDueDate: FormControl<KmMaganiCropFormRawValue['cropDueDate']>;
  cropVasuliPatraDate: FormControl<KmMaganiCropFormRawValue['cropVasuliPatraDate']>;
  kmManjuri: FormControl<KmMaganiCropFormRawValue['kmManjuri']>;
  kmArea: FormControl<KmMaganiCropFormRawValue['kmArea']>;
  eKararNumber: FormControl<KmMaganiCropFormRawValue['eKararNumber']>;
  eKararAmount: FormControl<KmMaganiCropFormRawValue['eKararAmount']>;
  eKararArea: FormControl<KmMaganiCropFormRawValue['eKararArea']>;
  maganiArea: FormControl<KmMaganiCropFormRawValue['maganiArea']>;
  maganiAmount: FormControl<KmMaganiCropFormRawValue['maganiAmount']>;
  maganiShare: FormControl<KmMaganiCropFormRawValue['maganiShare']>;
  pacsNumber: FormControl<KmMaganiCropFormRawValue['pacsNumber']>;
  kmMagani: FormControl<KmMaganiCropFormRawValue['kmMagani']>;
};

export type KmMaganiCropFormGroup = FormGroup<KmMaganiCropFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class KmMaganiCropFormService {
  createKmMaganiCropFormGroup(kmMaganiCrop: KmMaganiCropFormGroupInput = { id: null }): KmMaganiCropFormGroup {
    const kmMaganiCropRawValue = this.convertKmMaganiCropToKmMaganiCropRawValue({
      ...this.getFormDefaults(),
      ...kmMaganiCrop,
    });
    return new FormGroup<KmMaganiCropFormGroupContent>({
      id: new FormControl(
        { value: kmMaganiCropRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      cropName: new FormControl(kmMaganiCropRawValue.cropName),
      cropBalance: new FormControl(kmMaganiCropRawValue.cropBalance),
      cropDue: new FormControl(kmMaganiCropRawValue.cropDue),
      cropDueDate: new FormControl(kmMaganiCropRawValue.cropDueDate),
      cropVasuliPatraDate: new FormControl(kmMaganiCropRawValue.cropVasuliPatraDate),
      kmManjuri: new FormControl(kmMaganiCropRawValue.kmManjuri),
      kmArea: new FormControl(kmMaganiCropRawValue.kmArea),
      eKararNumber: new FormControl(kmMaganiCropRawValue.eKararNumber),
      eKararAmount: new FormControl(kmMaganiCropRawValue.eKararAmount),
      eKararArea: new FormControl(kmMaganiCropRawValue.eKararArea),
      maganiArea: new FormControl(kmMaganiCropRawValue.maganiArea),
      maganiAmount: new FormControl(kmMaganiCropRawValue.maganiAmount),
      maganiShare: new FormControl(kmMaganiCropRawValue.maganiShare),
      pacsNumber: new FormControl(kmMaganiCropRawValue.pacsNumber),
      kmMagani: new FormControl(kmMaganiCropRawValue.kmMagani),
    });
  }

  getKmMaganiCrop(form: KmMaganiCropFormGroup): IKmMaganiCrop | NewKmMaganiCrop {
    return this.convertKmMaganiCropRawValueToKmMaganiCrop(form.getRawValue() as KmMaganiCropFormRawValue | NewKmMaganiCropFormRawValue);
  }

  resetForm(form: KmMaganiCropFormGroup, kmMaganiCrop: KmMaganiCropFormGroupInput): void {
    const kmMaganiCropRawValue = this.convertKmMaganiCropToKmMaganiCropRawValue({ ...this.getFormDefaults(), ...kmMaganiCrop });
    form.reset(
      {
        ...kmMaganiCropRawValue,
        id: { value: kmMaganiCropRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): KmMaganiCropFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      cropDueDate: currentTime,
      cropVasuliPatraDate: currentTime,
    };
  }

  private convertKmMaganiCropRawValueToKmMaganiCrop(
    rawKmMaganiCrop: KmMaganiCropFormRawValue | NewKmMaganiCropFormRawValue
  ): IKmMaganiCrop | NewKmMaganiCrop {
    return {
      ...rawKmMaganiCrop,
      cropDueDate: dayjs(rawKmMaganiCrop.cropDueDate, DATE_TIME_FORMAT),
      cropVasuliPatraDate: dayjs(rawKmMaganiCrop.cropVasuliPatraDate, DATE_TIME_FORMAT),
    };
  }

  private convertKmMaganiCropToKmMaganiCropRawValue(
    kmMaganiCrop: IKmMaganiCrop | (Partial<NewKmMaganiCrop> & KmMaganiCropFormDefaults)
  ): KmMaganiCropFormRawValue | PartialWithRequiredKeyOf<NewKmMaganiCropFormRawValue> {
    return {
      ...kmMaganiCrop,
      cropDueDate: kmMaganiCrop.cropDueDate ? kmMaganiCrop.cropDueDate.format(DATE_TIME_FORMAT) : undefined,
      cropVasuliPatraDate: kmMaganiCrop.cropVasuliPatraDate ? kmMaganiCrop.cropVasuliPatraDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
