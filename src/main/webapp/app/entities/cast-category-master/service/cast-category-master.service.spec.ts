import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICastCategoryMaster } from '../cast-category-master.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../cast-category-master.test-samples';

import { CastCategoryMasterService } from './cast-category-master.service';

const requireRestSample: ICastCategoryMaster = {
  ...sampleWithRequiredData,
};

describe('CastCategoryMaster Service', () => {
  let service: CastCategoryMasterService;
  let httpMock: HttpTestingController;
  let expectedResult: ICastCategoryMaster | ICastCategoryMaster[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CastCategoryMasterService);
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

    it('should create a CastCategoryMaster', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const castCategoryMaster = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(castCategoryMaster).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CastCategoryMaster', () => {
      const castCategoryMaster = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(castCategoryMaster).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CastCategoryMaster', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CastCategoryMaster', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CastCategoryMaster', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCastCategoryMasterToCollectionIfMissing', () => {
      it('should add a CastCategoryMaster to an empty array', () => {
        const castCategoryMaster: ICastCategoryMaster = sampleWithRequiredData;
        expectedResult = service.addCastCategoryMasterToCollectionIfMissing([], castCategoryMaster);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(castCategoryMaster);
      });

      it('should not add a CastCategoryMaster to an array that contains it', () => {
        const castCategoryMaster: ICastCategoryMaster = sampleWithRequiredData;
        const castCategoryMasterCollection: ICastCategoryMaster[] = [
          {
            ...castCategoryMaster,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCastCategoryMasterToCollectionIfMissing(castCategoryMasterCollection, castCategoryMaster);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CastCategoryMaster to an array that doesn't contain it", () => {
        const castCategoryMaster: ICastCategoryMaster = sampleWithRequiredData;
        const castCategoryMasterCollection: ICastCategoryMaster[] = [sampleWithPartialData];
        expectedResult = service.addCastCategoryMasterToCollectionIfMissing(castCategoryMasterCollection, castCategoryMaster);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(castCategoryMaster);
      });

      it('should add only unique CastCategoryMaster to an array', () => {
        const castCategoryMasterArray: ICastCategoryMaster[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const castCategoryMasterCollection: ICastCategoryMaster[] = [sampleWithRequiredData];
        expectedResult = service.addCastCategoryMasterToCollectionIfMissing(castCategoryMasterCollection, ...castCategoryMasterArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const castCategoryMaster: ICastCategoryMaster = sampleWithRequiredData;
        const castCategoryMaster2: ICastCategoryMaster = sampleWithPartialData;
        expectedResult = service.addCastCategoryMasterToCollectionIfMissing([], castCategoryMaster, castCategoryMaster2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(castCategoryMaster);
        expect(expectedResult).toContain(castCategoryMaster2);
      });

      it('should accept null and undefined values', () => {
        const castCategoryMaster: ICastCategoryMaster = sampleWithRequiredData;
        expectedResult = service.addCastCategoryMasterToCollectionIfMissing([], null, castCategoryMaster, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(castCategoryMaster);
      });

      it('should return initial array if no CastCategoryMaster is added', () => {
        const castCategoryMasterCollection: ICastCategoryMaster[] = [sampleWithRequiredData];
        expectedResult = service.addCastCategoryMasterToCollectionIfMissing(castCategoryMasterCollection, undefined, null);
        expect(expectedResult).toEqual(castCategoryMasterCollection);
      });
    });

    describe('compareCastCategoryMaster', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCastCategoryMaster(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCastCategoryMaster(entity1, entity2);
        const compareResult2 = service.compareCastCategoryMaster(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareCastCategoryMaster(entity1, entity2);
        const compareResult2 = service.compareCastCategoryMaster(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareCastCategoryMaster(entity1, entity2);
        const compareResult2 = service.compareCastCategoryMaster(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
