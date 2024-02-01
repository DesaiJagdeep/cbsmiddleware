import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IKamalSociety } from '../kamal-society.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../kamal-society.test-samples';

import { KamalSocietyService, RestKamalSociety } from './kamal-society.service';

const requireRestSample: RestKamalSociety = {
  ...sampleWithRequiredData,
  kmDate: sampleWithRequiredData.kmDate?.toJSON(),
  kmFromDate: sampleWithRequiredData.kmFromDate?.toJSON(),
  kmToDate: sampleWithRequiredData.kmToDate?.toJSON(),
  zindagiPatrakDate: sampleWithRequiredData.zindagiPatrakDate?.toJSON(),
  bankTapasaniDate: sampleWithRequiredData.bankTapasaniDate?.toJSON(),
  govTapasaniDate: sampleWithRequiredData.govTapasaniDate?.toJSON(),
  sansthaTapasaniDate: sampleWithRequiredData.sansthaTapasaniDate?.toJSON(),
  talebandDate: sampleWithRequiredData.talebandDate?.toJSON(),
  balanceSheetDate: sampleWithRequiredData.balanceSheetDate?.toJSON(),
};

describe('KamalSociety Service', () => {
  let service: KamalSocietyService;
  let httpMock: HttpTestingController;
  let expectedResult: IKamalSociety | IKamalSociety[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(KamalSocietyService);
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

    it('should create a KamalSociety', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const kamalSociety = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(kamalSociety).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a KamalSociety', () => {
      const kamalSociety = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(kamalSociety).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a KamalSociety', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of KamalSociety', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a KamalSociety', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addKamalSocietyToCollectionIfMissing', () => {
      it('should add a KamalSociety to an empty array', () => {
        const kamalSociety: IKamalSociety = sampleWithRequiredData;
        expectedResult = service.addKamalSocietyToCollectionIfMissing([], kamalSociety);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(kamalSociety);
      });

      it('should not add a KamalSociety to an array that contains it', () => {
        const kamalSociety: IKamalSociety = sampleWithRequiredData;
        const kamalSocietyCollection: IKamalSociety[] = [
          {
            ...kamalSociety,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addKamalSocietyToCollectionIfMissing(kamalSocietyCollection, kamalSociety);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a KamalSociety to an array that doesn't contain it", () => {
        const kamalSociety: IKamalSociety = sampleWithRequiredData;
        const kamalSocietyCollection: IKamalSociety[] = [sampleWithPartialData];
        expectedResult = service.addKamalSocietyToCollectionIfMissing(kamalSocietyCollection, kamalSociety);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(kamalSociety);
      });

      it('should add only unique KamalSociety to an array', () => {
        const kamalSocietyArray: IKamalSociety[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const kamalSocietyCollection: IKamalSociety[] = [sampleWithRequiredData];
        expectedResult = service.addKamalSocietyToCollectionIfMissing(kamalSocietyCollection, ...kamalSocietyArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const kamalSociety: IKamalSociety = sampleWithRequiredData;
        const kamalSociety2: IKamalSociety = sampleWithPartialData;
        expectedResult = service.addKamalSocietyToCollectionIfMissing([], kamalSociety, kamalSociety2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(kamalSociety);
        expect(expectedResult).toContain(kamalSociety2);
      });

      it('should accept null and undefined values', () => {
        const kamalSociety: IKamalSociety = sampleWithRequiredData;
        expectedResult = service.addKamalSocietyToCollectionIfMissing([], null, kamalSociety, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(kamalSociety);
      });

      it('should return initial array if no KamalSociety is added', () => {
        const kamalSocietyCollection: IKamalSociety[] = [sampleWithRequiredData];
        expectedResult = service.addKamalSocietyToCollectionIfMissing(kamalSocietyCollection, undefined, null);
        expect(expectedResult).toEqual(kamalSocietyCollection);
      });
    });

    describe('compareKamalSociety', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareKamalSociety(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareKamalSociety(entity1, entity2);
        const compareResult2 = service.compareKamalSociety(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareKamalSociety(entity1, entity2);
        const compareResult2 = service.compareKamalSociety(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareKamalSociety(entity1, entity2);
        const compareResult2 = service.compareKamalSociety(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
