import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { ICourtCaseSetting } from '../court-case-setting.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../court-case-setting.test-samples';

import { CourtCaseSettingService, RestCourtCaseSetting } from './court-case-setting.service';

const requireRestSample: RestCourtCaseSetting = {
  ...sampleWithRequiredData,
  meetingDate: sampleWithRequiredData.meetingDate?.format(DATE_FORMAT),
};

describe('CourtCaseSetting Service', () => {
  let service: CourtCaseSettingService;
  let httpMock: HttpTestingController;
  let expectedResult: ICourtCaseSetting | ICourtCaseSetting[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CourtCaseSettingService);
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

    it('should create a CourtCaseSetting', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const courtCaseSetting = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(courtCaseSetting).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CourtCaseSetting', () => {
      const courtCaseSetting = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(courtCaseSetting).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CourtCaseSetting', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CourtCaseSetting', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CourtCaseSetting', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCourtCaseSettingToCollectionIfMissing', () => {
      it('should add a CourtCaseSetting to an empty array', () => {
        const courtCaseSetting: ICourtCaseSetting = sampleWithRequiredData;
        expectedResult = service.addCourtCaseSettingToCollectionIfMissing([], courtCaseSetting);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(courtCaseSetting);
      });

      it('should not add a CourtCaseSetting to an array that contains it', () => {
        const courtCaseSetting: ICourtCaseSetting = sampleWithRequiredData;
        const courtCaseSettingCollection: ICourtCaseSetting[] = [
          {
            ...courtCaseSetting,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCourtCaseSettingToCollectionIfMissing(courtCaseSettingCollection, courtCaseSetting);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CourtCaseSetting to an array that doesn't contain it", () => {
        const courtCaseSetting: ICourtCaseSetting = sampleWithRequiredData;
        const courtCaseSettingCollection: ICourtCaseSetting[] = [sampleWithPartialData];
        expectedResult = service.addCourtCaseSettingToCollectionIfMissing(courtCaseSettingCollection, courtCaseSetting);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(courtCaseSetting);
      });

      it('should add only unique CourtCaseSetting to an array', () => {
        const courtCaseSettingArray: ICourtCaseSetting[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const courtCaseSettingCollection: ICourtCaseSetting[] = [sampleWithRequiredData];
        expectedResult = service.addCourtCaseSettingToCollectionIfMissing(courtCaseSettingCollection, ...courtCaseSettingArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const courtCaseSetting: ICourtCaseSetting = sampleWithRequiredData;
        const courtCaseSetting2: ICourtCaseSetting = sampleWithPartialData;
        expectedResult = service.addCourtCaseSettingToCollectionIfMissing([], courtCaseSetting, courtCaseSetting2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(courtCaseSetting);
        expect(expectedResult).toContain(courtCaseSetting2);
      });

      it('should accept null and undefined values', () => {
        const courtCaseSetting: ICourtCaseSetting = sampleWithRequiredData;
        expectedResult = service.addCourtCaseSettingToCollectionIfMissing([], null, courtCaseSetting, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(courtCaseSetting);
      });

      it('should return initial array if no CourtCaseSetting is added', () => {
        const courtCaseSettingCollection: ICourtCaseSetting[] = [sampleWithRequiredData];
        expectedResult = service.addCourtCaseSettingToCollectionIfMissing(courtCaseSettingCollection, undefined, null);
        expect(expectedResult).toEqual(courtCaseSettingCollection);
      });
    });

    describe('compareCourtCaseSetting', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCourtCaseSetting(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCourtCaseSetting(entity1, entity2);
        const compareResult2 = service.compareCourtCaseSetting(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareCourtCaseSetting(entity1, entity2);
        const compareResult2 = service.compareCourtCaseSetting(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareCourtCaseSetting(entity1, entity2);
        const compareResult2 = service.compareCourtCaseSetting(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
