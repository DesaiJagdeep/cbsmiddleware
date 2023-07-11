import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IIssPortalFile } from '../iss-portal-file.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../iss-portal-file.test-samples';

import { IssPortalFileService, RestIssPortalFile } from './iss-portal-file.service';

const requireRestSample: RestIssPortalFile = {
  ...sampleWithRequiredData,
  fromDisbursementDate: sampleWithRequiredData.fromDisbursementDate?.format(DATE_FORMAT),
  toDisbursementDate: sampleWithRequiredData.toDisbursementDate?.format(DATE_FORMAT),
};

describe('IssPortalFile Service', () => {
  let service: IssPortalFileService;
  let httpMock: HttpTestingController;
  let expectedResult: IIssPortalFile | IIssPortalFile[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(IssPortalFileService);
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

    it('should create a IssPortalFile', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const issPortalFile = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(issPortalFile).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a IssPortalFile', () => {
      const issPortalFile = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(issPortalFile).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a IssPortalFile', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of IssPortalFile', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a IssPortalFile', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addIssPortalFileToCollectionIfMissing', () => {
      it('should add a IssPortalFile to an empty array', () => {
        const issPortalFile: IIssPortalFile = sampleWithRequiredData;
        expectedResult = service.addIssPortalFileToCollectionIfMissing([], issPortalFile);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(issPortalFile);
      });

      it('should not add a IssPortalFile to an array that contains it', () => {
        const issPortalFile: IIssPortalFile = sampleWithRequiredData;
        const issPortalFileCollection: IIssPortalFile[] = [
          {
            ...issPortalFile,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addIssPortalFileToCollectionIfMissing(issPortalFileCollection, issPortalFile);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a IssPortalFile to an array that doesn't contain it", () => {
        const issPortalFile: IIssPortalFile = sampleWithRequiredData;
        const issPortalFileCollection: IIssPortalFile[] = [sampleWithPartialData];
        expectedResult = service.addIssPortalFileToCollectionIfMissing(issPortalFileCollection, issPortalFile);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(issPortalFile);
      });

      it('should add only unique IssPortalFile to an array', () => {
        const issPortalFileArray: IIssPortalFile[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const issPortalFileCollection: IIssPortalFile[] = [sampleWithRequiredData];
        expectedResult = service.addIssPortalFileToCollectionIfMissing(issPortalFileCollection, ...issPortalFileArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const issPortalFile: IIssPortalFile = sampleWithRequiredData;
        const issPortalFile2: IIssPortalFile = sampleWithPartialData;
        expectedResult = service.addIssPortalFileToCollectionIfMissing([], issPortalFile, issPortalFile2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(issPortalFile);
        expect(expectedResult).toContain(issPortalFile2);
      });

      it('should accept null and undefined values', () => {
        const issPortalFile: IIssPortalFile = sampleWithRequiredData;
        expectedResult = service.addIssPortalFileToCollectionIfMissing([], null, issPortalFile, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(issPortalFile);
      });

      it('should return initial array if no IssPortalFile is added', () => {
        const issPortalFileCollection: IIssPortalFile[] = [sampleWithRequiredData];
        expectedResult = service.addIssPortalFileToCollectionIfMissing(issPortalFileCollection, undefined, null);
        expect(expectedResult).toEqual(issPortalFileCollection);
      });
    });

    describe('compareIssPortalFile', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareIssPortalFile(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareIssPortalFile(entity1, entity2);
        const compareResult2 = service.compareIssPortalFile(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareIssPortalFile(entity1, entity2);
        const compareResult2 = service.compareIssPortalFile(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareIssPortalFile(entity1, entity2);
        const compareResult2 = service.compareIssPortalFile(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
