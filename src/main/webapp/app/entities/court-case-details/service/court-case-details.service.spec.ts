import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICourtCaseDetails } from '../court-case-details.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../court-case-details.test-samples';

import { CourtCaseDetailsService, RestCourtCaseDetails } from './court-case-details.service';

const requireRestSample: RestCourtCaseDetails = {
  ...sampleWithRequiredData,
  dinank: sampleWithRequiredData.dinank?.toJSON(),
  caseDinank: sampleWithRequiredData.caseDinank?.toJSON(),
  certificateDinnank: sampleWithRequiredData.certificateDinnank?.toJSON(),
  dimmandDinnank: sampleWithRequiredData.dimmandDinnank?.toJSON(),
  japtiAadheshDinnank: sampleWithRequiredData.japtiAadheshDinnank?.toJSON(),
  vikriAddheshDinnank: sampleWithRequiredData.vikriAddheshDinnank?.toJSON(),
};

describe('CourtCaseDetails Service', () => {
  let service: CourtCaseDetailsService;
  let httpMock: HttpTestingController;
  let expectedResult: ICourtCaseDetails | ICourtCaseDetails[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CourtCaseDetailsService);
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

    it('should create a CourtCaseDetails', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const courtCaseDetails = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(courtCaseDetails).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CourtCaseDetails', () => {
      const courtCaseDetails = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(courtCaseDetails).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CourtCaseDetails', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CourtCaseDetails', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CourtCaseDetails', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCourtCaseDetailsToCollectionIfMissing', () => {
      it('should add a CourtCaseDetails to an empty array', () => {
        const courtCaseDetails: ICourtCaseDetails = sampleWithRequiredData;
        expectedResult = service.addCourtCaseDetailsToCollectionIfMissing([], courtCaseDetails);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(courtCaseDetails);
      });

      it('should not add a CourtCaseDetails to an array that contains it', () => {
        const courtCaseDetails: ICourtCaseDetails = sampleWithRequiredData;
        const courtCaseDetailsCollection: ICourtCaseDetails[] = [
          {
            ...courtCaseDetails,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCourtCaseDetailsToCollectionIfMissing(courtCaseDetailsCollection, courtCaseDetails);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CourtCaseDetails to an array that doesn't contain it", () => {
        const courtCaseDetails: ICourtCaseDetails = sampleWithRequiredData;
        const courtCaseDetailsCollection: ICourtCaseDetails[] = [sampleWithPartialData];
        expectedResult = service.addCourtCaseDetailsToCollectionIfMissing(courtCaseDetailsCollection, courtCaseDetails);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(courtCaseDetails);
      });

      it('should add only unique CourtCaseDetails to an array', () => {
        const courtCaseDetailsArray: ICourtCaseDetails[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const courtCaseDetailsCollection: ICourtCaseDetails[] = [sampleWithRequiredData];
        expectedResult = service.addCourtCaseDetailsToCollectionIfMissing(courtCaseDetailsCollection, ...courtCaseDetailsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const courtCaseDetails: ICourtCaseDetails = sampleWithRequiredData;
        const courtCaseDetails2: ICourtCaseDetails = sampleWithPartialData;
        expectedResult = service.addCourtCaseDetailsToCollectionIfMissing([], courtCaseDetails, courtCaseDetails2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(courtCaseDetails);
        expect(expectedResult).toContain(courtCaseDetails2);
      });

      it('should accept null and undefined values', () => {
        const courtCaseDetails: ICourtCaseDetails = sampleWithRequiredData;
        expectedResult = service.addCourtCaseDetailsToCollectionIfMissing([], null, courtCaseDetails, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(courtCaseDetails);
      });

      it('should return initial array if no CourtCaseDetails is added', () => {
        const courtCaseDetailsCollection: ICourtCaseDetails[] = [sampleWithRequiredData];
        expectedResult = service.addCourtCaseDetailsToCollectionIfMissing(courtCaseDetailsCollection, undefined, null);
        expect(expectedResult).toEqual(courtCaseDetailsCollection);
      });
    });

    describe('compareCourtCaseDetails', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCourtCaseDetails(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCourtCaseDetails(entity1, entity2);
        const compareResult2 = service.compareCourtCaseDetails(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareCourtCaseDetails(entity1, entity2);
        const compareResult2 = service.compareCourtCaseDetails(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareCourtCaseDetails(entity1, entity2);
        const compareResult2 = service.compareCourtCaseDetails(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
