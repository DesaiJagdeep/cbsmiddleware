import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IKamalSociety, NewKamalSociety } from '../kamal-society.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IKamalSociety for edit and NewKamalSocietyFormGroupInput for create.
 */
type KamalSocietyFormGroupInput = IKamalSociety | PartialWithRequiredKeyOf<NewKamalSociety>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IKamalSociety | NewKamalSociety> = Omit<T, 'kmDate'> & {
  kmDate?: string | null;
};

type KamalSocietyFormRawValue = FormValueOf<IKamalSociety>;

type NewKamalSocietyFormRawValue = FormValueOf<NewKamalSociety>;

type KamalSocietyFormDefaults = Pick<NewKamalSociety, 'id' | 'kmDate'>;

type KamalSocietyFormGroupContent = {
  id: FormControl<KamalSocietyFormRawValue['id'] | NewKamalSociety['id']>;
  financialYear: FormControl<KamalSocietyFormRawValue['financialYear']>;
  kmDate: FormControl<KamalSocietyFormRawValue['kmDate']>;
};

export type KamalSocietyFormGroup = FormGroup<KamalSocietyFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class KamalSocietyFormService {
  createKamalSocietyFormGroup(kamalSociety: KamalSocietyFormGroupInput = { id: null }): KamalSocietyFormGroup {
    const kamalSocietyRawValue = this.convertKamalSocietyToKamalSocietyRawValue({
      ...this.getFormDefaults(),
      ...kamalSociety,
    });
    return new FormGroup<KamalSocietyFormGroupContent>({
      id: new FormControl(
        { value: kamalSocietyRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      financialYear: new FormControl(kamalSocietyRawValue.financialYear, {
        validators: [Validators.required],
      }),
      kmDate: new FormControl(kamalSocietyRawValue.kmDate),
    });
  }

  getKamalSociety(form: KamalSocietyFormGroup): IKamalSociety | NewKamalSociety {
    return this.convertKamalSocietyRawValueToKamalSociety(form.getRawValue() as KamalSocietyFormRawValue | NewKamalSocietyFormRawValue);
  }

  resetForm(form: KamalSocietyFormGroup, kamalSociety: KamalSocietyFormGroupInput): void {
    const kamalSocietyRawValue = this.convertKamalSocietyToKamalSocietyRawValue({ ...this.getFormDefaults(), ...kamalSociety });
    form.reset(
      {
        ...kamalSocietyRawValue,
        id: { value: kamalSocietyRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): KamalSocietyFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      kmDate: currentTime,
    };
  }

  private convertKamalSocietyRawValueToKamalSociety(
    rawKamalSociety: KamalSocietyFormRawValue | NewKamalSocietyFormRawValue
  ): IKamalSociety | NewKamalSociety {
    return {
      ...rawKamalSociety,
      kmDate: dayjs(rawKamalSociety.kmDate, DATE_TIME_FORMAT),
    };
  }

  private convertKamalSocietyToKamalSocietyRawValue(
    kamalSociety: IKamalSociety | (Partial<NewKamalSociety> & KamalSocietyFormDefaults)
  ): KamalSocietyFormRawValue | PartialWithRequiredKeyOf<NewKamalSocietyFormRawValue> {
    return {
      ...kamalSociety,
      kmDate: kamalSociety.kmDate ? kamalSociety.kmDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
