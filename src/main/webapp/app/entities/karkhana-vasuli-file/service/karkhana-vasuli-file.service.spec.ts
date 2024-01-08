import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IKarkhanaVasuliFile } from '../karkhana-vasuli-file.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../karkhana-vasuli-file.test-samples';

import { KarkhanaVasuliFileService, RestKarkhanaVasuliFile } from './karkhana-vasuli-file.service';

const requireRestSample: RestKarkhanaVasuliFile = {
  ...sampleWithRequiredData,
  fromDate: sampleWithRequiredData.fromDate?.toJSON(),
  toDate: sampleWithRequiredData.toDate?.toJSON(),
};

describe('KarkhanaVasuliFile Service', () => {
  let service: KarkhanaVasuliFileService;
  let httpMock: HttpTestingController;
  let expectedResult: IKarkhanaVasuliFile | IKarkhanaVasuliFile[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(KarkhanaVasuliFileService);
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

    it('should create a KarkhanaVasuliFile', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const karkhanaVasuliFile = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(karkhanaVasuliFile).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a KarkhanaVasuliFile', () => {
      const karkhanaVasuliFile = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(karkhanaVasuliFile).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a KarkhanaVasuliFile', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of KarkhanaVasuliFile', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a KarkhanaVasuliFile', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addKarkhanaVasuliFileToCollectionIfMissing', () => {
      it('should add a KarkhanaVasuliFile to an empty array', () => {
        const karkhanaVasuliFile: IKarkhanaVasuliFile = sampleWithRequiredData;
        expectedResult = service.addKarkhanaVasuliFileToCollectionIfMissing([], karkhanaVasuliFile);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(karkhanaVasuliFile);
      });

      it('should not add a KarkhanaVasuliFile to an array that contains it', () => {
        const karkhanaVasuliFile: IKarkhanaVasuliFile = sampleWithRequiredData;
        const karkhanaVasuliFileCollection: IKarkhanaVasuliFile[] = [
          {
            ...karkhanaVasuliFile,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addKarkhanaVasuliFileToCollectionIfMissing(karkhanaVasuliFileCollection, karkhanaVasuliFile);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a KarkhanaVasuliFile to an array that doesn't contain it", () => {
        const karkhanaVasuliFile: IKarkhanaVasuliFile = sampleWithRequiredData;
        const karkhanaVasuliFileCollection: IKarkhanaVasuliFile[] = [sampleWithPartialData];
        expectedResult = service.addKarkhanaVasuliFileToCollectionIfMissing(karkhanaVasuliFileCollection, karkhanaVasuliFile);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(karkhanaVasuliFile);
      });

      it('should add only unique KarkhanaVasuliFile to an array', () => {
        const karkhanaVasuliFileArray: IKarkhanaVasuliFile[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const karkhanaVasuliFileCollection: IKarkhanaVasuliFile[] = [sampleWithRequiredData];
        expectedResult = service.addKarkhanaVasuliFileToCollectionIfMissing(karkhanaVasuliFileCollection, ...karkhanaVasuliFileArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const karkhanaVasuliFile: IKarkhanaVasuliFile = sampleWithRequiredData;
        const karkhanaVasuliFile2: IKarkhanaVasuliFile = sampleWithPartialData;
        expectedResult = service.addKarkhanaVasuliFileToCollectionIfMissing([], karkhanaVasuliFile, karkhanaVasuliFile2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(karkhanaVasuliFile);
        expect(expectedResult).toContain(karkhanaVasuliFile2);
      });

      it('should accept null and undefined values', () => {
        const karkhanaVasuliFile: IKarkhanaVasuliFile = sampleWithRequiredData;
        expectedResult = service.addKarkhanaVasuliFileToCollectionIfMissing([], null, karkhanaVasuliFile, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(karkhanaVasuliFile);
      });

      it('should return initial array if no KarkhanaVasuliFile is added', () => {
        const karkhanaVasuliFileCollection: IKarkhanaVasuliFile[] = [sampleWithRequiredData];
        expectedResult = service.addKarkhanaVasuliFileToCollectionIfMissing(karkhanaVasuliFileCollection, undefined, null);
        expect(expectedResult).toEqual(karkhanaVasuliFileCollection);
      });
    });

    describe('compareKarkhanaVasuliFile', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareKarkhanaVasuliFile(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareKarkhanaVasuliFile(entity1, entity2);
        const compareResult2 = service.compareKarkhanaVasuliFile(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareKarkhanaVasuliFile(entity1, entity2);
        const compareResult2 = service.compareKarkhanaVasuliFile(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareKarkhanaVasuliFile(entity1, entity2);
        const compareResult2 = service.compareKarkhanaVasuliFile(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
