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
type FormValueOf<T extends IKamalSociety | NewKamalSociety> = Omit<
  T,
  | 'kmDate'
  | 'kmFromDate'
  | 'kmToDate'
  | 'zindagiPatrakDate'
  | 'bankTapasaniDate'
  | 'govTapasaniDate'
  | 'sansthaTapasaniDate'
  | 'talebandDate'
  | 'balanceSheetDate'
> & {
  kmDate?: string | null;
  kmFromDate?: string | null;
  kmToDate?: string | null;
  zindagiPatrakDate?: string | null;
  bankTapasaniDate?: string | null;
  govTapasaniDate?: string | null;
  sansthaTapasaniDate?: string | null;
  talebandDate?: string | null;
  balanceSheetDate?: string | null;
};

type KamalSocietyFormRawValue = FormValueOf<IKamalSociety>;

type NewKamalSocietyFormRawValue = FormValueOf<NewKamalSociety>;

type KamalSocietyFormDefaults = Pick<
  NewKamalSociety,
  | 'id'
  | 'kmDate'
  | 'kmFromDate'
  | 'kmToDate'
  | 'zindagiPatrakDate'
  | 'bankTapasaniDate'
  | 'govTapasaniDate'
  | 'sansthaTapasaniDate'
  | 'talebandDate'
  | 'balanceSheetDate'
  | 'pacsVerifiedFlag'
  | 'branchVerifiedFlag'
  | 'headOfficeVerifiedFlag'
  | 'isSupplimenteryFlag'
>;

type KamalSocietyFormGroupContent = {
  id: FormControl<KamalSocietyFormRawValue['id'] | NewKamalSociety['id']>;
  financialYear: FormControl<KamalSocietyFormRawValue['financialYear']>;
  kmDate: FormControl<KamalSocietyFormRawValue['kmDate']>;
  kmDateMr: FormControl<KamalSocietyFormRawValue['kmDateMr']>;
  kmFromDate: FormControl<KamalSocietyFormRawValue['kmFromDate']>;
  kmFromDateMr: FormControl<KamalSocietyFormRawValue['kmFromDateMr']>;
  kmToDate: FormControl<KamalSocietyFormRawValue['kmToDate']>;
  kmToDateMr: FormControl<KamalSocietyFormRawValue['kmToDateMr']>;
  pacsNumber: FormControl<KamalSocietyFormRawValue['pacsNumber']>;
  zindagiPatrakDate: FormControl<KamalSocietyFormRawValue['zindagiPatrakDate']>;
  zindagiPatrakDateMr: FormControl<KamalSocietyFormRawValue['zindagiPatrakDateMr']>;
  bankTapasaniDate: FormControl<KamalSocietyFormRawValue['bankTapasaniDate']>;
  bankTapasaniDateMr: FormControl<KamalSocietyFormRawValue['bankTapasaniDateMr']>;
  govTapasaniDate: FormControl<KamalSocietyFormRawValue['govTapasaniDate']>;
  govTapasaniDateMr: FormControl<KamalSocietyFormRawValue['govTapasaniDateMr']>;
  sansthaTapasaniDate: FormControl<KamalSocietyFormRawValue['sansthaTapasaniDate']>;
  sansthaTapasaniDateMr: FormControl<KamalSocietyFormRawValue['sansthaTapasaniDateMr']>;
  totalLand: FormControl<KamalSocietyFormRawValue['totalLand']>;
  bagayat: FormControl<KamalSocietyFormRawValue['bagayat']>;
  jirayat: FormControl<KamalSocietyFormRawValue['jirayat']>;
  totalFarmer: FormControl<KamalSocietyFormRawValue['totalFarmer']>;
  memberFarmer: FormControl<KamalSocietyFormRawValue['memberFarmer']>;
  nonMemberFarmer: FormControl<KamalSocietyFormRawValue['nonMemberFarmer']>;
  talebandDate: FormControl<KamalSocietyFormRawValue['talebandDate']>;
  memLoan: FormControl<KamalSocietyFormRawValue['memLoan']>;
  memDue: FormControl<KamalSocietyFormRawValue['memDue']>;
  memVasuli: FormControl<KamalSocietyFormRawValue['memVasuli']>;
  memVasuliPer: FormControl<KamalSocietyFormRawValue['memVasuliPer']>;
  bankLoan: FormControl<KamalSocietyFormRawValue['bankLoan']>;
  bankDue: FormControl<KamalSocietyFormRawValue['bankDue']>;
  bankVasuli: FormControl<KamalSocietyFormRawValue['bankVasuli']>;
  bankVasuliPer: FormControl<KamalSocietyFormRawValue['bankVasuliPer']>;
  balanceSheetDate: FormControl<KamalSocietyFormRawValue['balanceSheetDate']>;
  balanceSheetDateMr: FormControl<KamalSocietyFormRawValue['balanceSheetDateMr']>;
  liabilityAdhikrutShareCapital: FormControl<KamalSocietyFormRawValue['liabilityAdhikrutShareCapital']>;
  liabilityVasulShareCapital: FormControl<KamalSocietyFormRawValue['liabilityVasulShareCapital']>;
  liabilityFund: FormControl<KamalSocietyFormRawValue['liabilityFund']>;
  liabilityDeposite: FormControl<KamalSocietyFormRawValue['liabilityDeposite']>;
  liabilityBalanceSheetBankLoan: FormControl<KamalSocietyFormRawValue['liabilityBalanceSheetBankLoan']>;
  liabilityOtherPayable: FormControl<KamalSocietyFormRawValue['liabilityOtherPayable']>;
  liabilityProfit: FormControl<KamalSocietyFormRawValue['liabilityProfit']>;
  assetCash: FormControl<KamalSocietyFormRawValue['assetCash']>;
  assetInvestment: FormControl<KamalSocietyFormRawValue['assetInvestment']>;
  assetImaratFund: FormControl<KamalSocietyFormRawValue['assetImaratFund']>;
  assetMemberLoan: FormControl<KamalSocietyFormRawValue['assetMemberLoan']>;
  assetDeadStock: FormControl<KamalSocietyFormRawValue['assetDeadStock']>;
  assetOtherReceivable: FormControl<KamalSocietyFormRawValue['assetOtherReceivable']>;
  assetLoss: FormControl<KamalSocietyFormRawValue['assetLoss']>;
  totalLiability: FormControl<KamalSocietyFormRawValue['totalLiability']>;
  totalAsset: FormControl<KamalSocietyFormRawValue['totalAsset']>;
  villageCode: FormControl<KamalSocietyFormRawValue['villageCode']>;
  pacsVerifiedFlag: FormControl<KamalSocietyFormRawValue['pacsVerifiedFlag']>;
  branchVerifiedFlag: FormControl<KamalSocietyFormRawValue['branchVerifiedFlag']>;
  headOfficeVerifiedFlag: FormControl<KamalSocietyFormRawValue['headOfficeVerifiedFlag']>;
  isSupplimenteryFlag: FormControl<KamalSocietyFormRawValue['isSupplimenteryFlag']>;
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
      kmDateMr: new FormControl(kamalSocietyRawValue.kmDateMr),
      kmFromDate: new FormControl(kamalSocietyRawValue.kmFromDate),
      kmFromDateMr: new FormControl(kamalSocietyRawValue.kmFromDateMr),
      kmToDate: new FormControl(kamalSocietyRawValue.kmToDate),
      kmToDateMr: new FormControl(kamalSocietyRawValue.kmToDateMr),
      pacsNumber: new FormControl(kamalSocietyRawValue.pacsNumber),
      zindagiPatrakDate: new FormControl(kamalSocietyRawValue.zindagiPatrakDate),
      zindagiPatrakDateMr: new FormControl(kamalSocietyRawValue.zindagiPatrakDateMr),
      bankTapasaniDate: new FormControl(kamalSocietyRawValue.bankTapasaniDate),
      bankTapasaniDateMr: new FormControl(kamalSocietyRawValue.bankTapasaniDateMr),
      govTapasaniDate: new FormControl(kamalSocietyRawValue.govTapasaniDate),
      govTapasaniDateMr: new FormControl(kamalSocietyRawValue.govTapasaniDateMr),
      sansthaTapasaniDate: new FormControl(kamalSocietyRawValue.sansthaTapasaniDate),
      sansthaTapasaniDateMr: new FormControl(kamalSocietyRawValue.sansthaTapasaniDateMr),
      totalLand: new FormControl(kamalSocietyRawValue.totalLand),
      bagayat: new FormControl(kamalSocietyRawValue.bagayat),
      jirayat: new FormControl(kamalSocietyRawValue.jirayat),
      totalFarmer: new FormControl(kamalSocietyRawValue.totalFarmer),
      memberFarmer: new FormControl(kamalSocietyRawValue.memberFarmer),
      nonMemberFarmer: new FormControl(kamalSocietyRawValue.nonMemberFarmer),
      talebandDate: new FormControl(kamalSocietyRawValue.talebandDate),
      memLoan: new FormControl(kamalSocietyRawValue.memLoan),
      memDue: new FormControl(kamalSocietyRawValue.memDue),
      memVasuli: new FormControl(kamalSocietyRawValue.memVasuli),
      memVasuliPer: new FormControl(kamalSocietyRawValue.memVasuliPer),
      bankLoan: new FormControl(kamalSocietyRawValue.bankLoan),
      bankDue: new FormControl(kamalSocietyRawValue.bankDue),
      bankVasuli: new FormControl(kamalSocietyRawValue.bankVasuli),
      bankVasuliPer: new FormControl(kamalSocietyRawValue.bankVasuliPer),
      balanceSheetDate: new FormControl(kamalSocietyRawValue.balanceSheetDate),
      balanceSheetDateMr: new FormControl(kamalSocietyRawValue.balanceSheetDateMr),
      liabilityAdhikrutShareCapital: new FormControl(kamalSocietyRawValue.liabilityAdhikrutShareCapital),
      liabilityVasulShareCapital: new FormControl(kamalSocietyRawValue.liabilityVasulShareCapital),
      liabilityFund: new FormControl(kamalSocietyRawValue.liabilityFund),
      liabilityDeposite: new FormControl(kamalSocietyRawValue.liabilityDeposite),
      liabilityBalanceSheetBankLoan: new FormControl(kamalSocietyRawValue.liabilityBalanceSheetBankLoan),
      liabilityOtherPayable: new FormControl(kamalSocietyRawValue.liabilityOtherPayable),
      liabilityProfit: new FormControl(kamalSocietyRawValue.liabilityProfit),
      assetCash: new FormControl(kamalSocietyRawValue.assetCash),
      assetInvestment: new FormControl(kamalSocietyRawValue.assetInvestment),
      assetImaratFund: new FormControl(kamalSocietyRawValue.assetImaratFund),
      assetMemberLoan: new FormControl(kamalSocietyRawValue.assetMemberLoan),
      assetDeadStock: new FormControl(kamalSocietyRawValue.assetDeadStock),
      assetOtherReceivable: new FormControl(kamalSocietyRawValue.assetOtherReceivable),
      assetLoss: new FormControl(kamalSocietyRawValue.assetLoss),
      totalLiability: new FormControl(kamalSocietyRawValue.totalLiability),
      totalAsset: new FormControl(kamalSocietyRawValue.totalAsset),
      villageCode: new FormControl(kamalSocietyRawValue.villageCode),
      pacsVerifiedFlag: new FormControl(kamalSocietyRawValue.pacsVerifiedFlag),
      branchVerifiedFlag: new FormControl(kamalSocietyRawValue.branchVerifiedFlag),
      headOfficeVerifiedFlag: new FormControl(kamalSocietyRawValue.headOfficeVerifiedFlag),
      isSupplimenteryFlag: new FormControl(kamalSocietyRawValue.isSupplimenteryFlag),
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
      kmFromDate: currentTime,
      kmToDate: currentTime,
      zindagiPatrakDate: currentTime,
      bankTapasaniDate: currentTime,
      govTapasaniDate: currentTime,
      sansthaTapasaniDate: currentTime,
      talebandDate: currentTime,
      balanceSheetDate: currentTime,
      pacsVerifiedFlag: false,
      branchVerifiedFlag: false,
      headOfficeVerifiedFlag: false,
      isSupplimenteryFlag: false,
    };
  }

  private convertKamalSocietyRawValueToKamalSociety(
    rawKamalSociety: KamalSocietyFormRawValue | NewKamalSocietyFormRawValue
  ): IKamalSociety | NewKamalSociety {
    return {
      ...rawKamalSociety,
      kmDate: dayjs(rawKamalSociety.kmDate, DATE_TIME_FORMAT),
      kmFromDate: dayjs(rawKamalSociety.kmFromDate, DATE_TIME_FORMAT),
      kmToDate: dayjs(rawKamalSociety.kmToDate, DATE_TIME_FORMAT),
      zindagiPatrakDate: dayjs(rawKamalSociety.zindagiPatrakDate, DATE_TIME_FORMAT),
      bankTapasaniDate: dayjs(rawKamalSociety.bankTapasaniDate, DATE_TIME_FORMAT),
      govTapasaniDate: dayjs(rawKamalSociety.govTapasaniDate, DATE_TIME_FORMAT),
      sansthaTapasaniDate: dayjs(rawKamalSociety.sansthaTapasaniDate, DATE_TIME_FORMAT),
      talebandDate: dayjs(rawKamalSociety.talebandDate, DATE_TIME_FORMAT),
      balanceSheetDate: dayjs(rawKamalSociety.balanceSheetDate, DATE_TIME_FORMAT),
    };
  }

  private convertKamalSocietyToKamalSocietyRawValue(
    kamalSociety: IKamalSociety | (Partial<NewKamalSociety> & KamalSocietyFormDefaults)
  ): KamalSocietyFormRawValue | PartialWithRequiredKeyOf<NewKamalSocietyFormRawValue> {
    return {
      ...kamalSociety,
      kmDate: kamalSociety.kmDate ? kamalSociety.kmDate.format(DATE_TIME_FORMAT) : undefined,
      kmFromDate: kamalSociety.kmFromDate ? kamalSociety.kmFromDate.format(DATE_TIME_FORMAT) : undefined,
      kmToDate: kamalSociety.kmToDate ? kamalSociety.kmToDate.format(DATE_TIME_FORMAT) : undefined,
      zindagiPatrakDate: kamalSociety.zindagiPatrakDate ? kamalSociety.zindagiPatrakDate.format(DATE_TIME_FORMAT) : undefined,
      bankTapasaniDate: kamalSociety.bankTapasaniDate ? kamalSociety.bankTapasaniDate.format(DATE_TIME_FORMAT) : undefined,
      govTapasaniDate: kamalSociety.govTapasaniDate ? kamalSociety.govTapasaniDate.format(DATE_TIME_FORMAT) : undefined,
      sansthaTapasaniDate: kamalSociety.sansthaTapasaniDate ? kamalSociety.sansthaTapasaniDate.format(DATE_TIME_FORMAT) : undefined,
      talebandDate: kamalSociety.talebandDate ? kamalSociety.talebandDate.format(DATE_TIME_FORMAT) : undefined,
      balanceSheetDate: kamalSociety.balanceSheetDate ? kamalSociety.balanceSheetDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
