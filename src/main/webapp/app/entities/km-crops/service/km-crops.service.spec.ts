import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IKmCrops } from '../km-crops.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../km-crops.test-samples';

import { KmCropsService } from './km-crops.service';

const requireRestSample: IKmCrops = {
  ...sampleWithRequiredData,
};

describe('KmCrops Service', () => {
  let service: KmCropsService;
  let httpMock: HttpTestingController;
  let expectedResult: IKmCrops | IKmCrops[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(KmCropsService);
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

    it('should create a KmCrops', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const kmCrops = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(kmCrops).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a KmCrops', () => {
      const kmCrops = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(kmCrops).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a KmCrops', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of KmCrops', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a KmCrops', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addKmCropsToCollectionIfMissing', () => {
      it('should add a KmCrops to an empty array', () => {
        const kmCrops: IKmCrops = sampleWithRequiredData;
        expectedResult = service.addKmCropsToCollectionIfMissing([], kmCrops);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(kmCrops);
      });

      it('should not add a KmCrops to an array that contains it', () => {
        const kmCrops: IKmCrops = sampleWithRequiredData;
        const kmCropsCollection: IKmCrops[] = [
          {
            ...kmCrops,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addKmCropsToCollectionIfMissing(kmCropsCollection, kmCrops);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a KmCrops to an array that doesn't contain it", () => {
        const kmCrops: IKmCrops = sampleWithRequiredData;
        const kmCropsCollection: IKmCrops[] = [sampleWithPartialData];
        expectedResult = service.addKmCropsToCollectionIfMissing(kmCropsCollection, kmCrops);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(kmCrops);
      });

      it('should add only unique KmCrops to an array', () => {
        const kmCropsArray: IKmCrops[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const kmCropsCollection: IKmCrops[] = [sampleWithRequiredData];
        expectedResult = service.addKmCropsToCollectionIfMissing(kmCropsCollection, ...kmCropsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const kmCrops: IKmCrops = sampleWithRequiredData;
        const kmCrops2: IKmCrops = sampleWithPartialData;
        expectedResult = service.addKmCropsToCollectionIfMissing([], kmCrops, kmCrops2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(kmCrops);
        expect(expectedResult).toContain(kmCrops2);
      });

      it('should accept null and undefined values', () => {
        const kmCrops: IKmCrops = sampleWithRequiredData;
        expectedResult = service.addKmCropsToCollectionIfMissing([], null, kmCrops, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(kmCrops);
      });

      it('should return initial array if no KmCrops is added', () => {
        const kmCropsCollection: IKmCrops[] = [sampleWithRequiredData];
        expectedResult = service.addKmCropsToCollectionIfMissing(kmCropsCollection, undefined, null);
        expect(expectedResult).toEqual(kmCropsCollection);
      });
    });

    describe('compareKmCrops', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareKmCrops(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareKmCrops(entity1, entity2);
        const compareResult2 = service.compareKmCrops(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareKmCrops(entity1, entity2);
        const compareResult2 = service.compareKmCrops(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareKmCrops(entity1, entity2);
        const compareResult2 = service.compareKmCrops(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
