import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IIssFileParser, NewIssFileParser } from '../iss-file-parser.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IIssFileParser for edit and NewIssFileParserFormGroupInput for create.
 */
type IssFileParserFormGroupInput = IIssFileParser | PartialWithRequiredKeyOf<NewIssFileParser>;

type IssFileParserFormDefaults = Pick<NewIssFileParser, 'id'>;

type IssFileParserFormGroupContent = {
  id: FormControl<IIssFileParser['id'] | NewIssFileParser['id']>;
  financialYear: FormControl<IIssFileParser['financialYear']>;
  bankName: FormControl<IIssFileParser['bankName']>;
  bankCode: FormControl<IIssFileParser['bankCode']>;
  branchName: FormControl<IIssFileParser['branchName']>;
  branchCode: FormControl<IIssFileParser['branchCode']>;
  schemeWiseBranchCode: FormControl<IIssFileParser['schemeWiseBranchCode']>;
  ifsc: FormControl<IIssFileParser['ifsc']>;
  loanAccountNumberkcc: FormControl<IIssFileParser['loanAccountNumberkcc']>;
  farmerName: FormControl<IIssFileParser['farmerName']>;
  gender: FormControl<IIssFileParser['gender']>;
  aadharNumber: FormControl<IIssFileParser['aadharNumber']>;
  dateofBirth: FormControl<IIssFileParser['dateofBirth']>;
  ageAtTimeOfSanction: FormControl<IIssFileParser['ageAtTimeOfSanction']>;
  mobileNo: FormControl<IIssFileParser['mobileNo']>;
  farmersCategory: FormControl<IIssFileParser['farmersCategory']>;
  farmerType: FormControl<IIssFileParser['farmerType']>;
  socialCategory: FormControl<IIssFileParser['socialCategory']>;
  relativeType: FormControl<IIssFileParser['relativeType']>;
  relativeName: FormControl<IIssFileParser['relativeName']>;
  stateName: FormControl<IIssFileParser['stateName']>;
  stateCode: FormControl<IIssFileParser['stateCode']>;
  districtName: FormControl<IIssFileParser['districtName']>;
  districtCode: FormControl<IIssFileParser['districtCode']>;
  blockCode: FormControl<IIssFileParser['blockCode']>;
  blockName: FormControl<IIssFileParser['blockName']>;
  villageCode: FormControl<IIssFileParser['villageCode']>;
  villageName: FormControl<IIssFileParser['villageName']>;
  address: FormControl<IIssFileParser['address']>;
  pinCode: FormControl<IIssFileParser['pinCode']>;
  accountType: FormControl<IIssFileParser['accountType']>;
  accountNumber: FormControl<IIssFileParser['accountNumber']>;
  pacsName: FormControl<IIssFileParser['pacsName']>;
  pacsNumber: FormControl<IIssFileParser['pacsNumber']>;
  accountHolderType: FormControl<IIssFileParser['accountHolderType']>;
  primaryOccupation: FormControl<IIssFileParser['primaryOccupation']>;
  loanSactionDate: FormControl<IIssFileParser['loanSactionDate']>;
  loanSanctionAmount: FormControl<IIssFileParser['loanSanctionAmount']>;
  tenureOFLoan: FormControl<IIssFileParser['tenureOFLoan']>;
  dateOfOverDuePayment: FormControl<IIssFileParser['dateOfOverDuePayment']>;
  cropName: FormControl<IIssFileParser['cropName']>;
  surveyNo: FormControl<IIssFileParser['surveyNo']>;
  satBaraSubsurveyNo: FormControl<IIssFileParser['satBaraSubsurveyNo']>;
  seasonName: FormControl<IIssFileParser['seasonName']>;
  areaHect: FormControl<IIssFileParser['areaHect']>;
  landType: FormControl<IIssFileParser['landType']>;
  disbursementDate: FormControl<IIssFileParser['disbursementDate']>;
  disburseAmount: FormControl<IIssFileParser['disburseAmount']>;
  maturityLoanDate: FormControl<IIssFileParser['maturityLoanDate']>;
  recoveryAmountPrinciple: FormControl<IIssFileParser['recoveryAmountPrinciple']>;
  recoveryAmountInterest: FormControl<IIssFileParser['recoveryAmountInterest']>;
  recoveryDate: FormControl<IIssFileParser['recoveryDate']>;
  issPortalFile: FormControl<IIssFileParser['issPortalFile']>;
};

export type IssFileParserFormGroup = FormGroup<IssFileParserFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class IssFileParserFormService {
  createIssFileParserFormGroup(issFileParser: IssFileParserFormGroupInput = { id: null }): IssFileParserFormGroup {
    const issFileParserRawValue = {
      ...this.getFormDefaults(),
      ...issFileParser,
    };
    return new FormGroup<IssFileParserFormGroupContent>({
      id: new FormControl(
        { value: issFileParserRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      financialYear: new FormControl(issFileParserRawValue.financialYear),
      bankName: new FormControl(issFileParserRawValue.bankName),
      bankCode: new FormControl(issFileParserRawValue.bankCode),
      branchName: new FormControl(issFileParserRawValue.branchName),
      branchCode: new FormControl(issFileParserRawValue.branchCode),
      schemeWiseBranchCode: new FormControl(issFileParserRawValue.schemeWiseBranchCode),
      ifsc: new FormControl(issFileParserRawValue.ifsc),
      loanAccountNumberkcc: new FormControl(issFileParserRawValue.loanAccountNumberkcc),
      farmerName: new FormControl(issFileParserRawValue.farmerName),
      gender: new FormControl(issFileParserRawValue.gender),
      aadharNumber: new FormControl(issFileParserRawValue.aadharNumber),
      dateofBirth: new FormControl(issFileParserRawValue.dateofBirth),
      ageAtTimeOfSanction: new FormControl(issFileParserRawValue.ageAtTimeOfSanction),
      mobileNo: new FormControl(issFileParserRawValue.mobileNo),
      farmersCategory: new FormControl(issFileParserRawValue.farmersCategory),
      farmerType: new FormControl(issFileParserRawValue.farmerType),
      socialCategory: new FormControl(issFileParserRawValue.socialCategory),
      relativeType: new FormControl(issFileParserRawValue.relativeType),
      relativeName: new FormControl(issFileParserRawValue.relativeName),
      stateName: new FormControl(issFileParserRawValue.stateName),
      stateCode: new FormControl(issFileParserRawValue.stateCode),
      districtName: new FormControl(issFileParserRawValue.districtName),
      districtCode: new FormControl(issFileParserRawValue.districtCode),
      blockCode: new FormControl(issFileParserRawValue.blockCode),
      blockName: new FormControl(issFileParserRawValue.blockName),
      villageCode: new FormControl(issFileParserRawValue.villageCode),
      villageName: new FormControl(issFileParserRawValue.villageName),
      address: new FormControl(issFileParserRawValue.address),
      pinCode: new FormControl(issFileParserRawValue.pinCode),
      accountType: new FormControl(issFileParserRawValue.accountType),
      accountNumber: new FormControl(issFileParserRawValue.accountNumber),
      pacsName: new FormControl(issFileParserRawValue.pacsName),
      pacsNumber: new FormControl(issFileParserRawValue.pacsNumber),
      accountHolderType: new FormControl(issFileParserRawValue.accountHolderType),
      primaryOccupation: new FormControl(issFileParserRawValue.primaryOccupation),
      loanSactionDate: new FormControl(issFileParserRawValue.loanSactionDate),
      loanSanctionAmount: new FormControl(issFileParserRawValue.loanSanctionAmount),
      tenureOFLoan: new FormControl(issFileParserRawValue.tenureOFLoan),
      dateOfOverDuePayment: new FormControl(issFileParserRawValue.dateOfOverDuePayment),
      cropName: new FormControl(issFileParserRawValue.cropName),
      surveyNo: new FormControl(issFileParserRawValue.surveyNo),
      satBaraSubsurveyNo: new FormControl(issFileParserRawValue.satBaraSubsurveyNo),
      seasonName: new FormControl(issFileParserRawValue.seasonName),
      areaHect: new FormControl(issFileParserRawValue.areaHect),
      landType: new FormControl(issFileParserRawValue.landType),
      disbursementDate: new FormControl(issFileParserRawValue.disbursementDate),
      disburseAmount: new FormControl(issFileParserRawValue.disburseAmount),
      maturityLoanDate: new FormControl(issFileParserRawValue.maturityLoanDate),
      recoveryAmountPrinciple: new FormControl(issFileParserRawValue.recoveryAmountPrinciple),
      recoveryAmountInterest: new FormControl(issFileParserRawValue.recoveryAmountInterest),
      recoveryDate: new FormControl(issFileParserRawValue.recoveryDate),
      issPortalFile: new FormControl(issFileParserRawValue.issPortalFile),
    });
  }

  getIssFileParser(form: IssFileParserFormGroup): IIssFileParser | NewIssFileParser {
    return form.getRawValue() as IIssFileParser | NewIssFileParser;
  }

  resetForm(form: IssFileParserFormGroup, issFileParser: IssFileParserFormGroupInput): void {
    const issFileParserRawValue = { ...this.getFormDefaults(), ...issFileParser };
    form.reset(
      {
        ...issFileParserRawValue,
        id: { value: issFileParserRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): IssFileParserFormDefaults {
    return {
      id: null,
    };
  }
}
