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
  deposite: FormControl<KmDetailsFormRawValue['deposite']>;
  depositeMr: FormControl<KmDetailsFormRawValue['depositeMr']>;
  dueLoan: FormControl<KmDetailsFormRawValue['dueLoan']>;
  dueLoanMr: FormControl<KmDetailsFormRawValue['dueLoanMr']>;
  dueAmount: FormControl<KmDetailsFormRawValue['dueAmount']>;
  dueAmountMr: FormControl<KmDetailsFormRawValue['dueAmountMr']>;
  dueDateMr: FormControl<KmDetailsFormRawValue['dueDateMr']>;
  dueDate: FormControl<KmDetailsFormRawValue['dueDate']>;
  kmDate: FormControl<KmDetailsFormRawValue['kmDate']>;
  kmDateMr: FormControl<KmDetailsFormRawValue['kmDateMr']>;
  kmFromDate: FormControl<KmDetailsFormRawValue['kmFromDate']>;
  kmFromDateMr: FormControl<KmDetailsFormRawValue['kmFromDateMr']>;
  kmToDate: FormControl<KmDetailsFormRawValue['kmToDate']>;
  kmToDateMr: FormControl<KmDetailsFormRawValue['kmToDateMr']>;
  bagayatHector: FormControl<KmDetailsFormRawValue['bagayatHector']>;
  bagayatHectorMr: FormControl<KmDetailsFormRawValue['bagayatHectorMr']>;
  bagayatAre: FormControl<KmDetailsFormRawValue['bagayatAre']>;
  bagayatAreMr: FormControl<KmDetailsFormRawValue['bagayatAreMr']>;
  jirayatHector: FormControl<KmDetailsFormRawValue['jirayatHector']>;
  jirayatHectorMr: FormControl<KmDetailsFormRawValue['jirayatHectorMr']>;
  jirayatAre: FormControl<KmDetailsFormRawValue['jirayatAre']>;
  jirayatAreMr: FormControl<KmDetailsFormRawValue['jirayatAreMr']>;
  zindagiAmt: FormControl<KmDetailsFormRawValue['zindagiAmt']>;
  zindagiNo: FormControl<KmDetailsFormRawValue['zindagiNo']>;
  surveyNo: FormControl<KmDetailsFormRawValue['surveyNo']>;
  landValue: FormControl<KmDetailsFormRawValue['landValue']>;
  landValueMr: FormControl<KmDetailsFormRawValue['landValueMr']>;
  eAgreementAmt: FormControl<KmDetailsFormRawValue['eAgreementAmt']>;
  eAgreementAmtMr: FormControl<KmDetailsFormRawValue['eAgreementAmtMr']>;
  eAgreementDate: FormControl<KmDetailsFormRawValue['eAgreementDate']>;
  eAgreementDateMr: FormControl<KmDetailsFormRawValue['eAgreementDateMr']>;
  bojaAmount: FormControl<KmDetailsFormRawValue['bojaAmount']>;
  bojaAmountMr: FormControl<KmDetailsFormRawValue['bojaAmountMr']>;
  bojaDate: FormControl<KmDetailsFormRawValue['bojaDate']>;
  bojaDateMr: FormControl<KmDetailsFormRawValue['bojaDateMr']>;
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
        }
      ),
      shares: new FormControl(kmDetailsRawValue.shares, {
        validators: [Validators.required],
      }),
      sharesMr: new FormControl(kmDetailsRawValue.sharesMr),
      sugarShares: new FormControl(kmDetailsRawValue.sugarShares, {
        validators: [Validators.required],
      }),
      sugarSharesMr: new FormControl(kmDetailsRawValue.sugarSharesMr),
      deposite: new FormControl(kmDetailsRawValue.deposite, {
        validators: [Validators.required],
      }),
      depositeMr: new FormControl(kmDetailsRawValue.depositeMr),
      dueLoan: new FormControl(kmDetailsRawValue.dueLoan, {
        validators: [Validators.required],
      }),
      dueLoanMr: new FormControl(kmDetailsRawValue.dueLoanMr),
      dueAmount: new FormControl(kmDetailsRawValue.dueAmount, {
        validators: [Validators.required],
      }),
      dueAmountMr: new FormControl(kmDetailsRawValue.dueAmountMr),
      dueDateMr: new FormControl(kmDetailsRawValue.dueDateMr),
      dueDate: new FormControl(kmDetailsRawValue.dueDate, {
        validators: [Validators.required],
      }),
      kmDate: new FormControl(kmDetailsRawValue.kmDate, {
        validators: [Validators.required],
      }),
      kmDateMr: new FormControl(kmDetailsRawValue.kmDateMr),
      kmFromDate: new FormControl(kmDetailsRawValue.kmFromDate, {
        validators: [Validators.required],
      }),
      kmFromDateMr: new FormControl(kmDetailsRawValue.kmFromDateMr),
      kmToDate: new FormControl(kmDetailsRawValue.kmToDate, {
        validators: [Validators.required],
      }),
      kmToDateMr: new FormControl(kmDetailsRawValue.kmToDateMr),
      bagayatHector: new FormControl(kmDetailsRawValue.bagayatHector, {
        validators: [Validators.required],
      }),
      bagayatHectorMr: new FormControl(kmDetailsRawValue.bagayatHectorMr),
      bagayatAre: new FormControl(kmDetailsRawValue.bagayatAre, {
        validators: [Validators.required],
      }),
      bagayatAreMr: new FormControl(kmDetailsRawValue.bagayatAreMr),
      jirayatHector: new FormControl(kmDetailsRawValue.jirayatHector, {
        validators: [Validators.required],
      }),
      jirayatHectorMr: new FormControl(kmDetailsRawValue.jirayatHectorMr),
      jirayatAre: new FormControl(kmDetailsRawValue.jirayatAre, {
        validators: [Validators.required],
      }),
      jirayatAreMr: new FormControl(kmDetailsRawValue.jirayatAreMr),
      zindagiAmt: new FormControl(kmDetailsRawValue.zindagiAmt, {
        validators: [Validators.required],
      }),
      zindagiNo: new FormControl(kmDetailsRawValue.zindagiNo, {
        validators: [Validators.required],
      }),
      surveyNo: new FormControl(kmDetailsRawValue.surveyNo, {
        validators: [Validators.required, Validators.maxLength(1000)],
      }),
      landValue: new FormControl(kmDetailsRawValue.landValue, {
        validators: [Validators.required],
      }),
      landValueMr: new FormControl(kmDetailsRawValue.landValueMr),
      eAgreementAmt: new FormControl(kmDetailsRawValue.eAgreementAmt, {
        validators: [Validators.required],
      }),
      eAgreementAmtMr: new FormControl(kmDetailsRawValue.eAgreementAmtMr),
      eAgreementDate: new FormControl(kmDetailsRawValue.eAgreementDate, {
        validators: [Validators.required],
      }),
      eAgreementDateMr: new FormControl(kmDetailsRawValue.eAgreementDateMr),
      bojaAmount: new FormControl(kmDetailsRawValue.bojaAmount, {
        validators: [Validators.required],
      }),
      bojaAmountMr: new FormControl(kmDetailsRawValue.bojaAmountMr),
      bojaDate: new FormControl(kmDetailsRawValue.bojaDate, {
        validators: [Validators.required],
      }),
      bojaDateMr: new FormControl(kmDetailsRawValue.bojaDateMr),
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
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
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
    kmDetails: IKmDetails | (Partial<NewKmDetails> & KmDetailsFormDefaults)
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
