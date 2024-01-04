import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IKmDetails } from '../km-details.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../km-details.test-samples';

import { KmDetailsService, RestKmDetails } from './km-details.service';

const requireRestSample: RestKmDetails = {
  ...sampleWithRequiredData,
  dueDate: sampleWithRequiredData.dueDate?.toJSON(),
  kmDate: sampleWithRequiredData.kmDate?.toJSON(),
  kmFromDate: sampleWithRequiredData.kmFromDate?.toJSON(),
  kmToDate: sampleWithRequiredData.kmToDate?.toJSON(),
  eAgreementDate: sampleWithRequiredData.eAgreementDate?.toJSON(),
  bojaDate: sampleWithRequiredData.bojaDate?.toJSON(),
};

describe('KmDetails Service', () => {
  let service: KmDetailsService;
  let httpMock: HttpTestingController;
  let expectedResult: IKmDetails | IKmDetails[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(KmDetailsService);
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

    it('should create a KmDetails', () => {
      const kmDetails = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(kmDetails).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a KmDetails', () => {
      const kmDetails = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(kmDetails).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a KmDetails', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of KmDetails', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a KmDetails', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addKmDetailsToCollectionIfMissing', () => {
      it('should add a KmDetails to an empty array', () => {
        const kmDetails: IKmDetails = sampleWithRequiredData;
        expectedResult = service.addKmDetailsToCollectionIfMissing([], kmDetails);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(kmDetails);
      });

      it('should not add a KmDetails to an array that contains it', () => {
        const kmDetails: IKmDetails = sampleWithRequiredData;
        const kmDetailsCollection: IKmDetails[] = [
          {
            ...kmDetails,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addKmDetailsToCollectionIfMissing(kmDetailsCollection, kmDetails);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a KmDetails to an array that doesn't contain it", () => {
        const kmDetails: IKmDetails = sampleWithRequiredData;
        const kmDetailsCollection: IKmDetails[] = [sampleWithPartialData];
        expectedResult = service.addKmDetailsToCollectionIfMissing(kmDetailsCollection, kmDetails);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(kmDetails);
      });

      it('should add only unique KmDetails to an array', () => {
        const kmDetailsArray: IKmDetails[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const kmDetailsCollection: IKmDetails[] = [sampleWithRequiredData];
        expectedResult = service.addKmDetailsToCollectionIfMissing(kmDetailsCollection, ...kmDetailsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const kmDetails: IKmDetails = sampleWithRequiredData;
        const kmDetails2: IKmDetails = sampleWithPartialData;
        expectedResult = service.addKmDetailsToCollectionIfMissing([], kmDetails, kmDetails2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(kmDetails);
        expect(expectedResult).toContain(kmDetails2);
      });

      it('should accept null and undefined values', () => {
        const kmDetails: IKmDetails = sampleWithRequiredData;
        expectedResult = service.addKmDetailsToCollectionIfMissing([], null, kmDetails, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(kmDetails);
      });

      it('should return initial array if no KmDetails is added', () => {
        const kmDetailsCollection: IKmDetails[] = [sampleWithRequiredData];
        expectedResult = service.addKmDetailsToCollectionIfMissing(kmDetailsCollection, undefined, null);
        expect(expectedResult).toEqual(kmDetailsCollection);
      });
    });

    describe('compareKmDetails', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareKmDetails(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareKmDetails(entity1, entity2);
        const compareResult2 = service.compareKmDetails(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareKmDetails(entity1, entity2);
        const compareResult2 = service.compareKmDetails(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareKmDetails(entity1, entity2);
        const compareResult2 = service.compareKmDetails(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
