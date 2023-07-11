import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IOccupationMaster } from '../occupation-master.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../occupation-master.test-samples';

import { OccupationMasterService } from './occupation-master.service';

const requireRestSample: IOccupationMaster = {
  ...sampleWithRequiredData,
};

describe('OccupationMaster Service', () => {
  let service: OccupationMasterService;
  let httpMock: HttpTestingController;
  let expectedResult: IOccupationMaster | IOccupationMaster[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(OccupationMasterService);
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

    it('should create a OccupationMaster', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const occupationMaster = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(occupationMaster).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a OccupationMaster', () => {
      const occupationMaster = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(occupationMaster).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a OccupationMaster', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of OccupationMaster', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a OccupationMaster', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addOccupationMasterToCollectionIfMissing', () => {
      it('should add a OccupationMaster to an empty array', () => {
        const occupationMaster: IOccupationMaster = sampleWithRequiredData;
        expectedResult = service.addOccupationMasterToCollectionIfMissing([], occupationMaster);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(occupationMaster);
      });

      it('should not add a OccupationMaster to an array that contains it', () => {
        const occupationMaster: IOccupationMaster = sampleWithRequiredData;
        const occupationMasterCollection: IOccupationMaster[] = [
          {
            ...occupationMaster,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addOccupationMasterToCollectionIfMissing(occupationMasterCollection, occupationMaster);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a OccupationMaster to an array that doesn't contain it", () => {
        const occupationMaster: IOccupationMaster = sampleWithRequiredData;
        const occupationMasterCollection: IOccupationMaster[] = [sampleWithPartialData];
        expectedResult = service.addOccupationMasterToCollectionIfMissing(occupationMasterCollection, occupationMaster);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(occupationMaster);
      });

      it('should add only unique OccupationMaster to an array', () => {
        const occupationMasterArray: IOccupationMaster[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const occupationMasterCollection: IOccupationMaster[] = [sampleWithRequiredData];
        expectedResult = service.addOccupationMasterToCollectionIfMissing(occupationMasterCollection, ...occupationMasterArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const occupationMaster: IOccupationMaster = sampleWithRequiredData;
        const occupationMaster2: IOccupationMaster = sampleWithPartialData;
        expectedResult = service.addOccupationMasterToCollectionIfMissing([], occupationMaster, occupationMaster2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(occupationMaster);
        expect(expectedResult).toContain(occupationMaster2);
      });

      it('should accept null and undefined values', () => {
        const occupationMaster: IOccupationMaster = sampleWithRequiredData;
        expectedResult = service.addOccupationMasterToCollectionIfMissing([], null, occupationMaster, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(occupationMaster);
      });

      it('should return initial array if no OccupationMaster is added', () => {
        const occupationMasterCollection: IOccupationMaster[] = [sampleWithRequiredData];
        expectedResult = service.addOccupationMasterToCollectionIfMissing(occupationMasterCollection, undefined, null);
        expect(expectedResult).toEqual(occupationMasterCollection);
      });
    });

    describe('compareOccupationMaster', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareOccupationMaster(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareOccupationMaster(entity1, entity2);
        const compareResult2 = service.compareOccupationMaster(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareOccupationMaster(entity1, entity2);
        const compareResult2 = service.compareOccupationMaster(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareOccupationMaster(entity1, entity2);
        const compareResult2 = service.compareOccupationMaster(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
