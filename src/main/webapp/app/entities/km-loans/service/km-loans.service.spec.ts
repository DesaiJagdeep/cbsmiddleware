import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IKmLoans } from '../km-loans.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../km-loans.test-samples';

import { KmLoansService, RestKmLoans } from './km-loans.service';

const requireRestSample: RestKmLoans = {
  ...sampleWithRequiredData,
  loanDate: sampleWithRequiredData.loanDate?.toJSON(),
  dueDate: sampleWithRequiredData.dueDate?.toJSON(),
};

describe('KmLoans Service', () => {
  let service: KmLoansService;
  let httpMock: HttpTestingController;
  let expectedResult: IKmLoans | IKmLoans[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(KmLoansService);
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

    it('should create a KmLoans', () => {
      const kmLoans = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(kmLoans).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a KmLoans', () => {
      const kmLoans = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(kmLoans).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a KmLoans', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of KmLoans', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a KmLoans', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addKmLoansToCollectionIfMissing', () => {
      it('should add a KmLoans to an empty array', () => {
        const kmLoans: IKmLoans = sampleWithRequiredData;
        expectedResult = service.addKmLoansToCollectionIfMissing([], kmLoans);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(kmLoans);
      });

      it('should not add a KmLoans to an array that contains it', () => {
        const kmLoans: IKmLoans = sampleWithRequiredData;
        const kmLoansCollection: IKmLoans[] = [
          {
            ...kmLoans,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addKmLoansToCollectionIfMissing(kmLoansCollection, kmLoans);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a KmLoans to an array that doesn't contain it", () => {
        const kmLoans: IKmLoans = sampleWithRequiredData;
        const kmLoansCollection: IKmLoans[] = [sampleWithPartialData];
        expectedResult = service.addKmLoansToCollectionIfMissing(kmLoansCollection, kmLoans);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(kmLoans);
      });

      it('should add only unique KmLoans to an array', () => {
        const kmLoansArray: IKmLoans[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const kmLoansCollection: IKmLoans[] = [sampleWithRequiredData];
        expectedResult = service.addKmLoansToCollectionIfMissing(kmLoansCollection, ...kmLoansArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const kmLoans: IKmLoans = sampleWithRequiredData;
        const kmLoans2: IKmLoans = sampleWithPartialData;
        expectedResult = service.addKmLoansToCollectionIfMissing([], kmLoans, kmLoans2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(kmLoans);
        expect(expectedResult).toContain(kmLoans2);
      });

      it('should accept null and undefined values', () => {
        const kmLoans: IKmLoans = sampleWithRequiredData;
        expectedResult = service.addKmLoansToCollectionIfMissing([], null, kmLoans, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(kmLoans);
      });

      it('should return initial array if no KmLoans is added', () => {
        const kmLoansCollection: IKmLoans[] = [sampleWithRequiredData];
        expectedResult = service.addKmLoansToCollectionIfMissing(kmLoansCollection, undefined, null);
        expect(expectedResult).toEqual(kmLoansCollection);
      });
    });

    describe('compareKmLoans', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareKmLoans(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareKmLoans(entity1, entity2);
        const compareResult2 = service.compareKmLoans(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareKmLoans(entity1, entity2);
        const compareResult2 = service.compareKmLoans(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareKmLoans(entity1, entity2);
        const compareResult2 = service.compareKmLoans(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
