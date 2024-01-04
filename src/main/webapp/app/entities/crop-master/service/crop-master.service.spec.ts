import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICropMaster } from '../crop-master.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../crop-master.test-samples';

import { CropMasterService, RestCropMaster } from './crop-master.service';

const requireRestSample: RestCropMaster = {
  ...sampleWithRequiredData,
  vatapFromDay: sampleWithRequiredData.vatapFromDay?.toJSON(),
  vatapToMonth: sampleWithRequiredData.vatapToMonth?.toJSON(),
  lastToDay: sampleWithRequiredData.lastToDay?.toJSON(),
  lastToMonth: sampleWithRequiredData.lastToMonth?.toJSON(),
  dueDay: sampleWithRequiredData.dueDay?.toJSON(),
  dueMonth: sampleWithRequiredData.dueMonth?.toJSON(),
};

describe('CropMaster Service', () => {
  let service: CropMasterService;
  let httpMock: HttpTestingController;
  let expectedResult: ICropMaster | ICropMaster[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CropMasterService);
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

    it('should create a CropMaster', () => {
      const cropMaster = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(cropMaster).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CropMaster', () => {
      const cropMaster = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(cropMaster).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CropMaster', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CropMaster', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CropMaster', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCropMasterToCollectionIfMissing', () => {
      it('should add a CropMaster to an empty array', () => {
        const cropMaster: ICropMaster = sampleWithRequiredData;
        expectedResult = service.addCropMasterToCollectionIfMissing([], cropMaster);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(cropMaster);
      });

      it('should not add a CropMaster to an array that contains it', () => {
        const cropMaster: ICropMaster = sampleWithRequiredData;
        const cropMasterCollection: ICropMaster[] = [
          {
            ...cropMaster,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCropMasterToCollectionIfMissing(cropMasterCollection, cropMaster);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CropMaster to an array that doesn't contain it", () => {
        const cropMaster: ICropMaster = sampleWithRequiredData;
        const cropMasterCollection: ICropMaster[] = [sampleWithPartialData];
        expectedResult = service.addCropMasterToCollectionIfMissing(cropMasterCollection, cropMaster);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(cropMaster);
      });

      it('should add only unique CropMaster to an array', () => {
        const cropMasterArray: ICropMaster[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const cropMasterCollection: ICropMaster[] = [sampleWithRequiredData];
        expectedResult = service.addCropMasterToCollectionIfMissing(cropMasterCollection, ...cropMasterArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const cropMaster: ICropMaster = sampleWithRequiredData;
        const cropMaster2: ICropMaster = sampleWithPartialData;
        expectedResult = service.addCropMasterToCollectionIfMissing([], cropMaster, cropMaster2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(cropMaster);
        expect(expectedResult).toContain(cropMaster2);
      });

      it('should accept null and undefined values', () => {
        const cropMaster: ICropMaster = sampleWithRequiredData;
        expectedResult = service.addCropMasterToCollectionIfMissing([], null, cropMaster, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(cropMaster);
      });

      it('should return initial array if no CropMaster is added', () => {
        const cropMasterCollection: ICropMaster[] = [sampleWithRequiredData];
        expectedResult = service.addCropMasterToCollectionIfMissing(cropMasterCollection, undefined, null);
        expect(expectedResult).toEqual(cropMasterCollection);
      });
    });

    describe('compareCropMaster', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCropMaster(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCropMaster(entity1, entity2);
        const compareResult2 = service.compareCropMaster(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareCropMaster(entity1, entity2);
        const compareResult2 = service.compareCropMaster(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareCropMaster(entity1, entity2);
        const compareResult2 = service.compareCropMaster(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
