import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IKmDetails, NewKmDetails } from '../km-details.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IKmDetails for edit and NewKmDetailsFormGroupInput for create.
 */
type KmDetailsFormGroupInput = IKmDetails | PartialWithRequiredKeyOf<NewKmDetails>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IKmDetails | NewKmDetails> = Omit<
  T,
  'dueDate' | 'kmDate' | 'kmFromDate' | 'kmToDate' | 'eAgreementDate' | 'bojaDate'
> & {
  dueDate?: string | null;
  kmDate?: string | null;
  kmFromDate?: string | null;
  kmToDate?: string | null;
  eAgreementDate?: string | null;
  bojaDate?: string | null;
};

type KmDetailsFormRawValue = FormValueOf<IKmDetails>;

type NewKmDetailsFormRawValue = FormValueOf<NewKmDetails>;

type KmDetailsFormDefaults = Pick<NewKmDetails, 'id' | 'dueDate' | 'kmDate' | 'kmFromDate' | 'kmToDate' | 'eAgreementDate' | 'bojaDate'>;

type KmDetailsFormGroupContent = {
  id: FormControl<KmDetailsFormRawValue['id'] | NewKmDetails['id']>;
  shares: FormControl<KmDetailsFormRawValue['shares']>;
  sharesMr: FormControl<KmDetailsFormRawValue['sharesMr']>;
  sugarShares: FormControl<KmDetailsFormRawValue['sugarShares']>;
  sugarSharesMr: FormControl<KmDetailsFormRawValue['sugarSharesMr']>;
  deposit: FormControl<KmDetailsFormRawValue['deposit']>;
  depositMr: FormControl<KmDetailsFormRawValue['depositMr']>;
  dueLoan: FormControl<KmDetailsFormRawValue['dueLoan']>;
  dueLoanMr: FormControl<KmDetailsFormRawValue['dueLoanMr']>;
  dueAmount: FormControl<KmDetailsFormRawValue['dueAmount']>;
  dueAmountMr: FormControl<KmDetailsFormRawValue['dueAmountMr']>;
  dueDate: FormControl<KmDetailsFormRawValue['dueDate']>;
  kmDate: FormControl<KmDetailsFormRawValue['kmDate']>;
  kmFromDate: FormControl<KmDetailsFormRawValue['kmFromDate']>;
  kmToDate: FormControl<KmDetailsFormRawValue['kmToDate']>;
  bagayatHector: FormControl<KmDetailsFormRawValue['bagayatHector']>;
  bagayatHectorMr: FormControl<KmDetailsFormRawValue['bagayatHectorMr']>;
  bagayatAre: FormControl<KmDetailsFormRawValue['bagayatAre']>;
  bagayatAreMr: FormControl<KmDetailsFormRawValue['bagayatAreMr']>;
  jirayatHector: FormControl<KmDetailsFormRawValue['jirayatHector']>;
  jirayatHectorMr: FormControl<KmDetailsFormRawValue['jirayatHectorMr']>;
  jirayatAre: FormControl<KmDetailsFormRawValue['jirayatAre']>;
  jirayatAreMr: FormControl<KmDetailsFormRawValue['jirayatAreMr']>;
  landValue: FormControl<KmDetailsFormRawValue['landValue']>;
  landValueMr: FormControl<KmDetailsFormRawValue['landValueMr']>;
  eAggrementAmt: FormControl<KmDetailsFormRawValue['eAggrementAmt']>;
  eAgreementAmt: FormControl<KmDetailsFormRawValue['eAgreementAmt']>;
  eAgreementDate: FormControl<KmDetailsFormRawValue['eAgreementDate']>;
  bojaAmount: FormControl<KmDetailsFormRawValue['bojaAmount']>;
  bojaAmountMr: FormControl<KmDetailsFormRawValue['bojaAmountMr']>;
  bojaDate: FormControl<KmDetailsFormRawValue['bojaDate']>;
  kmMaster: FormControl<KmDetailsFormRawValue['kmMaster']>;
};

export type KmDetailsFormGroup = FormGroup<KmDetailsFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class KmDetailsFormService {
  createKmDetailsFormGroup(kmDetails: KmDetailsFormGroupInput = { id: null }): KmDetailsFormGroup {
    const kmDetailsRawValue = this.convertKmDetailsToKmDetailsRawValue({
      ...this.getFormDefaults(),
      ...kmDetails,
    });
    return new FormGroup<KmDetailsFormGroupContent>({
      id: new FormControl(
        { value: kmDetailsRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      shares: new FormControl(kmDetailsRawValue.shares),
      sharesMr: new FormControl(kmDetailsRawValue.sharesMr),
      sugarShares: new FormControl(kmDetailsRawValue.sugarShares),
      sugarSharesMr: new FormControl(kmDetailsRawValue.sugarSharesMr),
      deposit: new FormControl(kmDetailsRawValue.deposit),
      depositMr: new FormControl(kmDetailsRawValue.depositMr),
      dueLoan: new FormControl(kmDetailsRawValue.dueLoan),
      dueLoanMr: new FormControl(kmDetailsRawValue.dueLoanMr),
      dueAmount: new FormControl(kmDetailsRawValue.dueAmount),
      dueAmountMr: new FormControl(kmDetailsRawValue.dueAmountMr),
      dueDate: new FormControl(kmDetailsRawValue.dueDate),
      kmDate: new FormControl(kmDetailsRawValue.kmDate),
      kmFromDate: new FormControl(kmDetailsRawValue.kmFromDate),
      kmToDate: new FormControl(kmDetailsRawValue.kmToDate),
      bagayatHector: new FormControl(kmDetailsRawValue.bagayatHector),
      bagayatHectorMr: new FormControl(kmDetailsRawValue.bagayatHectorMr),
      bagayatAre: new FormControl(kmDetailsRawValue.bagayatAre),
      bagayatAreMr: new FormControl(kmDetailsRawValue.bagayatAreMr),
      jirayatHector: new FormControl(kmDetailsRawValue.jirayatHector),
      jirayatHectorMr: new FormControl(kmDetailsRawValue.jirayatHectorMr),
      jirayatAre: new FormControl(kmDetailsRawValue.jirayatAre),
      jirayatAreMr: new FormControl(kmDetailsRawValue.jirayatAreMr),
      landValue: new FormControl(kmDetailsRawValue.landValue),
      landValueMr: new FormControl(kmDetailsRawValue.landValueMr),
      eAggrementAmt: new FormControl(kmDetailsRawValue.eAggrementAmt),
      eAgreementAmt: new FormControl(kmDetailsRawValue.eAgreementAmt),
      eAgreementDate: new FormControl(kmDetailsRawValue.eAgreementDate),
      bojaAmount: new FormControl(kmDetailsRawValue.bojaAmount),
      bojaAmountMr: new FormControl(kmDetailsRawValue.bojaAmountMr),
      bojaDate: new FormControl(kmDetailsRawValue.bojaDate),
      kmMaster: new FormControl(kmDetailsRawValue.kmMaster),
    });
  }

  getKmDetails(form: KmDetailsFormGroup): IKmDetails | NewKmDetails {
    return this.convertKmDetailsRawValueToKmDetails(form.getRawValue() as KmDetailsFormRawValue | NewKmDetailsFormRawValue);
  }

  resetForm(form: KmDetailsFormGroup, kmDetails: KmDetailsFormGroupInput): void {
    const kmDetailsRawValue = this.convertKmDetailsToKmDetailsRawValue({ ...this.getFormDefaults(), ...kmDetails });
    form.reset(
      {
        ...kmDetailsRawValue,
        id: { value: kmDetailsRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): KmDetailsFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      dueDate: currentTime,
      kmDate: currentTime,
      kmFromDate: currentTime,
      kmToDate: currentTime,
      eAgreementDate: currentTime,
      bojaDate: currentTime,
    };
  }

  private convertKmDetailsRawValueToKmDetails(rawKmDetails: KmDetailsFormRawValue | NewKmDetailsFormRawValue): IKmDetails | NewKmDetails {
    return {
      ...rawKmDetails,
      dueDate: dayjs(rawKmDetails.dueDate, DATE_TIME_FORMAT),
      kmDate: dayjs(rawKmDetails.kmDate, DATE_TIME_FORMAT),
      kmFromDate: dayjs(rawKmDetails.kmFromDate, DATE_TIME_FORMAT),
      kmToDate: dayjs(rawKmDetails.kmToDate, DATE_TIME_FORMAT),
      eAgreementDate: dayjs(rawKmDetails.eAgreementDate, DATE_TIME_FORMAT),
      bojaDate: dayjs(rawKmDetails.bojaDate, DATE_TIME_FORMAT),
    };
  }

  private convertKmDetailsToKmDetailsRawValue(
    kmDetails: IKmDetails | (Partial<NewKmDetails> & KmDetailsFormDefaults),
  ): KmDetailsFormRawValue | PartialWithRequiredKeyOf<NewKmDetailsFormRawValue> {
    return {
      ...kmDetails,
      dueDate: kmDetails.dueDate ? kmDetails.dueDate.format(DATE_TIME_FORMAT) : undefined,
      kmDate: kmDetails.kmDate ? kmDetails.kmDate.format(DATE_TIME_FORMAT) : undefined,
      kmFromDate: kmDetails.kmFromDate ? kmDetails.kmFromDate.format(DATE_TIME_FORMAT) : undefined,
      kmToDate: kmDetails.kmToDate ? kmDetails.kmToDate.format(DATE_TIME_FORMAT) : undefined,
      eAgreementDate: kmDetails.eAgreementDate ? kmDetails.eAgreementDate.format(DATE_TIME_FORMAT) : undefined,
      bojaDate: kmDetails.bojaDate ? kmDetails.bojaDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
