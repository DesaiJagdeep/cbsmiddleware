import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { ILoanDemandKMPatrak } from '../loan-demand-km-patrak.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../loan-demand-km-patrak.test-samples';

import { LoanDemandKMPatrakService, RestLoanDemandKMPatrak } from './loan-demand-km-patrak.service';

const requireRestSample: RestLoanDemandKMPatrak = {
  ...sampleWithRequiredData,
  date: sampleWithRequiredData.date?.format(DATE_FORMAT),
  kmDate: sampleWithRequiredData.kmDate?.format(DATE_FORMAT),
  pendingDate: sampleWithRequiredData.pendingDate?.format(DATE_FORMAT),
  depositeDate: sampleWithRequiredData.depositeDate?.format(DATE_FORMAT),
  dueDateB: sampleWithRequiredData.dueDateB?.format(DATE_FORMAT),
  vasulPatraRepaymentDateB: sampleWithRequiredData.vasulPatraRepaymentDateB?.format(DATE_FORMAT),
};

describe('LoanDemandKMPatrak Service', () => {
  let service: LoanDemandKMPatrakService;
  let httpMock: HttpTestingController;
  let expectedResult: ILoanDemandKMPatrak | ILoanDemandKMPatrak[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(LoanDemandKMPatrakService);
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

    it('should create a LoanDemandKMPatrak', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const loanDemandKMPatrak = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(loanDemandKMPatrak).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a LoanDemandKMPatrak', () => {
      const loanDemandKMPatrak = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(loanDemandKMPatrak).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a LoanDemandKMPatrak', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of LoanDemandKMPatrak', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a LoanDemandKMPatrak', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addLoanDemandKMPatrakToCollectionIfMissing', () => {
      it('should add a LoanDemandKMPatrak to an empty array', () => {
        const loanDemandKMPatrak: ILoanDemandKMPatrak = sampleWithRequiredData;
        expectedResult = service.addLoanDemandKMPatrakToCollectionIfMissing([], loanDemandKMPatrak);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(loanDemandKMPatrak);
      });

      it('should not add a LoanDemandKMPatrak to an array that contains it', () => {
        const loanDemandKMPatrak: ILoanDemandKMPatrak = sampleWithRequiredData;
        const loanDemandKMPatrakCollection: ILoanDemandKMPatrak[] = [
          {
            ...loanDemandKMPatrak,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addLoanDemandKMPatrakToCollectionIfMissing(loanDemandKMPatrakCollection, loanDemandKMPatrak);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a LoanDemandKMPatrak to an array that doesn't contain it", () => {
        const loanDemandKMPatrak: ILoanDemandKMPatrak = sampleWithRequiredData;
        const loanDemandKMPatrakCollection: ILoanDemandKMPatrak[] = [sampleWithPartialData];
        expectedResult = service.addLoanDemandKMPatrakToCollectionIfMissing(loanDemandKMPatrakCollection, loanDemandKMPatrak);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(loanDemandKMPatrak);
      });

      it('should add only unique LoanDemandKMPatrak to an array', () => {
        const loanDemandKMPatrakArray: ILoanDemandKMPatrak[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const loanDemandKMPatrakCollection: ILoanDemandKMPatrak[] = [sampleWithRequiredData];
        expectedResult = service.addLoanDemandKMPatrakToCollectionIfMissing(loanDemandKMPatrakCollection, ...loanDemandKMPatrakArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const loanDemandKMPatrak: ILoanDemandKMPatrak = sampleWithRequiredData;
        const loanDemandKMPatrak2: ILoanDemandKMPatrak = sampleWithPartialData;
        expectedResult = service.addLoanDemandKMPatrakToCollectionIfMissing([], loanDemandKMPatrak, loanDemandKMPatrak2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(loanDemandKMPatrak);
        expect(expectedResult).toContain(loanDemandKMPatrak2);
      });

      it('should accept null and undefined values', () => {
        const loanDemandKMPatrak: ILoanDemandKMPatrak = sampleWithRequiredData;
        expectedResult = service.addLoanDemandKMPatrakToCollectionIfMissing([], null, loanDemandKMPatrak, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(loanDemandKMPatrak);
      });

      it('should return initial array if no LoanDemandKMPatrak is added', () => {
        const loanDemandKMPatrakCollection: ILoanDemandKMPatrak[] = [sampleWithRequiredData];
        expectedResult = service.addLoanDemandKMPatrakToCollectionIfMissing(loanDemandKMPatrakCollection, undefined, null);
        expect(expectedResult).toEqual(loanDemandKMPatrakCollection);
      });
    });

    describe('compareLoanDemandKMPatrak', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareLoanDemandKMPatrak(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareLoanDemandKMPatrak(entity1, entity2);
        const compareResult2 = service.compareLoanDemandKMPatrak(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareLoanDemandKMPatrak(entity1, entity2);
        const compareResult2 = service.compareLoanDemandKMPatrak(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareLoanDemandKMPatrak(entity1, entity2);
        const compareResult2 = service.compareLoanDemandKMPatrak(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
