import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IKmMaganiCrop } from '../km-magani-crop.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../km-magani-crop.test-samples';

import { KmMaganiCropService, RestKmMaganiCrop } from './km-magani-crop.service';

const requireRestSample: RestKmMaganiCrop = {
  ...sampleWithRequiredData,
  cropDueDate: sampleWithRequiredData.cropDueDate?.toJSON(),
  cropVasuliPatraDate: sampleWithRequiredData.cropVasuliPatraDate?.toJSON(),
};

describe('KmMaganiCrop Service', () => {
  let service: KmMaganiCropService;
  let httpMock: HttpTestingController;
  let expectedResult: IKmMaganiCrop | IKmMaganiCrop[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(KmMaganiCropService);
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

    it('should create a KmMaganiCrop', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const kmMaganiCrop = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(kmMaganiCrop).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a KmMaganiCrop', () => {
      const kmMaganiCrop = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(kmMaganiCrop).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a KmMaganiCrop', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of KmMaganiCrop', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a KmMaganiCrop', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addKmMaganiCropToCollectionIfMissing', () => {
      it('should add a KmMaganiCrop to an empty array', () => {
        const kmMaganiCrop: IKmMaganiCrop = sampleWithRequiredData;
        expectedResult = service.addKmMaganiCropToCollectionIfMissing([], kmMaganiCrop);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(kmMaganiCrop);
      });

      it('should not add a KmMaganiCrop to an array that contains it', () => {
        const kmMaganiCrop: IKmMaganiCrop = sampleWithRequiredData;
        const kmMaganiCropCollection: IKmMaganiCrop[] = [
          {
            ...kmMaganiCrop,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addKmMaganiCropToCollectionIfMissing(kmMaganiCropCollection, kmMaganiCrop);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a KmMaganiCrop to an array that doesn't contain it", () => {
        const kmMaganiCrop: IKmMaganiCrop = sampleWithRequiredData;
        const kmMaganiCropCollection: IKmMaganiCrop[] = [sampleWithPartialData];
        expectedResult = service.addKmMaganiCropToCollectionIfMissing(kmMaganiCropCollection, kmMaganiCrop);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(kmMaganiCrop);
      });

      it('should add only unique KmMaganiCrop to an array', () => {
        const kmMaganiCropArray: IKmMaganiCrop[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const kmMaganiCropCollection: IKmMaganiCrop[] = [sampleWithRequiredData];
        expectedResult = service.addKmMaganiCropToCollectionIfMissing(kmMaganiCropCollection, ...kmMaganiCropArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const kmMaganiCrop: IKmMaganiCrop = sampleWithRequiredData;
        const kmMaganiCrop2: IKmMaganiCrop = sampleWithPartialData;
        expectedResult = service.addKmMaganiCropToCollectionIfMissing([], kmMaganiCrop, kmMaganiCrop2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(kmMaganiCrop);
        expect(expectedResult).toContain(kmMaganiCrop2);
      });

      it('should accept null and undefined values', () => {
        const kmMaganiCrop: IKmMaganiCrop = sampleWithRequiredData;
        expectedResult = service.addKmMaganiCropToCollectionIfMissing([], null, kmMaganiCrop, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(kmMaganiCrop);
      });

      it('should return initial array if no KmMaganiCrop is added', () => {
        const kmMaganiCropCollection: IKmMaganiCrop[] = [sampleWithRequiredData];
        expectedResult = service.addKmMaganiCropToCollectionIfMissing(kmMaganiCropCollection, undefined, null);
        expect(expectedResult).toEqual(kmMaganiCropCollection);
      });
    });

    describe('compareKmMaganiCrop', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareKmMaganiCrop(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareKmMaganiCrop(entity1, entity2);
        const compareResult2 = service.compareKmMaganiCrop(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareKmMaganiCrop(entity1, entity2);
        const compareResult2 = service.compareKmMaganiCrop(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareKmMaganiCrop(entity1, entity2);
        const compareResult2 = service.compareKmMaganiCrop(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
