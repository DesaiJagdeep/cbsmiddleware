import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IFarmerTypeMaster } from '../farmer-type-master.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../farmer-type-master.test-samples';

import { FarmerTypeMasterService } from './farmer-type-master.service';

const requireRestSample: IFarmerTypeMaster = {
  ...sampleWithRequiredData,
};

describe('FarmerTypeMaster Service', () => {
  let service: FarmerTypeMasterService;
  let httpMock: HttpTestingController;
  let expectedResult: IFarmerTypeMaster | IFarmerTypeMaster[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(FarmerTypeMasterService);
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

    it('should create a FarmerTypeMaster', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const farmerTypeMaster = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(farmerTypeMaster).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a FarmerTypeMaster', () => {
      const farmerTypeMaster = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(farmerTypeMaster).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a FarmerTypeMaster', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of FarmerTypeMaster', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a FarmerTypeMaster', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addFarmerTypeMasterToCollectionIfMissing', () => {
      it('should add a FarmerTypeMaster to an empty array', () => {
        const farmerTypeMaster: IFarmerTypeMaster = sampleWithRequiredData;
        expectedResult = service.addFarmerTypeMasterToCollectionIfMissing([], farmerTypeMaster);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(farmerTypeMaster);
      });

      it('should not add a FarmerTypeMaster to an array that contains it', () => {
        const farmerTypeMaster: IFarmerTypeMaster = sampleWithRequiredData;
        const farmerTypeMasterCollection: IFarmerTypeMaster[] = [
          {
            ...farmerTypeMaster,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addFarmerTypeMasterToCollectionIfMissing(farmerTypeMasterCollection, farmerTypeMaster);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a FarmerTypeMaster to an array that doesn't contain it", () => {
        const farmerTypeMaster: IFarmerTypeMaster = sampleWithRequiredData;
        const farmerTypeMasterCollection: IFarmerTypeMaster[] = [sampleWithPartialData];
        expectedResult = service.addFarmerTypeMasterToCollectionIfMissing(farmerTypeMasterCollection, farmerTypeMaster);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(farmerTypeMaster);
      });

      it('should add only unique FarmerTypeMaster to an array', () => {
        const farmerTypeMasterArray: IFarmerTypeMaster[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const farmerTypeMasterCollection: IFarmerTypeMaster[] = [sampleWithRequiredData];
        expectedResult = service.addFarmerTypeMasterToCollectionIfMissing(farmerTypeMasterCollection, ...farmerTypeMasterArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const farmerTypeMaster: IFarmerTypeMaster = sampleWithRequiredData;
        const farmerTypeMaster2: IFarmerTypeMaster = sampleWithPartialData;
        expectedResult = service.addFarmerTypeMasterToCollectionIfMissing([], farmerTypeMaster, farmerTypeMaster2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(farmerTypeMaster);
        expect(expectedResult).toContain(farmerTypeMaster2);
      });

      it('should accept null and undefined values', () => {
        const farmerTypeMaster: IFarmerTypeMaster = sampleWithRequiredData;
        expectedResult = service.addFarmerTypeMasterToCollectionIfMissing([], null, farmerTypeMaster, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(farmerTypeMaster);
      });

      it('should return initial array if no FarmerTypeMaster is added', () => {
        const farmerTypeMasterCollection: IFarmerTypeMaster[] = [sampleWithRequiredData];
        expectedResult = service.addFarmerTypeMasterToCollectionIfMissing(farmerTypeMasterCollection, undefined, null);
        expect(expectedResult).toEqual(farmerTypeMasterCollection);
      });
    });

    describe('compareFarmerTypeMaster', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareFarmerTypeMaster(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareFarmerTypeMaster(entity1, entity2);
        const compareResult2 = service.compareFarmerTypeMaster(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareFarmerTypeMaster(entity1, entity2);
        const compareResult2 = service.compareFarmerTypeMaster(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareFarmerTypeMaster(entity1, entity2);
        const compareResult2 = service.compareFarmerTypeMaster(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
