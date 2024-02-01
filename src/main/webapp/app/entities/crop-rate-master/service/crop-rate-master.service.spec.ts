import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICropRateMaster } from '../crop-rate-master.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../crop-rate-master.test-samples';

import { CropRateMasterService } from './crop-rate-master.service';

const requireRestSample: ICropRateMaster = {
  ...sampleWithRequiredData,
};

describe('CropRateMaster Service', () => {
  let service: CropRateMasterService;
  let httpMock: HttpTestingController;
  let expectedResult: ICropRateMaster | ICropRateMaster[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CropRateMasterService);
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

    it('should create a CropRateMaster', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const cropRateMaster = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(cropRateMaster).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CropRateMaster', () => {
      const cropRateMaster = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(cropRateMaster).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CropRateMaster', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CropRateMaster', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CropRateMaster', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCropRateMasterToCollectionIfMissing', () => {
      it('should add a CropRateMaster to an empty array', () => {
        const cropRateMaster: ICropRateMaster = sampleWithRequiredData;
        expectedResult = service.addCropRateMasterToCollectionIfMissing([], cropRateMaster);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(cropRateMaster);
      });

      it('should not add a CropRateMaster to an array that contains it', () => {
        const cropRateMaster: ICropRateMaster = sampleWithRequiredData;
        const cropRateMasterCollection: ICropRateMaster[] = [
          {
            ...cropRateMaster,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCropRateMasterToCollectionIfMissing(cropRateMasterCollection, cropRateMaster);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CropRateMaster to an array that doesn't contain it", () => {
        const cropRateMaster: ICropRateMaster = sampleWithRequiredData;
        const cropRateMasterCollection: ICropRateMaster[] = [sampleWithPartialData];
        expectedResult = service.addCropRateMasterToCollectionIfMissing(cropRateMasterCollection, cropRateMaster);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(cropRateMaster);
      });

      it('should add only unique CropRateMaster to an array', () => {
        const cropRateMasterArray: ICropRateMaster[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const cropRateMasterCollection: ICropRateMaster[] = [sampleWithRequiredData];
        expectedResult = service.addCropRateMasterToCollectionIfMissing(cropRateMasterCollection, ...cropRateMasterArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const cropRateMaster: ICropRateMaster = sampleWithRequiredData;
        const cropRateMaster2: ICropRateMaster = sampleWithPartialData;
        expectedResult = service.addCropRateMasterToCollectionIfMissing([], cropRateMaster, cropRateMaster2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(cropRateMaster);
        expect(expectedResult).toContain(cropRateMaster2);
      });

      it('should accept null and undefined values', () => {
        const cropRateMaster: ICropRateMaster = sampleWithRequiredData;
        expectedResult = service.addCropRateMasterToCollectionIfMissing([], null, cropRateMaster, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(cropRateMaster);
      });

      it('should return initial array if no CropRateMaster is added', () => {
        const cropRateMasterCollection: ICropRateMaster[] = [sampleWithRequiredData];
        expectedResult = service.addCropRateMasterToCollectionIfMissing(cropRateMasterCollection, undefined, null);
        expect(expectedResult).toEqual(cropRateMasterCollection);
      });
    });

    describe('compareCropRateMaster', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCropRateMaster(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCropRateMaster(entity1, entity2);
        const compareResult2 = service.compareCropRateMaster(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareCropRateMaster(entity1, entity2);
        const compareResult2 = service.compareCropRateMaster(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareCropRateMaster(entity1, entity2);
        const compareResult2 = service.compareCropRateMaster(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
