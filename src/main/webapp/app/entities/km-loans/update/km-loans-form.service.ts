import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IKmLoans, NewKmLoans } from '../km-loans.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IKmLoans for edit and NewKmLoansFormGroupInput for create.
 */
type KmLoansFormGroupInput = IKmLoans | PartialWithRequiredKeyOf<NewKmLoans>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IKmLoans | NewKmLoans> = Omit<T, 'loanDate' | 'dueDate'> & {
  loanDate?: string | null;
  dueDate?: string | null;
};

type KmLoansFormRawValue = FormValueOf<IKmLoans>;

type NewKmLoansFormRawValue = FormValueOf<NewKmLoans>;

type KmLoansFormDefaults = Pick<NewKmLoans, 'id' | 'loanDate' | 'dueDate'>;

type KmLoansFormGroupContent = {
  id: FormControl<KmLoansFormRawValue['id'] | NewKmLoans['id']>;
  cropName: FormControl<KmLoansFormRawValue['cropName']>;
  cropNameMr: FormControl<KmLoansFormRawValue['cropNameMr']>;
  loanDate: FormControl<KmLoansFormRawValue['loanDate']>;
  loanAmount: FormControl<KmLoansFormRawValue['loanAmount']>;
  loanAmountMr: FormControl<KmLoansFormRawValue['loanAmountMr']>;
  are: FormControl<KmLoansFormRawValue['are']>;
  areMr: FormControl<KmLoansFormRawValue['areMr']>;
  receivableAmt: FormControl<KmLoansFormRawValue['receivableAmt']>;
  receivableAmtMr: FormControl<KmLoansFormRawValue['receivableAmtMr']>;
  dueAmt: FormControl<KmLoansFormRawValue['dueAmt']>;
  dueAmtMr: FormControl<KmLoansFormRawValue['dueAmtMr']>;
  dueDate: FormControl<KmLoansFormRawValue['dueDate']>;
  kmDetails: FormControl<KmLoansFormRawValue['kmDetails']>;
};

export type KmLoansFormGroup = FormGroup<KmLoansFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class KmLoansFormService {
  createKmLoansFormGroup(kmLoans: KmLoansFormGroupInput = { id: null }): KmLoansFormGroup {
    const kmLoansRawValue = this.convertKmLoansToKmLoansRawValue({
      ...this.getFormDefaults(),
      ...kmLoans,
    });
    return new FormGroup<KmLoansFormGroupContent>({
      id: new FormControl(
        { value: kmLoansRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      cropName: new FormControl(kmLoansRawValue.cropName),
      cropNameMr: new FormControl(kmLoansRawValue.cropNameMr),
      loanDate: new FormControl(kmLoansRawValue.loanDate),
      loanAmount: new FormControl(kmLoansRawValue.loanAmount),
      loanAmountMr: new FormControl(kmLoansRawValue.loanAmountMr),
      are: new FormControl(kmLoansRawValue.are),
      areMr: new FormControl(kmLoansRawValue.areMr),
      receivableAmt: new FormControl(kmLoansRawValue.receivableAmt),
      receivableAmtMr: new FormControl(kmLoansRawValue.receivableAmtMr),
      dueAmt: new FormControl(kmLoansRawValue.dueAmt),
      dueAmtMr: new FormControl(kmLoansRawValue.dueAmtMr),
      dueDate: new FormControl(kmLoansRawValue.dueDate),
      kmDetails: new FormControl(kmLoansRawValue.kmDetails),
    });
  }

  getKmLoans(form: KmLoansFormGroup): IKmLoans | NewKmLoans {
    return this.convertKmLoansRawValueToKmLoans(form.getRawValue() as KmLoansFormRawValue | NewKmLoansFormRawValue);
  }

  resetForm(form: KmLoansFormGroup, kmLoans: KmLoansFormGroupInput): void {
    const kmLoansRawValue = this.convertKmLoansToKmLoansRawValue({ ...this.getFormDefaults(), ...kmLoans });
    form.reset(
      {
        ...kmLoansRawValue,
        id: { value: kmLoansRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): KmLoansFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      loanDate: currentTime,
      dueDate: currentTime,
    };
  }

  private convertKmLoansRawValueToKmLoans(rawKmLoans: KmLoansFormRawValue | NewKmLoansFormRawValue): IKmLoans | NewKmLoans {
    return {
      ...rawKmLoans,
      loanDate: dayjs(rawKmLoans.loanDate, DATE_TIME_FORMAT),
      dueDate: dayjs(rawKmLoans.dueDate, DATE_TIME_FORMAT),
    };
  }

  private convertKmLoansToKmLoansRawValue(
    kmLoans: IKmLoans | (Partial<NewKmLoans> & KmLoansFormDefaults),
  ): KmLoansFormRawValue | PartialWithRequiredKeyOf<NewKmLoansFormRawValue> {
    return {
      ...kmLoans,
      loanDate: kmLoans.loanDate ? kmLoans.loanDate.format(DATE_TIME_FORMAT) : undefined,
      dueDate: kmLoans.dueDate ? kmLoans.dueDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
