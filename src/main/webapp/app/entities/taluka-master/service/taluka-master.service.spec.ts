import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ITalukaMaster } from '../taluka-master.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../taluka-master.test-samples';

import { TalukaMasterService } from './taluka-master.service';

const requireRestSample: ITalukaMaster = {
  ...sampleWithRequiredData,
};

describe('TalukaMaster Service', () => {
  let service: TalukaMasterService;
  let httpMock: HttpTestingController;
  let expectedResult: ITalukaMaster | ITalukaMaster[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TalukaMasterService);
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

    it('should create a TalukaMaster', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const talukaMaster = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(talukaMaster).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TalukaMaster', () => {
      const talukaMaster = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(talukaMaster).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a TalukaMaster', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TalukaMaster', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a TalukaMaster', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addTalukaMasterToCollectionIfMissing', () => {
      it('should add a TalukaMaster to an empty array', () => {
        const talukaMaster: ITalukaMaster = sampleWithRequiredData;
        expectedResult = service.addTalukaMasterToCollectionIfMissing([], talukaMaster);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(talukaMaster);
      });

      it('should not add a TalukaMaster to an array that contains it', () => {
        const talukaMaster: ITalukaMaster = sampleWithRequiredData;
        const talukaMasterCollection: ITalukaMaster[] = [
          {
            ...talukaMaster,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addTalukaMasterToCollectionIfMissing(talukaMasterCollection, talukaMaster);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TalukaMaster to an array that doesn't contain it", () => {
        const talukaMaster: ITalukaMaster = sampleWithRequiredData;
        const talukaMasterCollection: ITalukaMaster[] = [sampleWithPartialData];
        expectedResult = service.addTalukaMasterToCollectionIfMissing(talukaMasterCollection, talukaMaster);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(talukaMaster);
      });

      it('should add only unique TalukaMaster to an array', () => {
        const talukaMasterArray: ITalukaMaster[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const talukaMasterCollection: ITalukaMaster[] = [sampleWithRequiredData];
        expectedResult = service.addTalukaMasterToCollectionIfMissing(talukaMasterCollection, ...talukaMasterArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const talukaMaster: ITalukaMaster = sampleWithRequiredData;
        const talukaMaster2: ITalukaMaster = sampleWithPartialData;
        expectedResult = service.addTalukaMasterToCollectionIfMissing([], talukaMaster, talukaMaster2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(talukaMaster);
        expect(expectedResult).toContain(talukaMaster2);
      });

      it('should accept null and undefined values', () => {
        const talukaMaster: ITalukaMaster = sampleWithRequiredData;
        expectedResult = service.addTalukaMasterToCollectionIfMissing([], null, talukaMaster, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(talukaMaster);
      });

      it('should return initial array if no TalukaMaster is added', () => {
        const talukaMasterCollection: ITalukaMaster[] = [sampleWithRequiredData];
        expectedResult = service.addTalukaMasterToCollectionIfMissing(talukaMasterCollection, undefined, null);
        expect(expectedResult).toEqual(talukaMasterCollection);
      });
    });

    describe('compareTalukaMaster', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareTalukaMaster(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareTalukaMaster(entity1, entity2);
        const compareResult2 = service.compareTalukaMaster(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareTalukaMaster(entity1, entity2);
        const compareResult2 = service.compareTalukaMaster(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareTalukaMaster(entity1, entity2);
        const compareResult2 = service.compareTalukaMaster(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
