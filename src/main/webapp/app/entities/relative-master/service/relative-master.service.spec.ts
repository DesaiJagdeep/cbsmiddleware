import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IRelativeMaster } from '../relative-master.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../relative-master.test-samples';

import { RelativeMasterService } from './relative-master.service';

const requireRestSample: IRelativeMaster = {
  ...sampleWithRequiredData,
};

describe('RelativeMaster Service', () => {
  let service: RelativeMasterService;
  let httpMock: HttpTestingController;
  let expectedResult: IRelativeMaster | IRelativeMaster[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(RelativeMasterService);
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

    it('should create a RelativeMaster', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const relativeMaster = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(relativeMaster).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a RelativeMaster', () => {
      const relativeMaster = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(relativeMaster).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a RelativeMaster', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of RelativeMaster', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a RelativeMaster', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addRelativeMasterToCollectionIfMissing', () => {
      it('should add a RelativeMaster to an empty array', () => {
        const relativeMaster: IRelativeMaster = sampleWithRequiredData;
        expectedResult = service.addRelativeMasterToCollectionIfMissing([], relativeMaster);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(relativeMaster);
      });

      it('should not add a RelativeMaster to an array that contains it', () => {
        const relativeMaster: IRelativeMaster = sampleWithRequiredData;
        const relativeMasterCollection: IRelativeMaster[] = [
          {
            ...relativeMaster,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addRelativeMasterToCollectionIfMissing(relativeMasterCollection, relativeMaster);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a RelativeMaster to an array that doesn't contain it", () => {
        const relativeMaster: IRelativeMaster = sampleWithRequiredData;
        const relativeMasterCollection: IRelativeMaster[] = [sampleWithPartialData];
        expectedResult = service.addRelativeMasterToCollectionIfMissing(relativeMasterCollection, relativeMaster);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(relativeMaster);
      });

      it('should add only unique RelativeMaster to an array', () => {
        const relativeMasterArray: IRelativeMaster[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const relativeMasterCollection: IRelativeMaster[] = [sampleWithRequiredData];
        expectedResult = service.addRelativeMasterToCollectionIfMissing(relativeMasterCollection, ...relativeMasterArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const relativeMaster: IRelativeMaster = sampleWithRequiredData;
        const relativeMaster2: IRelativeMaster = sampleWithPartialData;
        expectedResult = service.addRelativeMasterToCollectionIfMissing([], relativeMaster, relativeMaster2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(relativeMaster);
        expect(expectedResult).toContain(relativeMaster2);
      });

      it('should accept null and undefined values', () => {
        const relativeMaster: IRelativeMaster = sampleWithRequiredData;
        expectedResult = service.addRelativeMasterToCollectionIfMissing([], null, relativeMaster, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(relativeMaster);
      });

      it('should return initial array if no RelativeMaster is added', () => {
        const relativeMasterCollection: IRelativeMaster[] = [sampleWithRequiredData];
        expectedResult = service.addRelativeMasterToCollectionIfMissing(relativeMasterCollection, undefined, null);
        expect(expectedResult).toEqual(relativeMasterCollection);
      });
    });

    describe('compareRelativeMaster', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareRelativeMaster(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareRelativeMaster(entity1, entity2);
        const compareResult2 = service.compareRelativeMaster(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareRelativeMaster(entity1, entity2);
        const compareResult2 = service.compareRelativeMaster(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareRelativeMaster(entity1, entity2);
        const compareResult2 = service.compareRelativeMaster(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
