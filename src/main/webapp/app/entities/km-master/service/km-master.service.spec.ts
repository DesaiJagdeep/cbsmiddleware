import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IKmMaster } from '../km-master.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../km-master.test-samples';

import { KmMasterService } from './km-master.service';

const requireRestSample: IKmMaster = {
  ...sampleWithRequiredData,
};

describe('KmMaster Service', () => {
  let service: KmMasterService;
  let httpMock: HttpTestingController;
  let expectedResult: IKmMaster | IKmMaster[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(KmMasterService);
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

    it('should create a KmMaster', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const kmMaster = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(kmMaster).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a KmMaster', () => {
      const kmMaster = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(kmMaster).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a KmMaster', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of KmMaster', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a KmMaster', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addKmMasterToCollectionIfMissing', () => {
      it('should add a KmMaster to an empty array', () => {
        const kmMaster: IKmMaster = sampleWithRequiredData;
        expectedResult = service.addKmMasterToCollectionIfMissing([], kmMaster);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(kmMaster);
      });

      it('should not add a KmMaster to an array that contains it', () => {
        const kmMaster: IKmMaster = sampleWithRequiredData;
        const kmMasterCollection: IKmMaster[] = [
          {
            ...kmMaster,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addKmMasterToCollectionIfMissing(kmMasterCollection, kmMaster);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a KmMaster to an array that doesn't contain it", () => {
        const kmMaster: IKmMaster = sampleWithRequiredData;
        const kmMasterCollection: IKmMaster[] = [sampleWithPartialData];
        expectedResult = service.addKmMasterToCollectionIfMissing(kmMasterCollection, kmMaster);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(kmMaster);
      });

      it('should add only unique KmMaster to an array', () => {
        const kmMasterArray: IKmMaster[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const kmMasterCollection: IKmMaster[] = [sampleWithRequiredData];
        expectedResult = service.addKmMasterToCollectionIfMissing(kmMasterCollection, ...kmMasterArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const kmMaster: IKmMaster = sampleWithRequiredData;
        const kmMaster2: IKmMaster = sampleWithPartialData;
        expectedResult = service.addKmMasterToCollectionIfMissing([], kmMaster, kmMaster2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(kmMaster);
        expect(expectedResult).toContain(kmMaster2);
      });

      it('should accept null and undefined values', () => {
        const kmMaster: IKmMaster = sampleWithRequiredData;
        expectedResult = service.addKmMasterToCollectionIfMissing([], null, kmMaster, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(kmMaster);
      });

      it('should return initial array if no KmMaster is added', () => {
        const kmMasterCollection: IKmMaster[] = [sampleWithRequiredData];
        expectedResult = service.addKmMasterToCollectionIfMissing(kmMasterCollection, undefined, null);
        expect(expectedResult).toEqual(kmMasterCollection);
      });
    });

    describe('compareKmMaster', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareKmMaster(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareKmMaster(entity1, entity2);
        const compareResult2 = service.compareKmMaster(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareKmMaster(entity1, entity2);
        const compareResult2 = service.compareKmMaster(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareKmMaster(entity1, entity2);
        const compareResult2 = service.compareKmMaster(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
