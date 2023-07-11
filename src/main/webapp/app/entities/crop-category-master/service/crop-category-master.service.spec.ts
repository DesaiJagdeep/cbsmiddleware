import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICropCategoryMaster } from '../crop-category-master.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../crop-category-master.test-samples';

import { CropCategoryMasterService } from './crop-category-master.service';

const requireRestSample: ICropCategoryMaster = {
  ...sampleWithRequiredData,
};

describe('CropCategoryMaster Service', () => {
  let service: CropCategoryMasterService;
  let httpMock: HttpTestingController;
  let expectedResult: ICropCategoryMaster | ICropCategoryMaster[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CropCategoryMasterService);
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

    it('should create a CropCategoryMaster', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const cropCategoryMaster = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(cropCategoryMaster).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CropCategoryMaster', () => {
      const cropCategoryMaster = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(cropCategoryMaster).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CropCategoryMaster', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CropCategoryMaster', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CropCategoryMaster', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCropCategoryMasterToCollectionIfMissing', () => {
      it('should add a CropCategoryMaster to an empty array', () => {
        const cropCategoryMaster: ICropCategoryMaster = sampleWithRequiredData;
        expectedResult = service.addCropCategoryMasterToCollectionIfMissing([], cropCategoryMaster);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(cropCategoryMaster);
      });

      it('should not add a CropCategoryMaster to an array that contains it', () => {
        const cropCategoryMaster: ICropCategoryMaster = sampleWithRequiredData;
        const cropCategoryMasterCollection: ICropCategoryMaster[] = [
          {
            ...cropCategoryMaster,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCropCategoryMasterToCollectionIfMissing(cropCategoryMasterCollection, cropCategoryMaster);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CropCategoryMaster to an array that doesn't contain it", () => {
        const cropCategoryMaster: ICropCategoryMaster = sampleWithRequiredData;
        const cropCategoryMasterCollection: ICropCategoryMaster[] = [sampleWithPartialData];
        expectedResult = service.addCropCategoryMasterToCollectionIfMissing(cropCategoryMasterCollection, cropCategoryMaster);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(cropCategoryMaster);
      });

      it('should add only unique CropCategoryMaster to an array', () => {
        const cropCategoryMasterArray: ICropCategoryMaster[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const cropCategoryMasterCollection: ICropCategoryMaster[] = [sampleWithRequiredData];
        expectedResult = service.addCropCategoryMasterToCollectionIfMissing(cropCategoryMasterCollection, ...cropCategoryMasterArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const cropCategoryMaster: ICropCategoryMaster = sampleWithRequiredData;
        const cropCategoryMaster2: ICropCategoryMaster = sampleWithPartialData;
        expectedResult = service.addCropCategoryMasterToCollectionIfMissing([], cropCategoryMaster, cropCategoryMaster2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(cropCategoryMaster);
        expect(expectedResult).toContain(cropCategoryMaster2);
      });

      it('should accept null and undefined values', () => {
        const cropCategoryMaster: ICropCategoryMaster = sampleWithRequiredData;
        expectedResult = service.addCropCategoryMasterToCollectionIfMissing([], null, cropCategoryMaster, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(cropCategoryMaster);
      });

      it('should return initial array if no CropCategoryMaster is added', () => {
        const cropCategoryMasterCollection: ICropCategoryMaster[] = [sampleWithRequiredData];
        expectedResult = service.addCropCategoryMasterToCollectionIfMissing(cropCategoryMasterCollection, undefined, null);
        expect(expectedResult).toEqual(cropCategoryMasterCollection);
      });
    });

    describe('compareCropCategoryMaster', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCropCategoryMaster(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCropCategoryMaster(entity1, entity2);
        const compareResult2 = service.compareCropCategoryMaster(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareCropCategoryMaster(entity1, entity2);
        const compareResult2 = service.compareCropCategoryMaster(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareCropCategoryMaster(entity1, entity2);
        const compareResult2 = service.compareCropCategoryMaster(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
