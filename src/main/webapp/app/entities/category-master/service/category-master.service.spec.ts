import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICategoryMaster } from '../category-master.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../category-master.test-samples';

import { CategoryMasterService } from './category-master.service';

const requireRestSample: ICategoryMaster = {
  ...sampleWithRequiredData,
};

describe('CategoryMaster Service', () => {
  let service: CategoryMasterService;
  let httpMock: HttpTestingController;
  let expectedResult: ICategoryMaster | ICategoryMaster[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CategoryMasterService);
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

    it('should create a CategoryMaster', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const categoryMaster = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(categoryMaster).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CategoryMaster', () => {
      const categoryMaster = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(categoryMaster).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CategoryMaster', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CategoryMaster', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CategoryMaster', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCategoryMasterToCollectionIfMissing', () => {
      it('should add a CategoryMaster to an empty array', () => {
        const categoryMaster: ICategoryMaster = sampleWithRequiredData;
        expectedResult = service.addCategoryMasterToCollectionIfMissing([], categoryMaster);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(categoryMaster);
      });

      it('should not add a CategoryMaster to an array that contains it', () => {
        const categoryMaster: ICategoryMaster = sampleWithRequiredData;
        const categoryMasterCollection: ICategoryMaster[] = [
          {
            ...categoryMaster,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCategoryMasterToCollectionIfMissing(categoryMasterCollection, categoryMaster);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CategoryMaster to an array that doesn't contain it", () => {
        const categoryMaster: ICategoryMaster = sampleWithRequiredData;
        const categoryMasterCollection: ICategoryMaster[] = [sampleWithPartialData];
        expectedResult = service.addCategoryMasterToCollectionIfMissing(categoryMasterCollection, categoryMaster);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(categoryMaster);
      });

      it('should add only unique CategoryMaster to an array', () => {
        const categoryMasterArray: ICategoryMaster[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const categoryMasterCollection: ICategoryMaster[] = [sampleWithRequiredData];
        expectedResult = service.addCategoryMasterToCollectionIfMissing(categoryMasterCollection, ...categoryMasterArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const categoryMaster: ICategoryMaster = sampleWithRequiredData;
        const categoryMaster2: ICategoryMaster = sampleWithPartialData;
        expectedResult = service.addCategoryMasterToCollectionIfMissing([], categoryMaster, categoryMaster2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(categoryMaster);
        expect(expectedResult).toContain(categoryMaster2);
      });

      it('should accept null and undefined values', () => {
        const categoryMaster: ICategoryMaster = sampleWithRequiredData;
        expectedResult = service.addCategoryMasterToCollectionIfMissing([], null, categoryMaster, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(categoryMaster);
      });

      it('should return initial array if no CategoryMaster is added', () => {
        const categoryMasterCollection: ICategoryMaster[] = [sampleWithRequiredData];
        expectedResult = service.addCategoryMasterToCollectionIfMissing(categoryMasterCollection, undefined, null);
        expect(expectedResult).toEqual(categoryMasterCollection);
      });
    });

    describe('compareCategoryMaster', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCategoryMaster(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCategoryMaster(entity1, entity2);
        const compareResult2 = service.compareCategoryMaster(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareCategoryMaster(entity1, entity2);
        const compareResult2 = service.compareCategoryMaster(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareCategoryMaster(entity1, entity2);
        const compareResult2 = service.compareCategoryMaster(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
