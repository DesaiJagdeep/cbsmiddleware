import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPacsMaster } from '../pacs-master.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../pacs-master.test-samples';

import { PacsMasterService } from './pacs-master.service';

const requireRestSample: IPacsMaster = {
  ...sampleWithRequiredData,
};

describe('PacsMaster Service', () => {
  let service: PacsMasterService;
  let httpMock: HttpTestingController;
  let expectedResult: IPacsMaster | IPacsMaster[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PacsMasterService);
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

    it('should create a PacsMaster', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const pacsMaster = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(pacsMaster).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PacsMaster', () => {
      const pacsMaster = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(pacsMaster).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PacsMaster', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PacsMaster', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a PacsMaster', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPacsMasterToCollectionIfMissing', () => {
      it('should add a PacsMaster to an empty array', () => {
        const pacsMaster: IPacsMaster = sampleWithRequiredData;
        expectedResult = service.addPacsMasterToCollectionIfMissing([], pacsMaster);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(pacsMaster);
      });

      it('should not add a PacsMaster to an array that contains it', () => {
        const pacsMaster: IPacsMaster = sampleWithRequiredData;
        const pacsMasterCollection: IPacsMaster[] = [
          {
            ...pacsMaster,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPacsMasterToCollectionIfMissing(pacsMasterCollection, pacsMaster);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PacsMaster to an array that doesn't contain it", () => {
        const pacsMaster: IPacsMaster = sampleWithRequiredData;
        const pacsMasterCollection: IPacsMaster[] = [sampleWithPartialData];
        expectedResult = service.addPacsMasterToCollectionIfMissing(pacsMasterCollection, pacsMaster);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(pacsMaster);
      });

      it('should add only unique PacsMaster to an array', () => {
        const pacsMasterArray: IPacsMaster[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const pacsMasterCollection: IPacsMaster[] = [sampleWithRequiredData];
        expectedResult = service.addPacsMasterToCollectionIfMissing(pacsMasterCollection, ...pacsMasterArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const pacsMaster: IPacsMaster = sampleWithRequiredData;
        const pacsMaster2: IPacsMaster = sampleWithPartialData;
        expectedResult = service.addPacsMasterToCollectionIfMissing([], pacsMaster, pacsMaster2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(pacsMaster);
        expect(expectedResult).toContain(pacsMaster2);
      });

      it('should accept null and undefined values', () => {
        const pacsMaster: IPacsMaster = sampleWithRequiredData;
        expectedResult = service.addPacsMasterToCollectionIfMissing([], null, pacsMaster, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(pacsMaster);
      });

      it('should return initial array if no PacsMaster is added', () => {
        const pacsMasterCollection: IPacsMaster[] = [sampleWithRequiredData];
        expectedResult = service.addPacsMasterToCollectionIfMissing(pacsMasterCollection, undefined, null);
        expect(expectedResult).toEqual(pacsMasterCollection);
      });
    });

    describe('comparePacsMaster', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePacsMaster(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.comparePacsMaster(entity1, entity2);
        const compareResult2 = service.comparePacsMaster(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.comparePacsMaster(entity1, entity2);
        const compareResult2 = service.comparePacsMaster(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.comparePacsMaster(entity1, entity2);
        const compareResult2 = service.comparePacsMaster(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
