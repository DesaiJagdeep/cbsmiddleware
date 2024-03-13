import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IIsCalculateTemp } from '../is-calculate-temp.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../is-calculate-temp.test-samples';

import { IsCalculateTempService } from './is-calculate-temp.service';

const requireRestSample: IIsCalculateTemp = {
  ...sampleWithRequiredData,
};

describe('IsCalculateTemp Service', () => {
  let service: IsCalculateTempService;
  let httpMock: HttpTestingController;
  let expectedResult: IIsCalculateTemp | IIsCalculateTemp[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(IsCalculateTempService);
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

    it('should create a IsCalculateTemp', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const isCalculateTemp = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(isCalculateTemp).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a IsCalculateTemp', () => {
      const isCalculateTemp = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(isCalculateTemp).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a IsCalculateTemp', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of IsCalculateTemp', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a IsCalculateTemp', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addIsCalculateTempToCollectionIfMissing', () => {
      it('should add a IsCalculateTemp to an empty array', () => {
        const isCalculateTemp: IIsCalculateTemp = sampleWithRequiredData;
        expectedResult = service.addIsCalculateTempToCollectionIfMissing([], isCalculateTemp);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(isCalculateTemp);
      });

      it('should not add a IsCalculateTemp to an array that contains it', () => {
        const isCalculateTemp: IIsCalculateTemp = sampleWithRequiredData;
        const isCalculateTempCollection: IIsCalculateTemp[] = [
          {
            ...isCalculateTemp,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addIsCalculateTempToCollectionIfMissing(isCalculateTempCollection, isCalculateTemp);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a IsCalculateTemp to an array that doesn't contain it", () => {
        const isCalculateTemp: IIsCalculateTemp = sampleWithRequiredData;
        const isCalculateTempCollection: IIsCalculateTemp[] = [sampleWithPartialData];
        expectedResult = service.addIsCalculateTempToCollectionIfMissing(isCalculateTempCollection, isCalculateTemp);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(isCalculateTemp);
      });

      it('should add only unique IsCalculateTemp to an array', () => {
        const isCalculateTempArray: IIsCalculateTemp[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const isCalculateTempCollection: IIsCalculateTemp[] = [sampleWithRequiredData];
        expectedResult = service.addIsCalculateTempToCollectionIfMissing(isCalculateTempCollection, ...isCalculateTempArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const isCalculateTemp: IIsCalculateTemp = sampleWithRequiredData;
        const isCalculateTemp2: IIsCalculateTemp = sampleWithPartialData;
        expectedResult = service.addIsCalculateTempToCollectionIfMissing([], isCalculateTemp, isCalculateTemp2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(isCalculateTemp);
        expect(expectedResult).toContain(isCalculateTemp2);
      });

      it('should accept null and undefined values', () => {
        const isCalculateTemp: IIsCalculateTemp = sampleWithRequiredData;
        expectedResult = service.addIsCalculateTempToCollectionIfMissing([], null, isCalculateTemp, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(isCalculateTemp);
      });

      it('should return initial array if no IsCalculateTemp is added', () => {
        const isCalculateTempCollection: IIsCalculateTemp[] = [sampleWithRequiredData];
        expectedResult = service.addIsCalculateTempToCollectionIfMissing(isCalculateTempCollection, undefined, null);
        expect(expectedResult).toEqual(isCalculateTempCollection);
      });
    });

    describe('compareIsCalculateTemp', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareIsCalculateTemp(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareIsCalculateTemp(entity1, entity2);
        const compareResult2 = service.compareIsCalculateTemp(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareIsCalculateTemp(entity1, entity2);
        const compareResult2 = service.compareIsCalculateTemp(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareIsCalculateTemp(entity1, entity2);
        const compareResult2 = service.compareIsCalculateTemp(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
