import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IIsCalculateTemp, NewIsCalculateTemp } from '../is-calculate-temp.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IIsCalculateTemp for edit and NewIsCalculateTempFormGroupInput for create.
 */
type IsCalculateTempFormGroupInput = IIsCalculateTemp | PartialWithRequiredKeyOf<NewIsCalculateTemp>;

type IsCalculateTempFormDefaults = Pick<NewIsCalculateTemp, 'id'>;

type IsCalculateTempFormGroupContent = {
  id: FormControl<IIsCalculateTemp['id'] | NewIsCalculateTemp['id']>;
  serialNo: FormControl<IIsCalculateTemp['serialNo']>;
  financialYear: FormControl<IIsCalculateTemp['financialYear']>;
  issFileParserId: FormControl<IIsCalculateTemp['issFileParserId']>;
  branchCode: FormControl<IIsCalculateTemp['branchCode']>;
  pacsNumber: FormControl<IIsCalculateTemp['pacsNumber']>;
  loanAccountNumberKcc: FormControl<IIsCalculateTemp['loanAccountNumberKcc']>;
  farmerName: FormControl<IIsCalculateTemp['farmerName']>;
  gender: FormControl<IIsCalculateTemp['gender']>;
  aadharNumber: FormControl<IIsCalculateTemp['aadharNumber']>;
  mobileNo: FormControl<IIsCalculateTemp['mobileNo']>;
  farmerType: FormControl<IIsCalculateTemp['farmerType']>;
  socialCategory: FormControl<IIsCalculateTemp['socialCategory']>;
  accountNumber: FormControl<IIsCalculateTemp['accountNumber']>;
  loanSanctionDate: FormControl<IIsCalculateTemp['loanSanctionDate']>;
  loanSanctionAmount: FormControl<IIsCalculateTemp['loanSanctionAmount']>;
  disbursementDate: FormControl<IIsCalculateTemp['disbursementDate']>;
  disburseAmount: FormControl<IIsCalculateTemp['disburseAmount']>;
  maturityLoanDate: FormControl<IIsCalculateTemp['maturityLoanDate']>;
  bankDate: FormControl<IIsCalculateTemp['bankDate']>;
  cropName: FormControl<IIsCalculateTemp['cropName']>;
  recoveryAmount: FormControl<IIsCalculateTemp['recoveryAmount']>;
  recoveryInterest: FormControl<IIsCalculateTemp['recoveryInterest']>;
  recoveryDate: FormControl<IIsCalculateTemp['recoveryDate']>;
  balanceAmount: FormControl<IIsCalculateTemp['balanceAmount']>;
  prevDays: FormControl<IIsCalculateTemp['prevDays']>;
  presDays: FormControl<IIsCalculateTemp['presDays']>;
  actualDays: FormControl<IIsCalculateTemp['actualDays']>;
  nProd: FormControl<IIsCalculateTemp['nProd']>;
  productAmount: FormControl<IIsCalculateTemp['productAmount']>;
  productBank: FormControl<IIsCalculateTemp['productBank']>;
  productAbh3Lakh: FormControl<IIsCalculateTemp['productAbh3Lakh']>;
  interestFirst15: FormControl<IIsCalculateTemp['interestFirst15']>;
  interestFirst25: FormControl<IIsCalculateTemp['interestFirst25']>;
  interestSecond15: FormControl<IIsCalculateTemp['interestSecond15']>;
  interestSecond25: FormControl<IIsCalculateTemp['interestSecond25']>;
  interestStateFirst3: FormControl<IIsCalculateTemp['interestStateFirst3']>;
  interestStateSecond3: FormControl<IIsCalculateTemp['interestStateSecond3']>;
  interestFirstAbh3: FormControl<IIsCalculateTemp['interestFirstAbh3']>;
  interestSecondAbh3: FormControl<IIsCalculateTemp['interestSecondAbh3']>;
  interestAbove3Lakh: FormControl<IIsCalculateTemp['interestAbove3Lakh']>;
  panjabraoInt3: FormControl<IIsCalculateTemp['panjabraoInt3']>;
  isRecover: FormControl<IIsCalculateTemp['isRecover']>;
  abh3LakhAmt: FormControl<IIsCalculateTemp['abh3LakhAmt']>;
  upto50000: FormControl<IIsCalculateTemp['upto50000']>;
};

export type IsCalculateTempFormGroup = FormGroup<IsCalculateTempFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class IsCalculateTempFormService {
  createIsCalculateTempFormGroup(isCalculateTemp: IsCalculateTempFormGroupInput = { id: null }): IsCalculateTempFormGroup {
    const isCalculateTempRawValue = {
      ...this.getFormDefaults(),
      ...isCalculateTemp,
    };
    return new FormGroup<IsCalculateTempFormGroupContent>({
      id: new FormControl(
        { value: isCalculateTempRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      serialNo: new FormControl(isCalculateTempRawValue.serialNo),
      financialYear: new FormControl(isCalculateTempRawValue.financialYear),
      issFileParserId: new FormControl(isCalculateTempRawValue.issFileParserId),
      branchCode: new FormControl(isCalculateTempRawValue.branchCode),
      pacsNumber: new FormControl(isCalculateTempRawValue.pacsNumber),
      loanAccountNumberKcc: new FormControl(isCalculateTempRawValue.loanAccountNumberKcc),
      farmerName: new FormControl(isCalculateTempRawValue.farmerName),
      gender: new FormControl(isCalculateTempRawValue.gender),
      aadharNumber: new FormControl(isCalculateTempRawValue.aadharNumber),
      mobileNo: new FormControl(isCalculateTempRawValue.mobileNo),
      farmerType: new FormControl(isCalculateTempRawValue.farmerType),
      socialCategory: new FormControl(isCalculateTempRawValue.socialCategory),
      accountNumber: new FormControl(isCalculateTempRawValue.accountNumber),
      loanSanctionDate: new FormControl(isCalculateTempRawValue.loanSanctionDate),
      loanSanctionAmount: new FormControl(isCalculateTempRawValue.loanSanctionAmount),
      disbursementDate: new FormControl(isCalculateTempRawValue.disbursementDate),
      disburseAmount: new FormControl(isCalculateTempRawValue.disburseAmount),
      maturityLoanDate: new FormControl(isCalculateTempRawValue.maturityLoanDate),
      bankDate: new FormControl(isCalculateTempRawValue.bankDate),
      cropName: new FormControl(isCalculateTempRawValue.cropName),
      recoveryAmount: new FormControl(isCalculateTempRawValue.recoveryAmount),
      recoveryInterest: new FormControl(isCalculateTempRawValue.recoveryInterest),
      recoveryDate: new FormControl(isCalculateTempRawValue.recoveryDate),
      balanceAmount: new FormControl(isCalculateTempRawValue.balanceAmount),
      prevDays: new FormControl(isCalculateTempRawValue.prevDays),
      presDays: new FormControl(isCalculateTempRawValue.presDays),
      actualDays: new FormControl(isCalculateTempRawValue.actualDays),
      nProd: new FormControl(isCalculateTempRawValue.nProd),
      productAmount: new FormControl(isCalculateTempRawValue.productAmount),
      productBank: new FormControl(isCalculateTempRawValue.productBank),
      productAbh3Lakh: new FormControl(isCalculateTempRawValue.productAbh3Lakh),
      interestFirst15: new FormControl(isCalculateTempRawValue.interestFirst15),
      interestFirst25: new FormControl(isCalculateTempRawValue.interestFirst25),
      interestSecond15: new FormControl(isCalculateTempRawValue.interestSecond15),
      interestSecond25: new FormControl(isCalculateTempRawValue.interestSecond25),
      interestStateFirst3: new FormControl(isCalculateTempRawValue.interestStateFirst3),
      interestStateSecond3: new FormControl(isCalculateTempRawValue.interestStateSecond3),
      interestFirstAbh3: new FormControl(isCalculateTempRawValue.interestFirstAbh3),
      interestSecondAbh3: new FormControl(isCalculateTempRawValue.interestSecondAbh3),
      interestAbove3Lakh: new FormControl(isCalculateTempRawValue.interestAbove3Lakh),
      panjabraoInt3: new FormControl(isCalculateTempRawValue.panjabraoInt3),
      isRecover: new FormControl(isCalculateTempRawValue.isRecover),
      abh3LakhAmt: new FormControl(isCalculateTempRawValue.abh3LakhAmt),
      upto50000: new FormControl(isCalculateTempRawValue.upto50000),
    });
  }

  getIsCalculateTemp(form: IsCalculateTempFormGroup): IIsCalculateTemp | NewIsCalculateTemp {
    return form.getRawValue() as IIsCalculateTemp | NewIsCalculateTemp;
  }

  resetForm(form: IsCalculateTempFormGroup, isCalculateTemp: IsCalculateTempFormGroupInput): void {
    const isCalculateTempRawValue = { ...this.getFormDefaults(), ...isCalculateTemp };
    form.reset(
      {
        ...isCalculateTempRawValue,
        id: { value: isCalculateTempRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): IsCalculateTempFormDefaults {
    return {
      id: null,
    };
  }
}
