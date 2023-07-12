import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IApplicationLog } from '../application-log.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../application-log.test-samples';

import { ApplicationLogService } from './application-log.service';

const requireRestSample: IApplicationLog = {
  ...sampleWithRequiredData,
};

describe('ApplicationLog Service', () => {
  let service: ApplicationLogService;
  let httpMock: HttpTestingController;
  let expectedResult: IApplicationLog | IApplicationLog[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ApplicationLogService);
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

    it('should create a ApplicationLog', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const applicationLog = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(applicationLog).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ApplicationLog', () => {
      const applicationLog = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(applicationLog).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ApplicationLog', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ApplicationLog', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ApplicationLog', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addApplicationLogToCollectionIfMissing', () => {
      it('should add a ApplicationLog to an empty array', () => {
        const applicationLog: IApplicationLog = sampleWithRequiredData;
        expectedResult = service.addApplicationLogToCollectionIfMissing([], applicationLog);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(applicationLog);
      });

      it('should not add a ApplicationLog to an array that contains it', () => {
        const applicationLog: IApplicationLog = sampleWithRequiredData;
        const applicationLogCollection: IApplicationLog[] = [
          {
            ...applicationLog,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addApplicationLogToCollectionIfMissing(applicationLogCollection, applicationLog);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ApplicationLog to an array that doesn't contain it", () => {
        const applicationLog: IApplicationLog = sampleWithRequiredData;
        const applicationLogCollection: IApplicationLog[] = [sampleWithPartialData];
        expectedResult = service.addApplicationLogToCollectionIfMissing(applicationLogCollection, applicationLog);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(applicationLog);
      });

      it('should add only unique ApplicationLog to an array', () => {
        const applicationLogArray: IApplicationLog[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const applicationLogCollection: IApplicationLog[] = [sampleWithRequiredData];
        expectedResult = service.addApplicationLogToCollectionIfMissing(applicationLogCollection, ...applicationLogArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const applicationLog: IApplicationLog = sampleWithRequiredData;
        const applicationLog2: IApplicationLog = sampleWithPartialData;
        expectedResult = service.addApplicationLogToCollectionIfMissing([], applicationLog, applicationLog2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(applicationLog);
        expect(expectedResult).toContain(applicationLog2);
      });

      it('should accept null and undefined values', () => {
        const applicationLog: IApplicationLog = sampleWithRequiredData;
        expectedResult = service.addApplicationLogToCollectionIfMissing([], null, applicationLog, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(applicationLog);
      });

      it('should return initial array if no ApplicationLog is added', () => {
        const applicationLogCollection: IApplicationLog[] = [sampleWithRequiredData];
        expectedResult = service.addApplicationLogToCollectionIfMissing(applicationLogCollection, undefined, null);
        expect(expectedResult).toEqual(applicationLogCollection);
      });
    });

    describe('compareApplicationLog', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareApplicationLog(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareApplicationLog(entity1, entity2);
        const compareResult2 = service.compareApplicationLog(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareApplicationLog(entity1, entity2);
        const compareResult2 = service.compareApplicationLog(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareApplicationLog(entity1, entity2);
        const compareResult2 = service.compareApplicationLog(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
