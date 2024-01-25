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
type FormValueOf<T extends IKamalSociety | NewKamalSociety> = Omit<T, 'zindagiDate'> & {
  zindagiDate?: string | null;
};

type KamalSocietyFormRawValue = FormValueOf<IKamalSociety>;

type NewKamalSocietyFormRawValue = FormValueOf<NewKamalSociety>;

type KamalSocietyFormDefaults = Pick<NewKamalSociety, 'id' | 'zindagiDate'>;

type KamalSocietyFormGroupContent = {
  id: FormControl<KamalSocietyFormRawValue['id'] | NewKamalSociety['id']>;
  pacsNumber: FormControl<KamalSocietyFormRawValue['pacsNumber']>;
  zindagiDate: FormControl<KamalSocietyFormRawValue['zindagiDate']>;
  zindagiDateMr: FormControl<KamalSocietyFormRawValue['zindagiDateMr']>;
  village1: FormControl<KamalSocietyFormRawValue['village1']>;
  village1Mr: FormControl<KamalSocietyFormRawValue['village1Mr']>;
  village2: FormControl<KamalSocietyFormRawValue['village2']>;
  village2Mr: FormControl<KamalSocietyFormRawValue['village2Mr']>;
  village3: FormControl<KamalSocietyFormRawValue['village3']>;
  village3Mr: FormControl<KamalSocietyFormRawValue['village3Mr']>;
  totalLand: FormControl<KamalSocietyFormRawValue['totalLand']>;
  totalLandMr: FormControl<KamalSocietyFormRawValue['totalLandMr']>;
  totalMem: FormControl<KamalSocietyFormRawValue['totalMem']>;
  totalMemMr: FormControl<KamalSocietyFormRawValue['totalMemMr']>;
  totalNonMem: FormControl<KamalSocietyFormRawValue['totalNonMem']>;
  totalNonMemMr: FormControl<KamalSocietyFormRawValue['totalNonMemMr']>;
  totalGMem: FormControl<KamalSocietyFormRawValue['totalGMem']>;
  totalGMemMr: FormControl<KamalSocietyFormRawValue['totalGMemMr']>;
  memLoan: FormControl<KamalSocietyFormRawValue['memLoan']>;
  memLoanMr: FormControl<KamalSocietyFormRawValue['memLoanMr']>;
  memDue: FormControl<KamalSocietyFormRawValue['memDue']>;
  memDueMr: FormControl<KamalSocietyFormRawValue['memDueMr']>;
  memDueper: FormControl<KamalSocietyFormRawValue['memDueper']>;
  memDueperMr: FormControl<KamalSocietyFormRawValue['memDueperMr']>;
  memVasulpatra: FormControl<KamalSocietyFormRawValue['memVasulpatra']>;
  memVasulpatraMr: FormControl<KamalSocietyFormRawValue['memVasulpatraMr']>;
  memVasul: FormControl<KamalSocietyFormRawValue['memVasul']>;
  memVasulMr: FormControl<KamalSocietyFormRawValue['memVasulMr']>;
  memVasulPer: FormControl<KamalSocietyFormRawValue['memVasulPer']>;
  memVasulPerMr: FormControl<KamalSocietyFormRawValue['memVasulPerMr']>;
  bankLoan: FormControl<KamalSocietyFormRawValue['bankLoan']>;
  bankLoanMr: FormControl<KamalSocietyFormRawValue['bankLoanMr']>;
  bankDue: FormControl<KamalSocietyFormRawValue['bankDue']>;
  bankDueMr: FormControl<KamalSocietyFormRawValue['bankDueMr']>;
  bankDueper: FormControl<KamalSocietyFormRawValue['bankDueper']>;
  bankDueperMr: FormControl<KamalSocietyFormRawValue['bankDueperMr']>;
  bankVasulpatra: FormControl<KamalSocietyFormRawValue['bankVasulpatra']>;
  bankVasulpatraMr: FormControl<KamalSocietyFormRawValue['bankVasulpatraMr']>;
  bankVasul: FormControl<KamalSocietyFormRawValue['bankVasul']>;
  bankVasulMr: FormControl<KamalSocietyFormRawValue['bankVasulMr']>;
  bankVasulPer: FormControl<KamalSocietyFormRawValue['bankVasulPer']>;
  bankVasulPerMr: FormControl<KamalSocietyFormRawValue['bankVasulPerMr']>;
  shareCapital: FormControl<KamalSocietyFormRawValue['shareCapital']>;
  shareCapitalMr: FormControl<KamalSocietyFormRawValue['shareCapitalMr']>;
  share: FormControl<KamalSocietyFormRawValue['share']>;
  shareMr: FormControl<KamalSocietyFormRawValue['shareMr']>;
  funds: FormControl<KamalSocietyFormRawValue['funds']>;
  fundsMr: FormControl<KamalSocietyFormRawValue['fundsMr']>;
  deposit: FormControl<KamalSocietyFormRawValue['deposit']>;
  depositMr: FormControl<KamalSocietyFormRawValue['depositMr']>;
  payable: FormControl<KamalSocietyFormRawValue['payable']>;
  payableMr: FormControl<KamalSocietyFormRawValue['payableMr']>;
  profit: FormControl<KamalSocietyFormRawValue['profit']>;
  profitMr: FormControl<KamalSocietyFormRawValue['profitMr']>;
  cashInHand: FormControl<KamalSocietyFormRawValue['cashInHand']>;
  cashInHandMr: FormControl<KamalSocietyFormRawValue['cashInHandMr']>;
  investment: FormControl<KamalSocietyFormRawValue['investment']>;
  investmentMr: FormControl<KamalSocietyFormRawValue['investmentMr']>;
  deadStock: FormControl<KamalSocietyFormRawValue['deadStock']>;
  deadStockMr: FormControl<KamalSocietyFormRawValue['deadStockMr']>;
  otherPay: FormControl<KamalSocietyFormRawValue['otherPay']>;
  otherPayMr: FormControl<KamalSocietyFormRawValue['otherPayMr']>;
  loss: FormControl<KamalSocietyFormRawValue['loss']>;
  lossMr: FormControl<KamalSocietyFormRawValue['lossMr']>;
  totalBagayat: FormControl<KamalSocietyFormRawValue['totalBagayat']>;
  totalBagayatMr: FormControl<KamalSocietyFormRawValue['totalBagayatMr']>;
  totalJirayat: FormControl<KamalSocietyFormRawValue['totalJirayat']>;
  totalJirayatMr: FormControl<KamalSocietyFormRawValue['totalJirayatMr']>;
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
      pacsNumber: new FormControl(kamalSocietyRawValue.pacsNumber),
      zindagiDate: new FormControl(kamalSocietyRawValue.zindagiDate),
      zindagiDateMr: new FormControl(kamalSocietyRawValue.zindagiDateMr),
      village1: new FormControl(kamalSocietyRawValue.village1),
      village1Mr: new FormControl(kamalSocietyRawValue.village1Mr),
      village2: new FormControl(kamalSocietyRawValue.village2),
      village2Mr: new FormControl(kamalSocietyRawValue.village2Mr),
      village3: new FormControl(kamalSocietyRawValue.village3),
      village3Mr: new FormControl(kamalSocietyRawValue.village3Mr),
      totalLand: new FormControl(kamalSocietyRawValue.totalLand),
      totalLandMr: new FormControl(kamalSocietyRawValue.totalLandMr),
      totalMem: new FormControl(kamalSocietyRawValue.totalMem),
      totalMemMr: new FormControl(kamalSocietyRawValue.totalMemMr),
      totalNonMem: new FormControl(kamalSocietyRawValue.totalNonMem),
      totalNonMemMr: new FormControl(kamalSocietyRawValue.totalNonMemMr),
      totalGMem: new FormControl(kamalSocietyRawValue.totalGMem),
      totalGMemMr: new FormControl(kamalSocietyRawValue.totalGMemMr),
      memLoan: new FormControl(kamalSocietyRawValue.memLoan),
      memLoanMr: new FormControl(kamalSocietyRawValue.memLoanMr),
      memDue: new FormControl(kamalSocietyRawValue.memDue),
      memDueMr: new FormControl(kamalSocietyRawValue.memDueMr),
      memDueper: new FormControl(kamalSocietyRawValue.memDueper),
      memDueperMr: new FormControl(kamalSocietyRawValue.memDueperMr),
      memVasulpatra: new FormControl(kamalSocietyRawValue.memVasulpatra),
      memVasulpatraMr: new FormControl(kamalSocietyRawValue.memVasulpatraMr),
      memVasul: new FormControl(kamalSocietyRawValue.memVasul),
      memVasulMr: new FormControl(kamalSocietyRawValue.memVasulMr),
      memVasulPer: new FormControl(kamalSocietyRawValue.memVasulPer),
      memVasulPerMr: new FormControl(kamalSocietyRawValue.memVasulPerMr),
      bankLoan: new FormControl(kamalSocietyRawValue.bankLoan),
      bankLoanMr: new FormControl(kamalSocietyRawValue.bankLoanMr),
      bankDue: new FormControl(kamalSocietyRawValue.bankDue),
      bankDueMr: new FormControl(kamalSocietyRawValue.bankDueMr),
      bankDueper: new FormControl(kamalSocietyRawValue.bankDueper),
      bankDueperMr: new FormControl(kamalSocietyRawValue.bankDueperMr),
      bankVasulpatra: new FormControl(kamalSocietyRawValue.bankVasulpatra),
      bankVasulpatraMr: new FormControl(kamalSocietyRawValue.bankVasulpatraMr),
      bankVasul: new FormControl(kamalSocietyRawValue.bankVasul),
      bankVasulMr: new FormControl(kamalSocietyRawValue.bankVasulMr),
      bankVasulPer: new FormControl(kamalSocietyRawValue.bankVasulPer),
      bankVasulPerMr: new FormControl(kamalSocietyRawValue.bankVasulPerMr),
      shareCapital: new FormControl(kamalSocietyRawValue.shareCapital),
      shareCapitalMr: new FormControl(kamalSocietyRawValue.shareCapitalMr),
      share: new FormControl(kamalSocietyRawValue.share),
      shareMr: new FormControl(kamalSocietyRawValue.shareMr),
      funds: new FormControl(kamalSocietyRawValue.funds),
      fundsMr: new FormControl(kamalSocietyRawValue.fundsMr),
      deposit: new FormControl(kamalSocietyRawValue.deposit),
      depositMr: new FormControl(kamalSocietyRawValue.depositMr),
      payable: new FormControl(kamalSocietyRawValue.payable),
      payableMr: new FormControl(kamalSocietyRawValue.payableMr),
      profit: new FormControl(kamalSocietyRawValue.profit),
      profitMr: new FormControl(kamalSocietyRawValue.profitMr),
      cashInHand: new FormControl(kamalSocietyRawValue.cashInHand),
      cashInHandMr: new FormControl(kamalSocietyRawValue.cashInHandMr),
      investment: new FormControl(kamalSocietyRawValue.investment),
      investmentMr: new FormControl(kamalSocietyRawValue.investmentMr),
      deadStock: new FormControl(kamalSocietyRawValue.deadStock),
      deadStockMr: new FormControl(kamalSocietyRawValue.deadStockMr),
      otherPay: new FormControl(kamalSocietyRawValue.otherPay),
      otherPayMr: new FormControl(kamalSocietyRawValue.otherPayMr),
      loss: new FormControl(kamalSocietyRawValue.loss),
      lossMr: new FormControl(kamalSocietyRawValue.lossMr),
      totalBagayat: new FormControl(kamalSocietyRawValue.totalBagayat),
      totalBagayatMr: new FormControl(kamalSocietyRawValue.totalBagayatMr),
      totalJirayat: new FormControl(kamalSocietyRawValue.totalJirayat),
      totalJirayatMr: new FormControl(kamalSocietyRawValue.totalJirayatMr),
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
      zindagiDate: currentTime,
    };
  }

  private convertKamalSocietyRawValueToKamalSociety(
    rawKamalSociety: KamalSocietyFormRawValue | NewKamalSocietyFormRawValue
  ): IKamalSociety | NewKamalSociety {
    return {
      ...rawKamalSociety,
      zindagiDate: dayjs(rawKamalSociety.zindagiDate, DATE_TIME_FORMAT),
    };
  }

  private convertKamalSocietyToKamalSocietyRawValue(
    kamalSociety: IKamalSociety | (Partial<NewKamalSociety> & KamalSocietyFormDefaults)
  ): KamalSocietyFormRawValue | PartialWithRequiredKeyOf<NewKamalSocietyFormRawValue> {
    return {
      ...kamalSociety,
      zindagiDate: kamalSociety.zindagiDate ? kamalSociety.zindagiDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
