import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IAccountHolderMaster } from '../account-holder-master.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../account-holder-master.test-samples';

import { AccountHolderMasterService } from './account-holder-master.service';

const requireRestSample: IAccountHolderMaster = {
  ...sampleWithRequiredData,
};

describe('AccountHolderMaster Service', () => {
  let service: AccountHolderMasterService;
  let httpMock: HttpTestingController;
  let expectedResult: IAccountHolderMaster | IAccountHolderMaster[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AccountHolderMasterService);
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

    it('should create a AccountHolderMaster', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const accountHolderMaster = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(accountHolderMaster).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AccountHolderMaster', () => {
      const accountHolderMaster = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(accountHolderMaster).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AccountHolderMaster', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AccountHolderMaster', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a AccountHolderMaster', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAccountHolderMasterToCollectionIfMissing', () => {
      it('should add a AccountHolderMaster to an empty array', () => {
        const accountHolderMaster: IAccountHolderMaster = sampleWithRequiredData;
        expectedResult = service.addAccountHolderMasterToCollectionIfMissing([], accountHolderMaster);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(accountHolderMaster);
      });

      it('should not add a AccountHolderMaster to an array that contains it', () => {
        const accountHolderMaster: IAccountHolderMaster = sampleWithRequiredData;
        const accountHolderMasterCollection: IAccountHolderMaster[] = [
          {
            ...accountHolderMaster,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAccountHolderMasterToCollectionIfMissing(accountHolderMasterCollection, accountHolderMaster);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AccountHolderMaster to an array that doesn't contain it", () => {
        const accountHolderMaster: IAccountHolderMaster = sampleWithRequiredData;
        const accountHolderMasterCollection: IAccountHolderMaster[] = [sampleWithPartialData];
        expectedResult = service.addAccountHolderMasterToCollectionIfMissing(accountHolderMasterCollection, accountHolderMaster);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(accountHolderMaster);
      });

      it('should add only unique AccountHolderMaster to an array', () => {
        const accountHolderMasterArray: IAccountHolderMaster[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const accountHolderMasterCollection: IAccountHolderMaster[] = [sampleWithRequiredData];
        expectedResult = service.addAccountHolderMasterToCollectionIfMissing(accountHolderMasterCollection, ...accountHolderMasterArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const accountHolderMaster: IAccountHolderMaster = sampleWithRequiredData;
        const accountHolderMaster2: IAccountHolderMaster = sampleWithPartialData;
        expectedResult = service.addAccountHolderMasterToCollectionIfMissing([], accountHolderMaster, accountHolderMaster2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(accountHolderMaster);
        expect(expectedResult).toContain(accountHolderMaster2);
      });

      it('should accept null and undefined values', () => {
        const accountHolderMaster: IAccountHolderMaster = sampleWithRequiredData;
        expectedResult = service.addAccountHolderMasterToCollectionIfMissing([], null, accountHolderMaster, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(accountHolderMaster);
      });

      it('should return initial array if no AccountHolderMaster is added', () => {
        const accountHolderMasterCollection: IAccountHolderMaster[] = [sampleWithRequiredData];
        expectedResult = service.addAccountHolderMasterToCollectionIfMissing(accountHolderMasterCollection, undefined, null);
        expect(expectedResult).toEqual(accountHolderMasterCollection);
      });
    });

    describe('compareAccountHolderMaster', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAccountHolderMaster(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareAccountHolderMaster(entity1, entity2);
        const compareResult2 = service.compareAccountHolderMaster(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareAccountHolderMaster(entity1, entity2);
        const compareResult2 = service.compareAccountHolderMaster(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareAccountHolderMaster(entity1, entity2);
        const compareResult2 = service.compareAccountHolderMaster(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
