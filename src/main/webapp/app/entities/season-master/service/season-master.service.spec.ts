import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ISeasonMaster } from '../season-master.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../season-master.test-samples';

import { SeasonMasterService } from './season-master.service';

const requireRestSample: ISeasonMaster = {
  ...sampleWithRequiredData,
};

describe('SeasonMaster Service', () => {
  let service: SeasonMasterService;
  let httpMock: HttpTestingController;
  let expectedResult: ISeasonMaster | ISeasonMaster[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SeasonMasterService);
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

    it('should create a SeasonMaster', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const seasonMaster = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(seasonMaster).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SeasonMaster', () => {
      const seasonMaster = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(seasonMaster).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a SeasonMaster', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of SeasonMaster', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a SeasonMaster', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addSeasonMasterToCollectionIfMissing', () => {
      it('should add a SeasonMaster to an empty array', () => {
        const seasonMaster: ISeasonMaster = sampleWithRequiredData;
        expectedResult = service.addSeasonMasterToCollectionIfMissing([], seasonMaster);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(seasonMaster);
      });

      it('should not add a SeasonMaster to an array that contains it', () => {
        const seasonMaster: ISeasonMaster = sampleWithRequiredData;
        const seasonMasterCollection: ISeasonMaster[] = [
          {
            ...seasonMaster,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addSeasonMasterToCollectionIfMissing(seasonMasterCollection, seasonMaster);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SeasonMaster to an array that doesn't contain it", () => {
        const seasonMaster: ISeasonMaster = sampleWithRequiredData;
        const seasonMasterCollection: ISeasonMaster[] = [sampleWithPartialData];
        expectedResult = service.addSeasonMasterToCollectionIfMissing(seasonMasterCollection, seasonMaster);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(seasonMaster);
      });

      it('should add only unique SeasonMaster to an array', () => {
        const seasonMasterArray: ISeasonMaster[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const seasonMasterCollection: ISeasonMaster[] = [sampleWithRequiredData];
        expectedResult = service.addSeasonMasterToCollectionIfMissing(seasonMasterCollection, ...seasonMasterArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const seasonMaster: ISeasonMaster = sampleWithRequiredData;
        const seasonMaster2: ISeasonMaster = sampleWithPartialData;
        expectedResult = service.addSeasonMasterToCollectionIfMissing([], seasonMaster, seasonMaster2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(seasonMaster);
        expect(expectedResult).toContain(seasonMaster2);
      });

      it('should accept null and undefined values', () => {
        const seasonMaster: ISeasonMaster = sampleWithRequiredData;
        expectedResult = service.addSeasonMasterToCollectionIfMissing([], null, seasonMaster, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(seasonMaster);
      });

      it('should return initial array if no SeasonMaster is added', () => {
        const seasonMasterCollection: ISeasonMaster[] = [sampleWithRequiredData];
        expectedResult = service.addSeasonMasterToCollectionIfMissing(seasonMasterCollection, undefined, null);
        expect(expectedResult).toEqual(seasonMasterCollection);
      });
    });

    describe('compareSeasonMaster', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareSeasonMaster(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareSeasonMaster(entity1, entity2);
        const compareResult2 = service.compareSeasonMaster(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareSeasonMaster(entity1, entity2);
        const compareResult2 = service.compareSeasonMaster(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareSeasonMaster(entity1, entity2);
        const compareResult2 = service.compareSeasonMaster(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
