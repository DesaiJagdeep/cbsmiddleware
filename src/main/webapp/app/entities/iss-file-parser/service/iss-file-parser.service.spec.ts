import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IIssFileParser } from '../iss-file-parser.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../iss-file-parser.test-samples';

import { IssFileParserService } from './iss-file-parser.service';

const requireRestSample: IIssFileParser = {
  ...sampleWithRequiredData,
};

describe('IssFileParser Service', () => {
  let service: IssFileParserService;
  let httpMock: HttpTestingController;
  let expectedResult: IIssFileParser | IIssFileParser[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(IssFileParserService);
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

    it('should create a IssFileParser', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const issFileParser = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(issFileParser).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a IssFileParser', () => {
      const issFileParser = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(issFileParser).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a IssFileParser', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of IssFileParser', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a IssFileParser', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addIssFileParserToCollectionIfMissing', () => {
      it('should add a IssFileParser to an empty array', () => {
        const issFileParser: IIssFileParser = sampleWithRequiredData;
        expectedResult = service.addIssFileParserToCollectionIfMissing([], issFileParser);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(issFileParser);
      });

      it('should not add a IssFileParser to an array that contains it', () => {
        const issFileParser: IIssFileParser = sampleWithRequiredData;
        const issFileParserCollection: IIssFileParser[] = [
          {
            ...issFileParser,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addIssFileParserToCollectionIfMissing(issFileParserCollection, issFileParser);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a IssFileParser to an array that doesn't contain it", () => {
        const issFileParser: IIssFileParser = sampleWithRequiredData;
        const issFileParserCollection: IIssFileParser[] = [sampleWithPartialData];
        expectedResult = service.addIssFileParserToCollectionIfMissing(issFileParserCollection, issFileParser);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(issFileParser);
      });

      it('should add only unique IssFileParser to an array', () => {
        const issFileParserArray: IIssFileParser[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const issFileParserCollection: IIssFileParser[] = [sampleWithRequiredData];
        expectedResult = service.addIssFileParserToCollectionIfMissing(issFileParserCollection, ...issFileParserArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const issFileParser: IIssFileParser = sampleWithRequiredData;
        const issFileParser2: IIssFileParser = sampleWithPartialData;
        expectedResult = service.addIssFileParserToCollectionIfMissing([], issFileParser, issFileParser2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(issFileParser);
        expect(expectedResult).toContain(issFileParser2);
      });

      it('should accept null and undefined values', () => {
        const issFileParser: IIssFileParser = sampleWithRequiredData;
        expectedResult = service.addIssFileParserToCollectionIfMissing([], null, issFileParser, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(issFileParser);
      });

      it('should return initial array if no IssFileParser is added', () => {
        const issFileParserCollection: IIssFileParser[] = [sampleWithRequiredData];
        expectedResult = service.addIssFileParserToCollectionIfMissing(issFileParserCollection, undefined, null);
        expect(expectedResult).toEqual(issFileParserCollection);
      });
    });

    describe('compareIssFileParser', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareIssFileParser(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareIssFileParser(entity1, entity2);
        const compareResult2 = service.compareIssFileParser(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareIssFileParser(entity1, entity2);
        const compareResult2 = service.compareIssFileParser(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareIssFileParser(entity1, entity2);
        const compareResult2 = service.compareIssFileParser(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
