import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICourtCase, NewCourtCase } from '../court-case.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICourtCase for edit and NewCourtCaseFormGroupInput for create.
 */
type CourtCaseFormGroupInput = ICourtCase | PartialWithRequiredKeyOf<NewCourtCase>;

type CourtCaseFormDefaults = Pick<NewCourtCase, 'id'>;

type CourtCaseFormGroupContent = {
  id: FormControl<ICourtCase['id'] | NewCourtCase['id']>;
  srNo: FormControl<ICourtCase['srNo']>;
  accountNo: FormControl<ICourtCase['accountNo']>;
  nameOfDefaulter: FormControl<ICourtCase['nameOfDefaulter']>;
  address: FormControl<ICourtCase['address']>;
  loanType: FormControl<ICourtCase['loanType']>;
  loanAmount: FormControl<ICourtCase['loanAmount']>;
  loanDate: FormControl<ICourtCase['loanDate']>;
  termOfLoan: FormControl<ICourtCase['termOfLoan']>;
  interestRate: FormControl<ICourtCase['interestRate']>;
  installmentAmount: FormControl<ICourtCase['installmentAmount']>;
  totalCredit: FormControl<ICourtCase['totalCredit']>;
  balance: FormControl<ICourtCase['balance']>;
  interestPaid: FormControl<ICourtCase['interestPaid']>;
  penalInterestPaid: FormControl<ICourtCase['penalInterestPaid']>;
  dueAmount: FormControl<ICourtCase['dueAmount']>;
  dueDate: FormControl<ICourtCase['dueDate']>;
  dueInterest: FormControl<ICourtCase['dueInterest']>;
  duePenalInterest: FormControl<ICourtCase['duePenalInterest']>;
  dueMoreInterest: FormControl<ICourtCase['dueMoreInterest']>;
  interestRecivable: FormControl<ICourtCase['interestRecivable']>;
  gaurentorOne: FormControl<ICourtCase['gaurentorOne']>;
  gaurentorOneAddress: FormControl<ICourtCase['gaurentorOneAddress']>;
  gaurentorTwo: FormControl<ICourtCase['gaurentorTwo']>;
  gaurentorTwoAddress: FormControl<ICourtCase['gaurentorTwoAddress']>;
  firstNoticeDate: FormControl<ICourtCase['firstNoticeDate']>;
  secondNoticeDate: FormControl<ICourtCase['secondNoticeDate']>;
};

export type CourtCaseFormGroup = FormGroup<CourtCaseFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CourtCaseFormService {
  createCourtCaseFormGroup(courtCase: CourtCaseFormGroupInput = { id: null }): CourtCaseFormGroup {
    const courtCaseRawValue = {
      ...this.getFormDefaults(),
      ...courtCase,
    };
    return new FormGroup<CourtCaseFormGroupContent>({
      id: new FormControl(
        { value: courtCaseRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      srNo: new FormControl(courtCaseRawValue.srNo),
      accountNo: new FormControl(courtCaseRawValue.accountNo),
      nameOfDefaulter: new FormControl(courtCaseRawValue.nameOfDefaulter),
      address: new FormControl(courtCaseRawValue.address),
      loanType: new FormControl(courtCaseRawValue.loanType),
      loanAmount: new FormControl(courtCaseRawValue.loanAmount),
      loanDate: new FormControl(courtCaseRawValue.loanDate),
      termOfLoan: new FormControl(courtCaseRawValue.termOfLoan),
      interestRate: new FormControl(courtCaseRawValue.interestRate),
      installmentAmount: new FormControl(courtCaseRawValue.installmentAmount),
      totalCredit: new FormControl(courtCaseRawValue.totalCredit),
      balance: new FormControl(courtCaseRawValue.balance),
      interestPaid: new FormControl(courtCaseRawValue.interestPaid),
      penalInterestPaid: new FormControl(courtCaseRawValue.penalInterestPaid),
      dueAmount: new FormControl(courtCaseRawValue.dueAmount),
      dueDate: new FormControl(courtCaseRawValue.dueDate),
      dueInterest: new FormControl(courtCaseRawValue.dueInterest),
      duePenalInterest: new FormControl(courtCaseRawValue.duePenalInterest),
      dueMoreInterest: new FormControl(courtCaseRawValue.dueMoreInterest),
      interestRecivable: new FormControl(courtCaseRawValue.interestRecivable),
      gaurentorOne: new FormControl(courtCaseRawValue.gaurentorOne),
      gaurentorOneAddress: new FormControl(courtCaseRawValue.gaurentorOneAddress),
      gaurentorTwo: new FormControl(courtCaseRawValue.gaurentorTwo),
      gaurentorTwoAddress: new FormControl(courtCaseRawValue.gaurentorTwoAddress),
      firstNoticeDate: new FormControl(courtCaseRawValue.firstNoticeDate),
      secondNoticeDate: new FormControl(courtCaseRawValue.secondNoticeDate),
    });
  }

  getCourtCase(form: CourtCaseFormGroup): ICourtCase | NewCourtCase {
    return form.getRawValue() as ICourtCase | NewCourtCase;
  }

  resetForm(form: CourtCaseFormGroup, courtCase: CourtCaseFormGroupInput): void {
    const courtCaseRawValue = { ...this.getFormDefaults(), ...courtCase };
    form.reset(
      {
        ...courtCaseRawValue,
        id: { value: courtCaseRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): CourtCaseFormDefaults {
    return {
      id: null,
    };
  }
}
