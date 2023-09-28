import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ILoanDemandKMPatrak, NewLoanDemandKMPatrak } from '../loan-demand-km-patrak.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ILoanDemandKMPatrak for edit and NewLoanDemandKMPatrakFormGroupInput for create.
 */
type LoanDemandKMPatrakFormGroupInput = ILoanDemandKMPatrak | PartialWithRequiredKeyOf<NewLoanDemandKMPatrak>;

type LoanDemandKMPatrakFormDefaults = Pick<NewLoanDemandKMPatrak, 'id'>;

type LoanDemandKMPatrakFormGroupContent = {
  id: FormControl<ILoanDemandKMPatrak['id'] | NewLoanDemandKMPatrak['id']>;
  demandCode: FormControl<ILoanDemandKMPatrak['demandCode']>;
  date: FormControl<ILoanDemandKMPatrak['date']>;
  kmDate: FormControl<ILoanDemandKMPatrak['kmDate']>;
  shares: FormControl<ILoanDemandKMPatrak['shares']>;
  pid: FormControl<ILoanDemandKMPatrak['pid']>;
  code: FormControl<ILoanDemandKMPatrak['code']>;
  demandArea: FormControl<ILoanDemandKMPatrak['demandArea']>;
  cropType: FormControl<ILoanDemandKMPatrak['cropType']>;
  total: FormControl<ILoanDemandKMPatrak['total']>;
  check: FormControl<ILoanDemandKMPatrak['check']>;
  goods: FormControl<ILoanDemandKMPatrak['goods']>;
  sharesn: FormControl<ILoanDemandKMPatrak['sharesn']>;
  hn: FormControl<ILoanDemandKMPatrak['hn']>;
  area: FormControl<ILoanDemandKMPatrak['area']>;
  hAmount: FormControl<ILoanDemandKMPatrak['hAmount']>;
  name: FormControl<ILoanDemandKMPatrak['name']>;
  khateCode: FormControl<ILoanDemandKMPatrak['khateCode']>;
  remaining: FormControl<ILoanDemandKMPatrak['remaining']>;
  arrears: FormControl<ILoanDemandKMPatrak['arrears']>;
  kmAcceptance: FormControl<ILoanDemandKMPatrak['kmAcceptance']>;
  paidDate: FormControl<ILoanDemandKMPatrak['paidDate']>;
  kmCode: FormControl<ILoanDemandKMPatrak['kmCode']>;
  pendingDate: FormControl<ILoanDemandKMPatrak['pendingDate']>;
  depositeDate: FormControl<ILoanDemandKMPatrak['depositeDate']>;
  accountNumberB: FormControl<ILoanDemandKMPatrak['accountNumberB']>;
  loanDue: FormControl<ILoanDemandKMPatrak['loanDue']>;
  arrearsB: FormControl<ILoanDemandKMPatrak['arrearsB']>;
  dueDateB: FormControl<ILoanDemandKMPatrak['dueDateB']>;
  cropB: FormControl<ILoanDemandKMPatrak['cropB']>;
  kmAcceptanceB: FormControl<ILoanDemandKMPatrak['kmAcceptanceB']>;
  kmCodeB: FormControl<ILoanDemandKMPatrak['kmCodeB']>;
  hAgreementNumberB: FormControl<ILoanDemandKMPatrak['hAgreementNumberB']>;
  hAgreementAreaB: FormControl<ILoanDemandKMPatrak['hAgreementAreaB']>;
  hAgreementBurdenB: FormControl<ILoanDemandKMPatrak['hAgreementBurdenB']>;
  totalPaidB: FormControl<ILoanDemandKMPatrak['totalPaidB']>;
  demandAreaB: FormControl<ILoanDemandKMPatrak['demandAreaB']>;
  checkInTheFormOfPaymentB: FormControl<ILoanDemandKMPatrak['checkInTheFormOfPaymentB']>;
  sharesB: FormControl<ILoanDemandKMPatrak['sharesB']>;
  vasulPatraRepaymentDateB: FormControl<ILoanDemandKMPatrak['vasulPatraRepaymentDateB']>;
};

export type LoanDemandKMPatrakFormGroup = FormGroup<LoanDemandKMPatrakFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class LoanDemandKMPatrakFormService {
  createLoanDemandKMPatrakFormGroup(loanDemandKMPatrak: LoanDemandKMPatrakFormGroupInput = { id: null }): LoanDemandKMPatrakFormGroup {
    const loanDemandKMPatrakRawValue = {
      ...this.getFormDefaults(),
      ...loanDemandKMPatrak,
    };
    return new FormGroup<LoanDemandKMPatrakFormGroupContent>({
      id: new FormControl(
        { value: loanDemandKMPatrakRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      demandCode: new FormControl(loanDemandKMPatrakRawValue.demandCode),
      date: new FormControl(loanDemandKMPatrakRawValue.date),
      kmDate: new FormControl(loanDemandKMPatrakRawValue.kmDate),
      shares: new FormControl(loanDemandKMPatrakRawValue.shares),
      pid: new FormControl(loanDemandKMPatrakRawValue.pid),
      code: new FormControl(loanDemandKMPatrakRawValue.code),
      demandArea: new FormControl(loanDemandKMPatrakRawValue.demandArea),
      cropType: new FormControl(loanDemandKMPatrakRawValue.cropType),
      total: new FormControl(loanDemandKMPatrakRawValue.total),
      check: new FormControl(loanDemandKMPatrakRawValue.check),
      goods: new FormControl(loanDemandKMPatrakRawValue.goods),
      sharesn: new FormControl(loanDemandKMPatrakRawValue.sharesn),
      hn: new FormControl(loanDemandKMPatrakRawValue.hn),
      area: new FormControl(loanDemandKMPatrakRawValue.area),
      hAmount: new FormControl(loanDemandKMPatrakRawValue.hAmount),
      name: new FormControl(loanDemandKMPatrakRawValue.name),
      khateCode: new FormControl(loanDemandKMPatrakRawValue.khateCode),
      remaining: new FormControl(loanDemandKMPatrakRawValue.remaining),
      arrears: new FormControl(loanDemandKMPatrakRawValue.arrears),
      kmAcceptance: new FormControl(loanDemandKMPatrakRawValue.kmAcceptance),
      paidDate: new FormControl(loanDemandKMPatrakRawValue.paidDate),
      kmCode: new FormControl(loanDemandKMPatrakRawValue.kmCode),
      pendingDate: new FormControl(loanDemandKMPatrakRawValue.pendingDate),
      depositeDate: new FormControl(loanDemandKMPatrakRawValue.depositeDate),
      accountNumberB: new FormControl(loanDemandKMPatrakRawValue.accountNumberB),
      loanDue: new FormControl(loanDemandKMPatrakRawValue.loanDue),
      arrearsB: new FormControl(loanDemandKMPatrakRawValue.arrearsB),
      dueDateB: new FormControl(loanDemandKMPatrakRawValue.dueDateB),
      cropB: new FormControl(loanDemandKMPatrakRawValue.cropB),
      kmAcceptanceB: new FormControl(loanDemandKMPatrakRawValue.kmAcceptanceB),
      kmCodeB: new FormControl(loanDemandKMPatrakRawValue.kmCodeB),
      hAgreementNumberB: new FormControl(loanDemandKMPatrakRawValue.hAgreementNumberB),
      hAgreementAreaB: new FormControl(loanDemandKMPatrakRawValue.hAgreementAreaB),
      hAgreementBurdenB: new FormControl(loanDemandKMPatrakRawValue.hAgreementBurdenB),
      totalPaidB: new FormControl(loanDemandKMPatrakRawValue.totalPaidB),
      demandAreaB: new FormControl(loanDemandKMPatrakRawValue.demandAreaB),
      checkInTheFormOfPaymentB: new FormControl(loanDemandKMPatrakRawValue.checkInTheFormOfPaymentB),
      sharesB: new FormControl(loanDemandKMPatrakRawValue.sharesB),
      vasulPatraRepaymentDateB: new FormControl(loanDemandKMPatrakRawValue.vasulPatraRepaymentDateB),
    });
  }

  getLoanDemandKMPatrak(form: LoanDemandKMPatrakFormGroup): ILoanDemandKMPatrak | NewLoanDemandKMPatrak {
    return form.getRawValue() as ILoanDemandKMPatrak | NewLoanDemandKMPatrak;
  }

  resetForm(form: LoanDemandKMPatrakFormGroup, loanDemandKMPatrak: LoanDemandKMPatrakFormGroupInput): void {
    const loanDemandKMPatrakRawValue = { ...this.getFormDefaults(), ...loanDemandKMPatrak };
    form.reset(
      {
        ...loanDemandKMPatrakRawValue,
        id: { value: loanDemandKMPatrakRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): LoanDemandKMPatrakFormDefaults {
    return {
      id: null,
    };
  }
}
