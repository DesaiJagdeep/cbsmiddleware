import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IBatchTransaction } from '../batch-transaction.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../batch-transaction.test-samples';

import { BatchTransactionService } from './batch-transaction.service';

const requireRestSample: IBatchTransaction = {
  ...sampleWithRequiredData,
};

describe('BatchTransaction Service', () => {
  let service: BatchTransactionService;
  let httpMock: HttpTestingController;
  let expectedResult: IBatchTransaction | IBatchTransaction[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(BatchTransactionService);
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

    it('should create a BatchTransaction', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const batchTransaction = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(batchTransaction).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a BatchTransaction', () => {
      const batchTransaction = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(batchTransaction).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a BatchTransaction', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of BatchTransaction', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a BatchTransaction', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addBatchTransactionToCollectionIfMissing', () => {
      it('should add a BatchTransaction to an empty array', () => {
        const batchTransaction: IBatchTransaction = sampleWithRequiredData;
        expectedResult = service.addBatchTransactionToCollectionIfMissing([], batchTransaction);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(batchTransaction);
      });

      it('should not add a BatchTransaction to an array that contains it', () => {
        const batchTransaction: IBatchTransaction = sampleWithRequiredData;
        const batchTransactionCollection: IBatchTransaction[] = [
          {
            ...batchTransaction,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addBatchTransactionToCollectionIfMissing(batchTransactionCollection, batchTransaction);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a BatchTransaction to an array that doesn't contain it", () => {
        const batchTransaction: IBatchTransaction = sampleWithRequiredData;
        const batchTransactionCollection: IBatchTransaction[] = [sampleWithPartialData];
        expectedResult = service.addBatchTransactionToCollectionIfMissing(batchTransactionCollection, batchTransaction);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(batchTransaction);
      });

      it('should add only unique BatchTransaction to an array', () => {
        const batchTransactionArray: IBatchTransaction[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const batchTransactionCollection: IBatchTransaction[] = [sampleWithRequiredData];
        expectedResult = service.addBatchTransactionToCollectionIfMissing(batchTransactionCollection, ...batchTransactionArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const batchTransaction: IBatchTransaction = sampleWithRequiredData;
        const batchTransaction2: IBatchTransaction = sampleWithPartialData;
        expectedResult = service.addBatchTransactionToCollectionIfMissing([], batchTransaction, batchTransaction2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(batchTransaction);
        expect(expectedResult).toContain(batchTransaction2);
      });

      it('should accept null and undefined values', () => {
        const batchTransaction: IBatchTransaction = sampleWithRequiredData;
        expectedResult = service.addBatchTransactionToCollectionIfMissing([], null, batchTransaction, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(batchTransaction);
      });

      it('should return initial array if no BatchTransaction is added', () => {
        const batchTransactionCollection: IBatchTransaction[] = [sampleWithRequiredData];
        expectedResult = service.addBatchTransactionToCollectionIfMissing(batchTransactionCollection, undefined, null);
        expect(expectedResult).toEqual(batchTransactionCollection);
      });
    });

    describe('compareBatchTransaction', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareBatchTransaction(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareBatchTransaction(entity1, entity2);
        const compareResult2 = service.compareBatchTransaction(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareBatchTransaction(entity1, entity2);
        const compareResult2 = service.compareBatchTransaction(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareBatchTransaction(entity1, entity2);
        const compareResult2 = service.compareBatchTransaction(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
