import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICbsDataReport } from '../cbs-data-report.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../cbs-data-report.test-samples';

import { CbsDataReportService } from './cbs-data-report.service';

const requireRestSample: ICbsDataReport = {
  ...sampleWithRequiredData,
};

describe('CbsDataReport Service', () => {
  let service: CbsDataReportService;
  let httpMock: HttpTestingController;
  let expectedResult: ICbsDataReport | ICbsDataReport[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CbsDataReportService);
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

    it('should create a CbsDataReport', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const cbsDataReport = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(cbsDataReport).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CbsDataReport', () => {
      const cbsDataReport = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(cbsDataReport).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CbsDataReport', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CbsDataReport', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CbsDataReport', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCbsDataReportToCollectionIfMissing', () => {
      it('should add a CbsDataReport to an empty array', () => {
        const cbsDataReport: ICbsDataReport = sampleWithRequiredData;
        expectedResult = service.addCbsDataReportToCollectionIfMissing([], cbsDataReport);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(cbsDataReport);
      });

      it('should not add a CbsDataReport to an array that contains it', () => {
        const cbsDataReport: ICbsDataReport = sampleWithRequiredData;
        const cbsDataReportCollection: ICbsDataReport[] = [
          {
            ...cbsDataReport,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCbsDataReportToCollectionIfMissing(cbsDataReportCollection, cbsDataReport);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CbsDataReport to an array that doesn't contain it", () => {
        const cbsDataReport: ICbsDataReport = sampleWithRequiredData;
        const cbsDataReportCollection: ICbsDataReport[] = [sampleWithPartialData];
        expectedResult = service.addCbsDataReportToCollectionIfMissing(cbsDataReportCollection, cbsDataReport);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(cbsDataReport);
      });

      it('should add only unique CbsDataReport to an array', () => {
        const cbsDataReportArray: ICbsDataReport[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const cbsDataReportCollection: ICbsDataReport[] = [sampleWithRequiredData];
        expectedResult = service.addCbsDataReportToCollectionIfMissing(cbsDataReportCollection, ...cbsDataReportArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const cbsDataReport: ICbsDataReport = sampleWithRequiredData;
        const cbsDataReport2: ICbsDataReport = sampleWithPartialData;
        expectedResult = service.addCbsDataReportToCollectionIfMissing([], cbsDataReport, cbsDataReport2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(cbsDataReport);
        expect(expectedResult).toContain(cbsDataReport2);
      });

      it('should accept null and undefined values', () => {
        const cbsDataReport: ICbsDataReport = sampleWithRequiredData;
        expectedResult = service.addCbsDataReportToCollectionIfMissing([], null, cbsDataReport, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(cbsDataReport);
      });

      it('should return initial array if no CbsDataReport is added', () => {
        const cbsDataReportCollection: ICbsDataReport[] = [sampleWithRequiredData];
        expectedResult = service.addCbsDataReportToCollectionIfMissing(cbsDataReportCollection, undefined, null);
        expect(expectedResult).toEqual(cbsDataReportCollection);
      });
    });

    describe('compareCbsDataReport', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCbsDataReport(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCbsDataReport(entity1, entity2);
        const compareResult2 = service.compareCbsDataReport(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareCbsDataReport(entity1, entity2);
        const compareResult2 = service.compareCbsDataReport(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareCbsDataReport(entity1, entity2);
        const compareResult2 = service.compareCbsDataReport(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
