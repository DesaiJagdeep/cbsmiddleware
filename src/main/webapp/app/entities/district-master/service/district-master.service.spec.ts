import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IDistrictMaster } from '../district-master.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../district-master.test-samples';

import { DistrictMasterService } from './district-master.service';

const requireRestSample: IDistrictMaster = {
  ...sampleWithRequiredData,
};

describe('DistrictMaster Service', () => {
  let service: DistrictMasterService;
  let httpMock: HttpTestingController;
  let expectedResult: IDistrictMaster | IDistrictMaster[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DistrictMasterService);
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

    it('should create a DistrictMaster', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const districtMaster = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(districtMaster).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a DistrictMaster', () => {
      const districtMaster = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(districtMaster).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a DistrictMaster', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of DistrictMaster', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a DistrictMaster', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addDistrictMasterToCollectionIfMissing', () => {
      it('should add a DistrictMaster to an empty array', () => {
        const districtMaster: IDistrictMaster = sampleWithRequiredData;
        expectedResult = service.addDistrictMasterToCollectionIfMissing([], districtMaster);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(districtMaster);
      });

      it('should not add a DistrictMaster to an array that contains it', () => {
        const districtMaster: IDistrictMaster = sampleWithRequiredData;
        const districtMasterCollection: IDistrictMaster[] = [
          {
            ...districtMaster,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addDistrictMasterToCollectionIfMissing(districtMasterCollection, districtMaster);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a DistrictMaster to an array that doesn't contain it", () => {
        const districtMaster: IDistrictMaster = sampleWithRequiredData;
        const districtMasterCollection: IDistrictMaster[] = [sampleWithPartialData];
        expectedResult = service.addDistrictMasterToCollectionIfMissing(districtMasterCollection, districtMaster);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(districtMaster);
      });

      it('should add only unique DistrictMaster to an array', () => {
        const districtMasterArray: IDistrictMaster[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const districtMasterCollection: IDistrictMaster[] = [sampleWithRequiredData];
        expectedResult = service.addDistrictMasterToCollectionIfMissing(districtMasterCollection, ...districtMasterArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const districtMaster: IDistrictMaster = sampleWithRequiredData;
        const districtMaster2: IDistrictMaster = sampleWithPartialData;
        expectedResult = service.addDistrictMasterToCollectionIfMissing([], districtMaster, districtMaster2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(districtMaster);
        expect(expectedResult).toContain(districtMaster2);
      });

      it('should accept null and undefined values', () => {
        const districtMaster: IDistrictMaster = sampleWithRequiredData;
        expectedResult = service.addDistrictMasterToCollectionIfMissing([], null, districtMaster, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(districtMaster);
      });

      it('should return initial array if no DistrictMaster is added', () => {
        const districtMasterCollection: IDistrictMaster[] = [sampleWithRequiredData];
        expectedResult = service.addDistrictMasterToCollectionIfMissing(districtMasterCollection, undefined, null);
        expect(expectedResult).toEqual(districtMasterCollection);
      });
    });

    describe('compareDistrictMaster', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareDistrictMaster(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareDistrictMaster(entity1, entity2);
        const compareResult2 = service.compareDistrictMaster(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareDistrictMaster(entity1, entity2);
        const compareResult2 = service.compareDistrictMaster(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareDistrictMaster(entity1, entity2);
        const compareResult2 = service.compareDistrictMaster(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
