import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IApplicationLogHistory } from '../application-log-history.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../application-log-history.test-samples';

import { ApplicationLogHistoryService } from './application-log-history.service';

const requireRestSample: IApplicationLogHistory = {
  ...sampleWithRequiredData,
};

describe('ApplicationLogHistory Service', () => {
  let service: ApplicationLogHistoryService;
  let httpMock: HttpTestingController;
  let expectedResult: IApplicationLogHistory | IApplicationLogHistory[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ApplicationLogHistoryService);
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

    it('should create a ApplicationLogHistory', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const applicationLogHistory = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(applicationLogHistory).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ApplicationLogHistory', () => {
      const applicationLogHistory = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(applicationLogHistory).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ApplicationLogHistory', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ApplicationLogHistory', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ApplicationLogHistory', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addApplicationLogHistoryToCollectionIfMissing', () => {
      it('should add a ApplicationLogHistory to an empty array', () => {
        const applicationLogHistory: IApplicationLogHistory = sampleWithRequiredData;
        expectedResult = service.addApplicationLogHistoryToCollectionIfMissing([], applicationLogHistory);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(applicationLogHistory);
      });

      it('should not add a ApplicationLogHistory to an array that contains it', () => {
        const applicationLogHistory: IApplicationLogHistory = sampleWithRequiredData;
        const applicationLogHistoryCollection: IApplicationLogHistory[] = [
          {
            ...applicationLogHistory,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addApplicationLogHistoryToCollectionIfMissing(applicationLogHistoryCollection, applicationLogHistory);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ApplicationLogHistory to an array that doesn't contain it", () => {
        const applicationLogHistory: IApplicationLogHistory = sampleWithRequiredData;
        const applicationLogHistoryCollection: IApplicationLogHistory[] = [sampleWithPartialData];
        expectedResult = service.addApplicationLogHistoryToCollectionIfMissing(applicationLogHistoryCollection, applicationLogHistory);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(applicationLogHistory);
      });

      it('should add only unique ApplicationLogHistory to an array', () => {
        const applicationLogHistoryArray: IApplicationLogHistory[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const applicationLogHistoryCollection: IApplicationLogHistory[] = [sampleWithRequiredData];
        expectedResult = service.addApplicationLogHistoryToCollectionIfMissing(
          applicationLogHistoryCollection,
          ...applicationLogHistoryArray
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const applicationLogHistory: IApplicationLogHistory = sampleWithRequiredData;
        const applicationLogHistory2: IApplicationLogHistory = sampleWithPartialData;
        expectedResult = service.addApplicationLogHistoryToCollectionIfMissing([], applicationLogHistory, applicationLogHistory2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(applicationLogHistory);
        expect(expectedResult).toContain(applicationLogHistory2);
      });

      it('should accept null and undefined values', () => {
        const applicationLogHistory: IApplicationLogHistory = sampleWithRequiredData;
        expectedResult = service.addApplicationLogHistoryToCollectionIfMissing([], null, applicationLogHistory, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(applicationLogHistory);
      });

      it('should return initial array if no ApplicationLogHistory is added', () => {
        const applicationLogHistoryCollection: IApplicationLogHistory[] = [sampleWithRequiredData];
        expectedResult = service.addApplicationLogHistoryToCollectionIfMissing(applicationLogHistoryCollection, undefined, null);
        expect(expectedResult).toEqual(applicationLogHistoryCollection);
      });
    });

    describe('compareApplicationLogHistory', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareApplicationLogHistory(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareApplicationLogHistory(entity1, entity2);
        const compareResult2 = service.compareApplicationLogHistory(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareApplicationLogHistory(entity1, entity2);
        const compareResult2 = service.compareApplicationLogHistory(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareApplicationLogHistory(entity1, entity2);
        const compareResult2 = service.compareApplicationLogHistory(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
