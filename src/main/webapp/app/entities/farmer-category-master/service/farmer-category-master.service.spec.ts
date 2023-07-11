import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IFarmerCategoryMaster } from '../farmer-category-master.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../farmer-category-master.test-samples';

import { FarmerCategoryMasterService } from './farmer-category-master.service';

const requireRestSample: IFarmerCategoryMaster = {
  ...sampleWithRequiredData,
};

describe('FarmerCategoryMaster Service', () => {
  let service: FarmerCategoryMasterService;
  let httpMock: HttpTestingController;
  let expectedResult: IFarmerCategoryMaster | IFarmerCategoryMaster[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(FarmerCategoryMasterService);
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

    it('should create a FarmerCategoryMaster', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const farmerCategoryMaster = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(farmerCategoryMaster).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a FarmerCategoryMaster', () => {
      const farmerCategoryMaster = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(farmerCategoryMaster).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a FarmerCategoryMaster', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of FarmerCategoryMaster', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a FarmerCategoryMaster', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addFarmerCategoryMasterToCollectionIfMissing', () => {
      it('should add a FarmerCategoryMaster to an empty array', () => {
        const farmerCategoryMaster: IFarmerCategoryMaster = sampleWithRequiredData;
        expectedResult = service.addFarmerCategoryMasterToCollectionIfMissing([], farmerCategoryMaster);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(farmerCategoryMaster);
      });

      it('should not add a FarmerCategoryMaster to an array that contains it', () => {
        const farmerCategoryMaster: IFarmerCategoryMaster = sampleWithRequiredData;
        const farmerCategoryMasterCollection: IFarmerCategoryMaster[] = [
          {
            ...farmerCategoryMaster,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addFarmerCategoryMasterToCollectionIfMissing(farmerCategoryMasterCollection, farmerCategoryMaster);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a FarmerCategoryMaster to an array that doesn't contain it", () => {
        const farmerCategoryMaster: IFarmerCategoryMaster = sampleWithRequiredData;
        const farmerCategoryMasterCollection: IFarmerCategoryMaster[] = [sampleWithPartialData];
        expectedResult = service.addFarmerCategoryMasterToCollectionIfMissing(farmerCategoryMasterCollection, farmerCategoryMaster);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(farmerCategoryMaster);
      });

      it('should add only unique FarmerCategoryMaster to an array', () => {
        const farmerCategoryMasterArray: IFarmerCategoryMaster[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const farmerCategoryMasterCollection: IFarmerCategoryMaster[] = [sampleWithRequiredData];
        expectedResult = service.addFarmerCategoryMasterToCollectionIfMissing(farmerCategoryMasterCollection, ...farmerCategoryMasterArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const farmerCategoryMaster: IFarmerCategoryMaster = sampleWithRequiredData;
        const farmerCategoryMaster2: IFarmerCategoryMaster = sampleWithPartialData;
        expectedResult = service.addFarmerCategoryMasterToCollectionIfMissing([], farmerCategoryMaster, farmerCategoryMaster2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(farmerCategoryMaster);
        expect(expectedResult).toContain(farmerCategoryMaster2);
      });

      it('should accept null and undefined values', () => {
        const farmerCategoryMaster: IFarmerCategoryMaster = sampleWithRequiredData;
        expectedResult = service.addFarmerCategoryMasterToCollectionIfMissing([], null, farmerCategoryMaster, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(farmerCategoryMaster);
      });

      it('should return initial array if no FarmerCategoryMaster is added', () => {
        const farmerCategoryMasterCollection: IFarmerCategoryMaster[] = [sampleWithRequiredData];
        expectedResult = service.addFarmerCategoryMasterToCollectionIfMissing(farmerCategoryMasterCollection, undefined, null);
        expect(expectedResult).toEqual(farmerCategoryMasterCollection);
      });
    });

    describe('compareFarmerCategoryMaster', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareFarmerCategoryMaster(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareFarmerCategoryMaster(entity1, entity2);
        const compareResult2 = service.compareFarmerCategoryMaster(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareFarmerCategoryMaster(entity1, entity2);
        const compareResult2 = service.compareFarmerCategoryMaster(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareFarmerCategoryMaster(entity1, entity2);
        const compareResult2 = service.compareFarmerCategoryMaster(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
