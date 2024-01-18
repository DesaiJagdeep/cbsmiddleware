import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IFactoryMaster } from '../factory-master.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../factory-master.test-samples';

import { FactoryMasterService } from './factory-master.service';

const requireRestSample: IFactoryMaster = {
  ...sampleWithRequiredData,
};

describe('FactoryMaster Service', () => {
  let service: FactoryMasterService;
  let httpMock: HttpTestingController;
  let expectedResult: IFactoryMaster | IFactoryMaster[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(FactoryMasterService);
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

    it('should create a FactoryMaster', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const factoryMaster = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(factoryMaster).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a FactoryMaster', () => {
      const factoryMaster = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(factoryMaster).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a FactoryMaster', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of FactoryMaster', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a FactoryMaster', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addFactoryMasterToCollectionIfMissing', () => {
      it('should add a FactoryMaster to an empty array', () => {
        const factoryMaster: IFactoryMaster = sampleWithRequiredData;
        expectedResult = service.addFactoryMasterToCollectionIfMissing([], factoryMaster);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(factoryMaster);
      });

      it('should not add a FactoryMaster to an array that contains it', () => {
        const factoryMaster: IFactoryMaster = sampleWithRequiredData;
        const factoryMasterCollection: IFactoryMaster[] = [
          {
            ...factoryMaster,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addFactoryMasterToCollectionIfMissing(factoryMasterCollection, factoryMaster);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a FactoryMaster to an array that doesn't contain it", () => {
        const factoryMaster: IFactoryMaster = sampleWithRequiredData;
        const factoryMasterCollection: IFactoryMaster[] = [sampleWithPartialData];
        expectedResult = service.addFactoryMasterToCollectionIfMissing(factoryMasterCollection, factoryMaster);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(factoryMaster);
      });

      it('should add only unique FactoryMaster to an array', () => {
        const factoryMasterArray: IFactoryMaster[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const factoryMasterCollection: IFactoryMaster[] = [sampleWithRequiredData];
        expectedResult = service.addFactoryMasterToCollectionIfMissing(factoryMasterCollection, ...factoryMasterArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const factoryMaster: IFactoryMaster = sampleWithRequiredData;
        const factoryMaster2: IFactoryMaster = sampleWithPartialData;
        expectedResult = service.addFactoryMasterToCollectionIfMissing([], factoryMaster, factoryMaster2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(factoryMaster);
        expect(expectedResult).toContain(factoryMaster2);
      });

      it('should accept null and undefined values', () => {
        const factoryMaster: IFactoryMaster = sampleWithRequiredData;
        expectedResult = service.addFactoryMasterToCollectionIfMissing([], null, factoryMaster, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(factoryMaster);
      });

      it('should return initial array if no FactoryMaster is added', () => {
        const factoryMasterCollection: IFactoryMaster[] = [sampleWithRequiredData];
        expectedResult = service.addFactoryMasterToCollectionIfMissing(factoryMasterCollection, undefined, null);
        expect(expectedResult).toEqual(factoryMasterCollection);
      });
    });

    describe('compareFactoryMaster', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareFactoryMaster(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareFactoryMaster(entity1, entity2);
        const compareResult2 = service.compareFactoryMaster(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareFactoryMaster(entity1, entity2);
        const compareResult2 = service.compareFactoryMaster(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareFactoryMaster(entity1, entity2);
        const compareResult2 = service.compareFactoryMaster(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
