import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IDesignationMaster } from '../designation-master.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../designation-master.test-samples';

import { DesignationMasterService } from './designation-master.service';

const requireRestSample: IDesignationMaster = {
  ...sampleWithRequiredData,
};

describe('DesignationMaster Service', () => {
  let service: DesignationMasterService;
  let httpMock: HttpTestingController;
  let expectedResult: IDesignationMaster | IDesignationMaster[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DesignationMasterService);
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

    it('should create a DesignationMaster', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const designationMaster = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(designationMaster).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a DesignationMaster', () => {
      const designationMaster = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(designationMaster).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a DesignationMaster', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of DesignationMaster', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a DesignationMaster', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addDesignationMasterToCollectionIfMissing', () => {
      it('should add a DesignationMaster to an empty array', () => {
        const designationMaster: IDesignationMaster = sampleWithRequiredData;
        expectedResult = service.addDesignationMasterToCollectionIfMissing([], designationMaster);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(designationMaster);
      });

      it('should not add a DesignationMaster to an array that contains it', () => {
        const designationMaster: IDesignationMaster = sampleWithRequiredData;
        const designationMasterCollection: IDesignationMaster[] = [
          {
            ...designationMaster,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addDesignationMasterToCollectionIfMissing(designationMasterCollection, designationMaster);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a DesignationMaster to an array that doesn't contain it", () => {
        const designationMaster: IDesignationMaster = sampleWithRequiredData;
        const designationMasterCollection: IDesignationMaster[] = [sampleWithPartialData];
        expectedResult = service.addDesignationMasterToCollectionIfMissing(designationMasterCollection, designationMaster);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(designationMaster);
      });

      it('should add only unique DesignationMaster to an array', () => {
        const designationMasterArray: IDesignationMaster[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const designationMasterCollection: IDesignationMaster[] = [sampleWithRequiredData];
        expectedResult = service.addDesignationMasterToCollectionIfMissing(designationMasterCollection, ...designationMasterArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const designationMaster: IDesignationMaster = sampleWithRequiredData;
        const designationMaster2: IDesignationMaster = sampleWithPartialData;
        expectedResult = service.addDesignationMasterToCollectionIfMissing([], designationMaster, designationMaster2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(designationMaster);
        expect(expectedResult).toContain(designationMaster2);
      });

      it('should accept null and undefined values', () => {
        const designationMaster: IDesignationMaster = sampleWithRequiredData;
        expectedResult = service.addDesignationMasterToCollectionIfMissing([], null, designationMaster, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(designationMaster);
      });

      it('should return initial array if no DesignationMaster is added', () => {
        const designationMasterCollection: IDesignationMaster[] = [sampleWithRequiredData];
        expectedResult = service.addDesignationMasterToCollectionIfMissing(designationMasterCollection, undefined, null);
        expect(expectedResult).toEqual(designationMasterCollection);
      });
    });

    describe('compareDesignationMaster', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareDesignationMaster(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareDesignationMaster(entity1, entity2);
        const compareResult2 = service.compareDesignationMaster(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareDesignationMaster(entity1, entity2);
        const compareResult2 = service.compareDesignationMaster(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareDesignationMaster(entity1, entity2);
        const compareResult2 = service.compareDesignationMaster(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
