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
type FormValueOf<T extends IKmLoans | NewKmLoans> = Omit<T, 'dueDate'> & {
  dueDate?: string | null;
};

type KmLoansFormRawValue = FormValueOf<IKmLoans>;

type NewKmLoansFormRawValue = FormValueOf<NewKmLoans>;

type KmLoansFormDefaults = Pick<NewKmLoans, 'id' | 'dueDate'>;

type KmLoansFormGroupContent = {
  id: FormControl<KmLoansFormRawValue['id'] | NewKmLoans['id']>;
  hector: FormControl<KmLoansFormRawValue['hector']>;
  hectorMr: FormControl<KmLoansFormRawValue['hectorMr']>;
  are: FormControl<KmLoansFormRawValue['are']>;
  aremr: FormControl<KmLoansFormRawValue['aremr']>;
  noOfTree: FormControl<KmLoansFormRawValue['noOfTree']>;
  noOfTreeMr: FormControl<KmLoansFormRawValue['noOfTreeMr']>;
  sanctionAmt: FormControl<KmLoansFormRawValue['sanctionAmt']>;
  sanctionAmtMr: FormControl<KmLoansFormRawValue['sanctionAmtMr']>;
  loanAmt: FormControl<KmLoansFormRawValue['loanAmt']>;
  loanAmtMr: FormControl<KmLoansFormRawValue['loanAmtMr']>;
  receivableAmt: FormControl<KmLoansFormRawValue['receivableAmt']>;
  receivableAmtMr: FormControl<KmLoansFormRawValue['receivableAmtMr']>;
  dueAmt: FormControl<KmLoansFormRawValue['dueAmt']>;
  dueAmtMr: FormControl<KmLoansFormRawValue['dueAmtMr']>;
  dueDate: FormControl<KmLoansFormRawValue['dueDate']>;
  dueDateMr: FormControl<KmLoansFormRawValue['dueDateMr']>;
  spare: FormControl<KmLoansFormRawValue['spare']>;
  cropMaster: FormControl<KmLoansFormRawValue['cropMaster']>;
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
        }
      ),
      hector: new FormControl(kmLoansRawValue.hector, {
        validators: [Validators.required],
      }),
      hectorMr: new FormControl(kmLoansRawValue.hectorMr),
      are: new FormControl(kmLoansRawValue.are, {
        validators: [Validators.required],
      }),
      aremr: new FormControl(kmLoansRawValue.aremr),
      noOfTree: new FormControl(kmLoansRawValue.noOfTree, {
        validators: [Validators.required],
      }),
      noOfTreeMr: new FormControl(kmLoansRawValue.noOfTreeMr),
      sanctionAmt: new FormControl(kmLoansRawValue.sanctionAmt, {
        validators: [Validators.required],
      }),
      sanctionAmtMr: new FormControl(kmLoansRawValue.sanctionAmtMr),
      loanAmt: new FormControl(kmLoansRawValue.loanAmt, {
        validators: [Validators.required],
      }),
      loanAmtMr: new FormControl(kmLoansRawValue.loanAmtMr),
      receivableAmt: new FormControl(kmLoansRawValue.receivableAmt, {
        validators: [Validators.required],
      }),
      receivableAmtMr: new FormControl(kmLoansRawValue.receivableAmtMr),
      dueAmt: new FormControl(kmLoansRawValue.dueAmt, {
        validators: [Validators.required],
      }),
      dueAmtMr: new FormControl(kmLoansRawValue.dueAmtMr),
      dueDate: new FormControl(kmLoansRawValue.dueDate, {
        validators: [Validators.required],
      }),
      dueDateMr: new FormControl(kmLoansRawValue.dueDateMr),
      spare: new FormControl(kmLoansRawValue.spare),
      cropMaster: new FormControl(kmLoansRawValue.cropMaster),
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
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): KmLoansFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      dueDate: currentTime,
    };
  }

  private convertKmLoansRawValueToKmLoans(rawKmLoans: KmLoansFormRawValue | NewKmLoansFormRawValue): IKmLoans | NewKmLoans {
    return {
      ...rawKmLoans,
      dueDate: dayjs(rawKmLoans.dueDate, DATE_TIME_FORMAT),
    };
  }

  private convertKmLoansToKmLoansRawValue(
    kmLoans: IKmLoans | (Partial<NewKmLoans> & KmLoansFormDefaults)
  ): KmLoansFormRawValue | PartialWithRequiredKeyOf<NewKmLoansFormRawValue> {
    return {
      ...kmLoans,
      dueDate: kmLoans.dueDate ? kmLoans.dueDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
