import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IVillageMaster } from '../village-master.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../village-master.test-samples';

import { VillageMasterService } from './village-master.service';

const requireRestSample: IVillageMaster = {
  ...sampleWithRequiredData,
};

describe('VillageMaster Service', () => {
  let service: VillageMasterService;
  let httpMock: HttpTestingController;
  let expectedResult: IVillageMaster | IVillageMaster[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(VillageMasterService);
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

    it('should create a VillageMaster', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const villageMaster = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(villageMaster).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a VillageMaster', () => {
      const villageMaster = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(villageMaster).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a VillageMaster', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of VillageMaster', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a VillageMaster', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addVillageMasterToCollectionIfMissing', () => {
      it('should add a VillageMaster to an empty array', () => {
        const villageMaster: IVillageMaster = sampleWithRequiredData;
        expectedResult = service.addVillageMasterToCollectionIfMissing([], villageMaster);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(villageMaster);
      });

      it('should not add a VillageMaster to an array that contains it', () => {
        const villageMaster: IVillageMaster = sampleWithRequiredData;
        const villageMasterCollection: IVillageMaster[] = [
          {
            ...villageMaster,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addVillageMasterToCollectionIfMissing(villageMasterCollection, villageMaster);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a VillageMaster to an array that doesn't contain it", () => {
        const villageMaster: IVillageMaster = sampleWithRequiredData;
        const villageMasterCollection: IVillageMaster[] = [sampleWithPartialData];
        expectedResult = service.addVillageMasterToCollectionIfMissing(villageMasterCollection, villageMaster);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(villageMaster);
      });

      it('should add only unique VillageMaster to an array', () => {
        const villageMasterArray: IVillageMaster[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const villageMasterCollection: IVillageMaster[] = [sampleWithRequiredData];
        expectedResult = service.addVillageMasterToCollectionIfMissing(villageMasterCollection, ...villageMasterArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const villageMaster: IVillageMaster = sampleWithRequiredData;
        const villageMaster2: IVillageMaster = sampleWithPartialData;
        expectedResult = service.addVillageMasterToCollectionIfMissing([], villageMaster, villageMaster2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(villageMaster);
        expect(expectedResult).toContain(villageMaster2);
      });

      it('should accept null and undefined values', () => {
        const villageMaster: IVillageMaster = sampleWithRequiredData;
        expectedResult = service.addVillageMasterToCollectionIfMissing([], null, villageMaster, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(villageMaster);
      });

      it('should return initial array if no VillageMaster is added', () => {
        const villageMasterCollection: IVillageMaster[] = [sampleWithRequiredData];
        expectedResult = service.addVillageMasterToCollectionIfMissing(villageMasterCollection, undefined, null);
        expect(expectedResult).toEqual(villageMasterCollection);
      });
    });

    describe('compareVillageMaster', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareVillageMaster(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareVillageMaster(entity1, entity2);
        const compareResult2 = service.compareVillageMaster(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareVillageMaster(entity1, entity2);
        const compareResult2 = service.compareVillageMaster(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareVillageMaster(entity1, entity2);
        const compareResult2 = service.compareVillageMaster(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
