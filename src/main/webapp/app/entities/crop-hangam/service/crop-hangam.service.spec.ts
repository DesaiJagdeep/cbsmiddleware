import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICropHangam } from '../crop-hangam.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../crop-hangam.test-samples';

import { CropHangamService } from './crop-hangam.service';

const requireRestSample: ICropHangam = {
  ...sampleWithRequiredData,
};

describe('CropHangam Service', () => {
  let service: CropHangamService;
  let httpMock: HttpTestingController;
  let expectedResult: ICropHangam | ICropHangam[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CropHangamService);
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

    it('should create a CropHangam', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const cropHangam = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(cropHangam).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CropHangam', () => {
      const cropHangam = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(cropHangam).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CropHangam', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CropHangam', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CropHangam', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCropHangamToCollectionIfMissing', () => {
      it('should add a CropHangam to an empty array', () => {
        const cropHangam: ICropHangam = sampleWithRequiredData;
        expectedResult = service.addCropHangamToCollectionIfMissing([], cropHangam);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(cropHangam);
      });

      it('should not add a CropHangam to an array that contains it', () => {
        const cropHangam: ICropHangam = sampleWithRequiredData;
        const cropHangamCollection: ICropHangam[] = [
          {
            ...cropHangam,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCropHangamToCollectionIfMissing(cropHangamCollection, cropHangam);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CropHangam to an array that doesn't contain it", () => {
        const cropHangam: ICropHangam = sampleWithRequiredData;
        const cropHangamCollection: ICropHangam[] = [sampleWithPartialData];
        expectedResult = service.addCropHangamToCollectionIfMissing(cropHangamCollection, cropHangam);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(cropHangam);
      });

      it('should add only unique CropHangam to an array', () => {
        const cropHangamArray: ICropHangam[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const cropHangamCollection: ICropHangam[] = [sampleWithRequiredData];
        expectedResult = service.addCropHangamToCollectionIfMissing(cropHangamCollection, ...cropHangamArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const cropHangam: ICropHangam = sampleWithRequiredData;
        const cropHangam2: ICropHangam = sampleWithPartialData;
        expectedResult = service.addCropHangamToCollectionIfMissing([], cropHangam, cropHangam2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(cropHangam);
        expect(expectedResult).toContain(cropHangam2);
      });

      it('should accept null and undefined values', () => {
        const cropHangam: ICropHangam = sampleWithRequiredData;
        expectedResult = service.addCropHangamToCollectionIfMissing([], null, cropHangam, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(cropHangam);
      });

      it('should return initial array if no CropHangam is added', () => {
        const cropHangamCollection: ICropHangam[] = [sampleWithRequiredData];
        expectedResult = service.addCropHangamToCollectionIfMissing(cropHangamCollection, undefined, null);
        expect(expectedResult).toEqual(cropHangamCollection);
      });
    });

    describe('compareCropHangam', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCropHangam(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCropHangam(entity1, entity2);
        const compareResult2 = service.compareCropHangam(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareCropHangam(entity1, entity2);
        const compareResult2 = service.compareCropHangam(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareCropHangam(entity1, entity2);
        const compareResult2 = service.compareCropHangam(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
