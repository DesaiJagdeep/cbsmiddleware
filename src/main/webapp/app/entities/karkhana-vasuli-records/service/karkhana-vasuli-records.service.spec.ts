import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IKarkhanaVasuliRecords } from '../karkhana-vasuli-records.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../karkhana-vasuli-records.test-samples';

import { KarkhanaVasuliRecordsService } from './karkhana-vasuli-records.service';

const requireRestSample: IKarkhanaVasuliRecords = {
  ...sampleWithRequiredData,
};

describe('KarkhanaVasuliRecords Service', () => {
  let service: KarkhanaVasuliRecordsService;
  let httpMock: HttpTestingController;
  let expectedResult: IKarkhanaVasuliRecords | IKarkhanaVasuliRecords[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(KarkhanaVasuliRecordsService);
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

    it('should create a KarkhanaVasuliRecords', () => {
      const karkhanaVasuliRecords = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(karkhanaVasuliRecords).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a KarkhanaVasuliRecords', () => {
      const karkhanaVasuliRecords = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(karkhanaVasuliRecords).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a KarkhanaVasuliRecords', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of KarkhanaVasuliRecords', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a KarkhanaVasuliRecords', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addKarkhanaVasuliRecordsToCollectionIfMissing', () => {
      it('should add a KarkhanaVasuliRecords to an empty array', () => {
        const karkhanaVasuliRecords: IKarkhanaVasuliRecords = sampleWithRequiredData;
        expectedResult = service.addKarkhanaVasuliRecordsToCollectionIfMissing([], karkhanaVasuliRecords);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(karkhanaVasuliRecords);
      });

      it('should not add a KarkhanaVasuliRecords to an array that contains it', () => {
        const karkhanaVasuliRecords: IKarkhanaVasuliRecords = sampleWithRequiredData;
        const karkhanaVasuliRecordsCollection: IKarkhanaVasuliRecords[] = [
          {
            ...karkhanaVasuliRecords,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addKarkhanaVasuliRecordsToCollectionIfMissing(karkhanaVasuliRecordsCollection, karkhanaVasuliRecords);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a KarkhanaVasuliRecords to an array that doesn't contain it", () => {
        const karkhanaVasuliRecords: IKarkhanaVasuliRecords = sampleWithRequiredData;
        const karkhanaVasuliRecordsCollection: IKarkhanaVasuliRecords[] = [sampleWithPartialData];
        expectedResult = service.addKarkhanaVasuliRecordsToCollectionIfMissing(karkhanaVasuliRecordsCollection, karkhanaVasuliRecords);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(karkhanaVasuliRecords);
      });

      it('should add only unique KarkhanaVasuliRecords to an array', () => {
        const karkhanaVasuliRecordsArray: IKarkhanaVasuliRecords[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const karkhanaVasuliRecordsCollection: IKarkhanaVasuliRecords[] = [sampleWithRequiredData];
        expectedResult = service.addKarkhanaVasuliRecordsToCollectionIfMissing(
          karkhanaVasuliRecordsCollection,
          ...karkhanaVasuliRecordsArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const karkhanaVasuliRecords: IKarkhanaVasuliRecords = sampleWithRequiredData;
        const karkhanaVasuliRecords2: IKarkhanaVasuliRecords = sampleWithPartialData;
        expectedResult = service.addKarkhanaVasuliRecordsToCollectionIfMissing([], karkhanaVasuliRecords, karkhanaVasuliRecords2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(karkhanaVasuliRecords);
        expect(expectedResult).toContain(karkhanaVasuliRecords2);
      });

      it('should accept null and undefined values', () => {
        const karkhanaVasuliRecords: IKarkhanaVasuliRecords = sampleWithRequiredData;
        expectedResult = service.addKarkhanaVasuliRecordsToCollectionIfMissing([], null, karkhanaVasuliRecords, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(karkhanaVasuliRecords);
      });

      it('should return initial array if no KarkhanaVasuliRecords is added', () => {
        const karkhanaVasuliRecordsCollection: IKarkhanaVasuliRecords[] = [sampleWithRequiredData];
        expectedResult = service.addKarkhanaVasuliRecordsToCollectionIfMissing(karkhanaVasuliRecordsCollection, undefined, null);
        expect(expectedResult).toEqual(karkhanaVasuliRecordsCollection);
      });
    });

    describe('compareKarkhanaVasuliRecords', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareKarkhanaVasuliRecords(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareKarkhanaVasuliRecords(entity1, entity2);
        const compareResult2 = service.compareKarkhanaVasuliRecords(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareKarkhanaVasuliRecords(entity1, entity2);
        const compareResult2 = service.compareKarkhanaVasuliRecords(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareKarkhanaVasuliRecords(entity1, entity2);
        const compareResult2 = service.compareKarkhanaVasuliRecords(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
