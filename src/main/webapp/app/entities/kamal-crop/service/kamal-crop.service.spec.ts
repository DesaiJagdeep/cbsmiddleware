import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IKamalCrop } from '../kamal-crop.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../kamal-crop.test-samples';

import { KamalCropService } from './kamal-crop.service';

const requireRestSample: IKamalCrop = {
  ...sampleWithRequiredData,
};

describe('KamalCrop Service', () => {
  let service: KamalCropService;
  let httpMock: HttpTestingController;
  let expectedResult: IKamalCrop | IKamalCrop[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(KamalCropService);
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

    it('should create a KamalCrop', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const kamalCrop = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(kamalCrop).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a KamalCrop', () => {
      const kamalCrop = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(kamalCrop).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a KamalCrop', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of KamalCrop', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a KamalCrop', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addKamalCropToCollectionIfMissing', () => {
      it('should add a KamalCrop to an empty array', () => {
        const kamalCrop: IKamalCrop = sampleWithRequiredData;
        expectedResult = service.addKamalCropToCollectionIfMissing([], kamalCrop);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(kamalCrop);
      });

      it('should not add a KamalCrop to an array that contains it', () => {
        const kamalCrop: IKamalCrop = sampleWithRequiredData;
        const kamalCropCollection: IKamalCrop[] = [
          {
            ...kamalCrop,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addKamalCropToCollectionIfMissing(kamalCropCollection, kamalCrop);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a KamalCrop to an array that doesn't contain it", () => {
        const kamalCrop: IKamalCrop = sampleWithRequiredData;
        const kamalCropCollection: IKamalCrop[] = [sampleWithPartialData];
        expectedResult = service.addKamalCropToCollectionIfMissing(kamalCropCollection, kamalCrop);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(kamalCrop);
      });

      it('should add only unique KamalCrop to an array', () => {
        const kamalCropArray: IKamalCrop[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const kamalCropCollection: IKamalCrop[] = [sampleWithRequiredData];
        expectedResult = service.addKamalCropToCollectionIfMissing(kamalCropCollection, ...kamalCropArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const kamalCrop: IKamalCrop = sampleWithRequiredData;
        const kamalCrop2: IKamalCrop = sampleWithPartialData;
        expectedResult = service.addKamalCropToCollectionIfMissing([], kamalCrop, kamalCrop2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(kamalCrop);
        expect(expectedResult).toContain(kamalCrop2);
      });

      it('should accept null and undefined values', () => {
        const kamalCrop: IKamalCrop = sampleWithRequiredData;
        expectedResult = service.addKamalCropToCollectionIfMissing([], null, kamalCrop, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(kamalCrop);
      });

      it('should return initial array if no KamalCrop is added', () => {
        const kamalCropCollection: IKamalCrop[] = [sampleWithRequiredData];
        expectedResult = service.addKamalCropToCollectionIfMissing(kamalCropCollection, undefined, null);
        expect(expectedResult).toEqual(kamalCropCollection);
      });
    });

    describe('compareKamalCrop', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareKamalCrop(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareKamalCrop(entity1, entity2);
        const compareResult2 = service.compareKamalCrop(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareKamalCrop(entity1, entity2);
        const compareResult2 = service.compareKamalCrop(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareKamalCrop(entity1, entity2);
        const compareResult2 = service.compareKamalCrop(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
