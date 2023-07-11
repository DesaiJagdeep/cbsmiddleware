import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IBankBranchMaster } from '../bank-branch-master.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../bank-branch-master.test-samples';

import { BankBranchMasterService } from './bank-branch-master.service';

const requireRestSample: IBankBranchMaster = {
  ...sampleWithRequiredData,
};

describe('BankBranchMaster Service', () => {
  let service: BankBranchMasterService;
  let httpMock: HttpTestingController;
  let expectedResult: IBankBranchMaster | IBankBranchMaster[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(BankBranchMasterService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a BankBranchMaster', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const bankBranchMaster = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(bankBranchMaster).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a BankBranchMaster', () => {
      const bankBranchMaster = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(bankBranchMaster).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a BankBranchMaster', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of BankBranchMaster', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a BankBranchMaster', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addBankBranchMasterToCollectionIfMissing', () => {
      it('should add a BankBranchMaster to an empty array', () => {
        const bankBranchMaster: IBankBranchMaster = sampleWithRequiredData;
        expectedResult = service.addBankBranchMasterToCollectionIfMissing([], bankBranchMaster);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(bankBranchMaster);
      });

      it('should not add a BankBranchMaster to an array that contains it', () => {
        const bankBranchMaster: IBankBranchMaster = sampleWithRequiredData;
        const bankBranchMasterCollection: IBankBranchMaster[] = [
          {
            ...bankBranchMaster,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addBankBranchMasterToCollectionIfMissing(bankBranchMasterCollection, bankBranchMaster);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a BankBranchMaster to an array that doesn't contain it", () => {
        const bankBranchMaster: IBankBranchMaster = sampleWithRequiredData;
        const bankBranchMasterCollection: IBankBranchMaster[] = [sampleWithPartialData];
        expectedResult = service.addBankBranchMasterToCollectionIfMissing(bankBranchMasterCollection, bankBranchMaster);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(bankBranchMaster);
      });

      it('should add only unique BankBranchMaster to an array', () => {
        const bankBranchMasterArray: IBankBranchMaster[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const bankBranchMasterCollection: IBankBranchMaster[] = [sampleWithRequiredData];
        expectedResult = service.addBankBranchMasterToCollectionIfMissing(bankBranchMasterCollection, ...bankBranchMasterArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const bankBranchMaster: IBankBranchMaster = sampleWithRequiredData;
        const bankBranchMaster2: IBankBranchMaster = sampleWithPartialData;
        expectedResult = service.addBankBranchMasterToCollectionIfMissing([], bankBranchMaster, bankBranchMaster2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(bankBranchMaster);
        expect(expectedResult).toContain(bankBranchMaster2);
      });

      it('should accept null and undefined values', () => {
        const bankBranchMaster: IBankBranchMaster = sampleWithRequiredData;
        expectedResult = service.addBankBranchMasterToCollectionIfMissing([], null, bankBranchMaster, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(bankBranchMaster);
      });

      it('should return initial array if no BankBranchMaster is added', () => {
        const bankBranchMasterCollection: IBankBranchMaster[] = [sampleWithRequiredData];
        expectedResult = service.addBankBranchMasterToCollectionIfMissing(bankBranchMasterCollection, undefined, null);
        expect(expectedResult).toEqual(bankBranchMasterCollection);
      });
    });

    describe('compareBankBranchMaster', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareBankBranchMaster(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareBankBranchMaster(entity1, entity2);
        const compareResult2 = service.compareBankBranchMaster(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareBankBranchMaster(entity1, entity2);
        const compareResult2 = service.compareBankBranchMaster(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareBankBranchMaster(entity1, entity2);
        const compareResult2 = service.compareBankBranchMaster(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
