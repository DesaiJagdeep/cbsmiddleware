import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IActivityType } from '../activity-type.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../activity-type.test-samples';

import { ActivityTypeService } from './activity-type.service';

const requireRestSample: IActivityType = {
  ...sampleWithRequiredData,
};

describe('ActivityType Service', () => {
  let service: ActivityTypeService;
  let httpMock: HttpTestingController;
  let expectedResult: IActivityType | IActivityType[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ActivityTypeService);
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

    it('should create a ActivityType', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const activityType = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(activityType).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ActivityType', () => {
      const activityType = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(activityType).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ActivityType', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ActivityType', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ActivityType', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addActivityTypeToCollectionIfMissing', () => {
      it('should add a ActivityType to an empty array', () => {
        const activityType: IActivityType = sampleWithRequiredData;
        expectedResult = service.addActivityTypeToCollectionIfMissing([], activityType);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(activityType);
      });

      it('should not add a ActivityType to an array that contains it', () => {
        const activityType: IActivityType = sampleWithRequiredData;
        const activityTypeCollection: IActivityType[] = [
          {
            ...activityType,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addActivityTypeToCollectionIfMissing(activityTypeCollection, activityType);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ActivityType to an array that doesn't contain it", () => {
        const activityType: IActivityType = sampleWithRequiredData;
        const activityTypeCollection: IActivityType[] = [sampleWithPartialData];
        expectedResult = service.addActivityTypeToCollectionIfMissing(activityTypeCollection, activityType);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(activityType);
      });

      it('should add only unique ActivityType to an array', () => {
        const activityTypeArray: IActivityType[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const activityTypeCollection: IActivityType[] = [sampleWithRequiredData];
        expectedResult = service.addActivityTypeToCollectionIfMissing(activityTypeCollection, ...activityTypeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const activityType: IActivityType = sampleWithRequiredData;
        const activityType2: IActivityType = sampleWithPartialData;
        expectedResult = service.addActivityTypeToCollectionIfMissing([], activityType, activityType2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(activityType);
        expect(expectedResult).toContain(activityType2);
      });

      it('should accept null and undefined values', () => {
        const activityType: IActivityType = sampleWithRequiredData;
        expectedResult = service.addActivityTypeToCollectionIfMissing([], null, activityType, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(activityType);
      });

      it('should return initial array if no ActivityType is added', () => {
        const activityTypeCollection: IActivityType[] = [sampleWithRequiredData];
        expectedResult = service.addActivityTypeToCollectionIfMissing(activityTypeCollection, undefined, null);
        expect(expectedResult).toEqual(activityTypeCollection);
      });
    });

    describe('compareActivityType', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareActivityType(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareActivityType(entity1, entity2);
        const compareResult2 = service.compareActivityType(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareActivityType(entity1, entity2);
        const compareResult2 = service.compareActivityType(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareActivityType(entity1, entity2);
        const compareResult2 = service.compareActivityType(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
