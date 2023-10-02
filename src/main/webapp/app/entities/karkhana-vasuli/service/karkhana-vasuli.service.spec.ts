import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IKarkhanaVasuli } from '../karkhana-vasuli.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../karkhana-vasuli.test-samples';

import { KarkhanaVasuliService } from './karkhana-vasuli.service';

const requireRestSample: IKarkhanaVasuli = {
  ...sampleWithRequiredData,
};

describe('KarkhanaVasuli Service', () => {
  let service: KarkhanaVasuliService;
  let httpMock: HttpTestingController;
  let expectedResult: IKarkhanaVasuli | IKarkhanaVasuli[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(KarkhanaVasuliService);
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

    it('should create a KarkhanaVasuli', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const karkhanaVasuli = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(karkhanaVasuli).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a KarkhanaVasuli', () => {
      const karkhanaVasuli = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(karkhanaVasuli).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a KarkhanaVasuli', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of KarkhanaVasuli', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a KarkhanaVasuli', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addKarkhanaVasuliToCollectionIfMissing', () => {
      it('should add a KarkhanaVasuli to an empty array', () => {
        const karkhanaVasuli: IKarkhanaVasuli = sampleWithRequiredData;
        expectedResult = service.addKarkhanaVasuliToCollectionIfMissing([], karkhanaVasuli);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(karkhanaVasuli);
      });

      it('should not add a KarkhanaVasuli to an array that contains it', () => {
        const karkhanaVasuli: IKarkhanaVasuli = sampleWithRequiredData;
        const karkhanaVasuliCollection: IKarkhanaVasuli[] = [
          {
            ...karkhanaVasuli,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addKarkhanaVasuliToCollectionIfMissing(karkhanaVasuliCollection, karkhanaVasuli);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a KarkhanaVasuli to an array that doesn't contain it", () => {
        const karkhanaVasuli: IKarkhanaVasuli = sampleWithRequiredData;
        const karkhanaVasuliCollection: IKarkhanaVasuli[] = [sampleWithPartialData];
        expectedResult = service.addKarkhanaVasuliToCollectionIfMissing(karkhanaVasuliCollection, karkhanaVasuli);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(karkhanaVasuli);
      });

      it('should add only unique KarkhanaVasuli to an array', () => {
        const karkhanaVasuliArray: IKarkhanaVasuli[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const karkhanaVasuliCollection: IKarkhanaVasuli[] = [sampleWithRequiredData];
        expectedResult = service.addKarkhanaVasuliToCollectionIfMissing(karkhanaVasuliCollection, ...karkhanaVasuliArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const karkhanaVasuli: IKarkhanaVasuli = sampleWithRequiredData;
        const karkhanaVasuli2: IKarkhanaVasuli = sampleWithPartialData;
        expectedResult = service.addKarkhanaVasuliToCollectionIfMissing([], karkhanaVasuli, karkhanaVasuli2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(karkhanaVasuli);
        expect(expectedResult).toContain(karkhanaVasuli2);
      });

      it('should accept null and undefined values', () => {
        const karkhanaVasuli: IKarkhanaVasuli = sampleWithRequiredData;
        expectedResult = service.addKarkhanaVasuliToCollectionIfMissing([], null, karkhanaVasuli, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(karkhanaVasuli);
      });

      it('should return initial array if no KarkhanaVasuli is added', () => {
        const karkhanaVasuliCollection: IKarkhanaVasuli[] = [sampleWithRequiredData];
        expectedResult = service.addKarkhanaVasuliToCollectionIfMissing(karkhanaVasuliCollection, undefined, null);
        expect(expectedResult).toEqual(karkhanaVasuliCollection);
      });
    });

    describe('compareKarkhanaVasuli', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareKarkhanaVasuli(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareKarkhanaVasuli(entity1, entity2);
        const compareResult2 = service.compareKarkhanaVasuli(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareKarkhanaVasuli(entity1, entity2);
        const compareResult2 = service.compareKarkhanaVasuli(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareKarkhanaVasuli(entity1, entity2);
        const compareResult2 = service.compareKarkhanaVasuli(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
