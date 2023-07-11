import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../iss-file-parser.test-samples';

import { IssFileParserFormService } from './iss-file-parser-form.service';

describe('IssFileParser Form Service', () => {
  let service: IssFileParserFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(IssFileParserFormService);
  });

  describe('Service methods', () => {
    describe('createIssFileParserFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createIssFileParserFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            financialYear: expect.any(Object),
            bankName: expect.any(Object),
            bankCode: expect.any(Object),
            branchName: expect.any(Object),
            branchCode: expect.any(Object),
            schemeWiseBranchCode: expect.any(Object),
            ifsc: expect.any(Object),
            loanAccountNumberkcc: expect.any(Object),
            farmerName: expect.any(Object),
            gender: expect.any(Object),
            aadharNumber: expect.any(Object),
            dateofBirth: expect.any(Object),
            ageAtTimeOfSanction: expect.any(Object),
            mobileNo: expect.any(Object),
            farmersCategory: expect.any(Object),
            farmerType: expect.any(Object),
            socialCategory: expect.any(Object),
            relativeType: expect.any(Object),
            relativeName: expect.any(Object),
            stateName: expect.any(Object),
            stateCode: expect.any(Object),
            districtName: expect.any(Object),
            districtCode: expect.any(Object),
            blockCode: expect.any(Object),
            blockName: expect.any(Object),
            villageCode: expect.any(Object),
            villageName: expect.any(Object),
            address: expect.any(Object),
            pinCode: expect.any(Object),
            accountType: expect.any(Object),
            accountNumber: expect.any(Object),
            pacsName: expect.any(Object),
            pacsNumber: expect.any(Object),
            accountHolderType: expect.any(Object),
            primaryOccupation: expect.any(Object),
            loanSactionDate: expect.any(Object),
            loanSanctionAmount: expect.any(Object),
            tenureOFLoan: expect.any(Object),
            dateOfOverDuePayment: expect.any(Object),
            cropName: expect.any(Object),
            surveyNo: expect.any(Object),
            satBaraSubsurveyNo: expect.any(Object),
            seasonName: expect.any(Object),
            areaHect: expect.any(Object),
            landType: expect.any(Object),
            disbursementDate: expect.any(Object),
            disburseAmount: expect.any(Object),
            maturityLoanDate: expect.any(Object),
            recoveryAmountPrinciple: expect.any(Object),
            recoveryAmountInterest: expect.any(Object),
            recoveryDate: expect.any(Object),
            issPortalFile: expect.any(Object),
          })
        );
      });

      it('passing IIssFileParser should create a new form with FormGroup', () => {
        const formGroup = service.createIssFileParserFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            financialYear: expect.any(Object),
            bankName: expect.any(Object),
            bankCode: expect.any(Object),
            branchName: expect.any(Object),
            branchCode: expect.any(Object),
            schemeWiseBranchCode: expect.any(Object),
            ifsc: expect.any(Object),
            loanAccountNumberkcc: expect.any(Object),
            farmerName: expect.any(Object),
            gender: expect.any(Object),
            aadharNumber: expect.any(Object),
            dateofBirth: expect.any(Object),
            ageAtTimeOfSanction: expect.any(Object),
            mobileNo: expect.any(Object),
            farmersCategory: expect.any(Object),
            farmerType: expect.any(Object),
            socialCategory: expect.any(Object),
            relativeType: expect.any(Object),
            relativeName: expect.any(Object),
            stateName: expect.any(Object),
            stateCode: expect.any(Object),
            districtName: expect.any(Object),
            districtCode: expect.any(Object),
            blockCode: expect.any(Object),
            blockName: expect.any(Object),
            villageCode: expect.any(Object),
            villageName: expect.any(Object),
            address: expect.any(Object),
            pinCode: expect.any(Object),
            accountType: expect.any(Object),
            accountNumber: expect.any(Object),
            pacsName: expect.any(Object),
            pacsNumber: expect.any(Object),
            accountHolderType: expect.any(Object),
            primaryOccupation: expect.any(Object),
            loanSactionDate: expect.any(Object),
            loanSanctionAmount: expect.any(Object),
            tenureOFLoan: expect.any(Object),
            dateOfOverDuePayment: expect.any(Object),
            cropName: expect.any(Object),
            surveyNo: expect.any(Object),
            satBaraSubsurveyNo: expect.any(Object),
            seasonName: expect.any(Object),
            areaHect: expect.any(Object),
            landType: expect.any(Object),
            disbursementDate: expect.any(Object),
            disburseAmount: expect.any(Object),
            maturityLoanDate: expect.any(Object),
            recoveryAmountPrinciple: expect.any(Object),
            recoveryAmountInterest: expect.any(Object),
            recoveryDate: expect.any(Object),
            issPortalFile: expect.any(Object),
          })
        );
      });
    });

    describe('getIssFileParser', () => {
      it('should return NewIssFileParser for default IssFileParser initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createIssFileParserFormGroup(sampleWithNewData);

        const issFileParser = service.getIssFileParser(formGroup) as any;

        expect(issFileParser).toMatchObject(sampleWithNewData);
      });

      it('should return NewIssFileParser for empty IssFileParser initial value', () => {
        const formGroup = service.createIssFileParserFormGroup();

        const issFileParser = service.getIssFileParser(formGroup) as any;

        expect(issFileParser).toMatchObject({});
      });

      it('should return IIssFileParser', () => {
        const formGroup = service.createIssFileParserFormGroup(sampleWithRequiredData);

        const issFileParser = service.getIssFileParser(formGroup) as any;

        expect(issFileParser).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IIssFileParser should not enable id FormControl', () => {
        const formGroup = service.createIssFileParserFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewIssFileParser should disable id FormControl', () => {
        const formGroup = service.createIssFileParserFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
