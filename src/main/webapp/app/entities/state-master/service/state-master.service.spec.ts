import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IStateMaster } from '../state-master.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../state-master.test-samples';

import { StateMasterService } from './state-master.service';

const requireRestSample: IStateMaster = {
  ...sampleWithRequiredData,
};

describe('StateMaster Service', () => {
  let service: StateMasterService;
  let httpMock: HttpTestingController;
  let expectedResult: IStateMaster | IStateMaster[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(StateMasterService);
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

    it('should create a StateMaster', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const stateMaster = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(stateMaster).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a StateMaster', () => {
      const stateMaster = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(stateMaster).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a StateMaster', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of StateMaster', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a StateMaster', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addStateMasterToCollectionIfMissing', () => {
      it('should add a StateMaster to an empty array', () => {
        const stateMaster: IStateMaster = sampleWithRequiredData;
        expectedResult = service.addStateMasterToCollectionIfMissing([], stateMaster);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(stateMaster);
      });

      it('should not add a StateMaster to an array that contains it', () => {
        const stateMaster: IStateMaster = sampleWithRequiredData;
        const stateMasterCollection: IStateMaster[] = [
          {
            ...stateMaster,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addStateMasterToCollectionIfMissing(stateMasterCollection, stateMaster);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a StateMaster to an array that doesn't contain it", () => {
        const stateMaster: IStateMaster = sampleWithRequiredData;
        const stateMasterCollection: IStateMaster[] = [sampleWithPartialData];
        expectedResult = service.addStateMasterToCollectionIfMissing(stateMasterCollection, stateMaster);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(stateMaster);
      });

      it('should add only unique StateMaster to an array', () => {
        const stateMasterArray: IStateMaster[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const stateMasterCollection: IStateMaster[] = [sampleWithRequiredData];
        expectedResult = service.addStateMasterToCollectionIfMissing(stateMasterCollection, ...stateMasterArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const stateMaster: IStateMaster = sampleWithRequiredData;
        const stateMaster2: IStateMaster = sampleWithPartialData;
        expectedResult = service.addStateMasterToCollectionIfMissing([], stateMaster, stateMaster2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(stateMaster);
        expect(expectedResult).toContain(stateMaster2);
      });

      it('should accept null and undefined values', () => {
        const stateMaster: IStateMaster = sampleWithRequiredData;
        expectedResult = service.addStateMasterToCollectionIfMissing([], null, stateMaster, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(stateMaster);
      });

      it('should return initial array if no StateMaster is added', () => {
        const stateMasterCollection: IStateMaster[] = [sampleWithRequiredData];
        expectedResult = service.addStateMasterToCollectionIfMissing(stateMasterCollection, undefined, null);
        expect(expectedResult).toEqual(stateMasterCollection);
      });
    });

    describe('compareStateMaster', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareStateMaster(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareStateMaster(entity1, entity2);
        const compareResult2 = service.compareStateMaster(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareStateMaster(entity1, entity2);
        const compareResult2 = service.compareStateMaster(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareStateMaster(entity1, entity2);
        const compareResult2 = service.compareStateMaster(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
