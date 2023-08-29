import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICourtCase } from '../court-case.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../court-case.test-samples';

import { CourtCaseService, RestCourtCase } from './court-case.service';

const requireRestSample: RestCourtCase = {
  ...sampleWithRequiredData,
  caseDinank: sampleWithRequiredData.caseDinank?.toJSON(),
  thakDinnank: sampleWithRequiredData.thakDinnank?.toJSON(),
  karjDinnank: sampleWithRequiredData.karjDinnank?.toJSON(),
  mudatSampteDinank: sampleWithRequiredData.mudatSampteDinank?.toJSON(),
  tharavDinank: sampleWithRequiredData.tharavDinank?.toJSON(),
  noticeOne: sampleWithRequiredData.noticeOne?.toJSON(),
  noticeTwo: sampleWithRequiredData.noticeTwo?.toJSON(),
};

describe('CourtCase Service', () => {
  let service: CourtCaseService;
  let httpMock: HttpTestingController;
  let expectedResult: ICourtCase | ICourtCase[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CourtCaseService);
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

    it('should create a CourtCase', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const courtCase = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(courtCase).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CourtCase', () => {
      const courtCase = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(courtCase).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CourtCase', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CourtCase', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CourtCase', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCourtCaseToCollectionIfMissing', () => {
      it('should add a CourtCase to an empty array', () => {
        const courtCase: ICourtCase = sampleWithRequiredData;
        expectedResult = service.addCourtCaseToCollectionIfMissing([], courtCase);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(courtCase);
      });

      it('should not add a CourtCase to an array that contains it', () => {
        const courtCase: ICourtCase = sampleWithRequiredData;
        const courtCaseCollection: ICourtCase[] = [
          {
            ...courtCase,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCourtCaseToCollectionIfMissing(courtCaseCollection, courtCase);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CourtCase to an array that doesn't contain it", () => {
        const courtCase: ICourtCase = sampleWithRequiredData;
        const courtCaseCollection: ICourtCase[] = [sampleWithPartialData];
        expectedResult = service.addCourtCaseToCollectionIfMissing(courtCaseCollection, courtCase);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(courtCase);
      });

      it('should add only unique CourtCase to an array', () => {
        const courtCaseArray: ICourtCase[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const courtCaseCollection: ICourtCase[] = [sampleWithRequiredData];
        expectedResult = service.addCourtCaseToCollectionIfMissing(courtCaseCollection, ...courtCaseArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const courtCase: ICourtCase = sampleWithRequiredData;
        const courtCase2: ICourtCase = sampleWithPartialData;
        expectedResult = service.addCourtCaseToCollectionIfMissing([], courtCase, courtCase2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(courtCase);
        expect(expectedResult).toContain(courtCase2);
      });

      it('should accept null and undefined values', () => {
        const courtCase: ICourtCase = sampleWithRequiredData;
        expectedResult = service.addCourtCaseToCollectionIfMissing([], null, courtCase, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(courtCase);
      });

      it('should return initial array if no CourtCase is added', () => {
        const courtCaseCollection: ICourtCase[] = [sampleWithRequiredData];
        expectedResult = service.addCourtCaseToCollectionIfMissing(courtCaseCollection, undefined, null);
        expect(expectedResult).toEqual(courtCaseCollection);
      });
    });

    describe('compareCourtCase', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCourtCase(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCourtCase(entity1, entity2);
        const compareResult2 = service.compareCourtCase(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareCourtCase(entity1, entity2);
        const compareResult2 = service.compareCourtCase(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareCourtCase(entity1, entity2);
        const compareResult2 = service.compareCourtCase(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
