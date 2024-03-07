import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IKmMagani, NewKmMagani } from '../km-magani.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IKmMagani for edit and NewKmMaganiFormGroupInput for create.
 */
type KmMaganiFormGroupInput = IKmMagani | PartialWithRequiredKeyOf<NewKmMagani>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IKmMagani | NewKmMagani> = Omit<T, 'kmDate' | 'maganiDate'> & {
  kmDate?: string | null;
  maganiDate?: string | null;
};

type KmMaganiFormRawValue = FormValueOf<IKmMagani>;

type NewKmMaganiFormRawValue = FormValueOf<NewKmMagani>;

type KmMaganiFormDefaults = Pick<NewKmMagani, 'id' | 'kmDate' | 'maganiDate'>;

type KmMaganiFormGroupContent = {
  id: FormControl<KmMaganiFormRawValue['id'] | NewKmMagani['id']>;
  kmNumber: FormControl<KmMaganiFormRawValue['kmNumber']>;
  memberNumber: FormControl<KmMaganiFormRawValue['memberNumber']>;
  memberName: FormControl<KmMaganiFormRawValue['memberName']>;
  pacsNumber: FormControl<KmMaganiFormRawValue['pacsNumber']>;
  share: FormControl<KmMaganiFormRawValue['share']>;
  financialYear: FormControl<KmMaganiFormRawValue['financialYear']>;
  kmDate: FormControl<KmMaganiFormRawValue['kmDate']>;
  maganiDate: FormControl<KmMaganiFormRawValue['maganiDate']>;
};

export type KmMaganiFormGroup = FormGroup<KmMaganiFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class KmMaganiFormService {
  createKmMaganiFormGroup(kmMagani: KmMaganiFormGroupInput = { id: null }): KmMaganiFormGroup {
    const kmMaganiRawValue = this.convertKmMaganiToKmMaganiRawValue({
      ...this.getFormDefaults(),
      ...kmMagani,
    });
    return new FormGroup<KmMaganiFormGroupContent>({
      id: new FormControl(
        { value: kmMaganiRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      kmNumber: new FormControl(kmMaganiRawValue.kmNumber),
      memberNumber: new FormControl(kmMaganiRawValue.memberNumber),
      memberName: new FormControl(kmMaganiRawValue.memberName),
      pacsNumber: new FormControl(kmMaganiRawValue.pacsNumber),
      share: new FormControl(kmMaganiRawValue.share),
      financialYear: new FormControl(kmMaganiRawValue.financialYear),
      kmDate: new FormControl(kmMaganiRawValue.kmDate),
      maganiDate: new FormControl(kmMaganiRawValue.maganiDate),
    });
  }

  getKmMagani(form: KmMaganiFormGroup): IKmMagani | NewKmMagani {
    return this.convertKmMaganiRawValueToKmMagani(form.getRawValue() as KmMaganiFormRawValue | NewKmMaganiFormRawValue);
  }

  resetForm(form: KmMaganiFormGroup, kmMagani: KmMaganiFormGroupInput): void {
    const kmMaganiRawValue = this.convertKmMaganiToKmMaganiRawValue({ ...this.getFormDefaults(), ...kmMagani });
    form.reset(
      {
        ...kmMaganiRawValue,
        id: { value: kmMaganiRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): KmMaganiFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      kmDate: currentTime,
      maganiDate: currentTime,
    };
  }

  private convertKmMaganiRawValueToKmMagani(rawKmMagani: KmMaganiFormRawValue | NewKmMaganiFormRawValue): IKmMagani | NewKmMagani {
    return {
      ...rawKmMagani,
      kmDate: dayjs(rawKmMagani.kmDate, DATE_TIME_FORMAT),
      maganiDate: dayjs(rawKmMagani.maganiDate, DATE_TIME_FORMAT),
    };
  }

  private convertKmMaganiToKmMaganiRawValue(
    kmMagani: IKmMagani | (Partial<NewKmMagani> & KmMaganiFormDefaults)
  ): KmMaganiFormRawValue | PartialWithRequiredKeyOf<NewKmMaganiFormRawValue> {
    return {
      ...kmMagani,
      kmDate: kmMagani.kmDate ? kmMagani.kmDate.format(DATE_TIME_FORMAT) : undefined,
      maganiDate: kmMagani.maganiDate ? kmMagani.maganiDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
