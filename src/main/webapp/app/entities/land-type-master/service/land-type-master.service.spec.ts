import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ILandTypeMaster } from '../land-type-master.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../land-type-master.test-samples';

import { LandTypeMasterService } from './land-type-master.service';

const requireRestSample: ILandTypeMaster = {
  ...sampleWithRequiredData,
};

describe('LandTypeMaster Service', () => {
  let service: LandTypeMasterService;
  let httpMock: HttpTestingController;
  let expectedResult: ILandTypeMaster | ILandTypeMaster[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(LandTypeMasterService);
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

    it('should create a LandTypeMaster', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const landTypeMaster = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(landTypeMaster).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a LandTypeMaster', () => {
      const landTypeMaster = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(landTypeMaster).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a LandTypeMaster', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of LandTypeMaster', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a LandTypeMaster', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addLandTypeMasterToCollectionIfMissing', () => {
      it('should add a LandTypeMaster to an empty array', () => {
        const landTypeMaster: ILandTypeMaster = sampleWithRequiredData;
        expectedResult = service.addLandTypeMasterToCollectionIfMissing([], landTypeMaster);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(landTypeMaster);
      });

      it('should not add a LandTypeMaster to an array that contains it', () => {
        const landTypeMaster: ILandTypeMaster = sampleWithRequiredData;
        const landTypeMasterCollection: ILandTypeMaster[] = [
          {
            ...landTypeMaster,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addLandTypeMasterToCollectionIfMissing(landTypeMasterCollection, landTypeMaster);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a LandTypeMaster to an array that doesn't contain it", () => {
        const landTypeMaster: ILandTypeMaster = sampleWithRequiredData;
        const landTypeMasterCollection: ILandTypeMaster[] = [sampleWithPartialData];
        expectedResult = service.addLandTypeMasterToCollectionIfMissing(landTypeMasterCollection, landTypeMaster);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(landTypeMaster);
      });

      it('should add only unique LandTypeMaster to an array', () => {
        const landTypeMasterArray: ILandTypeMaster[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const landTypeMasterCollection: ILandTypeMaster[] = [sampleWithRequiredData];
        expectedResult = service.addLandTypeMasterToCollectionIfMissing(landTypeMasterCollection, ...landTypeMasterArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const landTypeMaster: ILandTypeMaster = sampleWithRequiredData;
        const landTypeMaster2: ILandTypeMaster = sampleWithPartialData;
        expectedResult = service.addLandTypeMasterToCollectionIfMissing([], landTypeMaster, landTypeMaster2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(landTypeMaster);
        expect(expectedResult).toContain(landTypeMaster2);
      });

      it('should accept null and undefined values', () => {
        const landTypeMaster: ILandTypeMaster = sampleWithRequiredData;
        expectedResult = service.addLandTypeMasterToCollectionIfMissing([], null, landTypeMaster, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(landTypeMaster);
      });

      it('should return initial array if no LandTypeMaster is added', () => {
        const landTypeMasterCollection: ILandTypeMaster[] = [sampleWithRequiredData];
        expectedResult = service.addLandTypeMasterToCollectionIfMissing(landTypeMasterCollection, undefined, null);
        expect(expectedResult).toEqual(landTypeMasterCollection);
      });
    });

    describe('compareLandTypeMaster', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareLandTypeMaster(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareLandTypeMaster(entity1, entity2);
        const compareResult2 = service.compareLandTypeMaster(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareLandTypeMaster(entity1, entity2);
        const compareResult2 = service.compareLandTypeMaster(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareLandTypeMaster(entity1, entity2);
        const compareResult2 = service.compareLandTypeMaster(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
