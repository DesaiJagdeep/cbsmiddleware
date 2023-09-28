import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IKamalMaryadaPatrak } from '../kamal-maryada-patrak.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../kamal-maryada-patrak.test-samples';

import { KamalMaryadaPatrakService, RestKamalMaryadaPatrak } from './kamal-maryada-patrak.service';

const requireRestSample: RestKamalMaryadaPatrak = {
  ...sampleWithRequiredData,
  kmDate: sampleWithRequiredData.kmDate?.format(DATE_FORMAT),
  toKMDate: sampleWithRequiredData.toKMDate?.format(DATE_FORMAT),
  meetingDate: sampleWithRequiredData.meetingDate?.format(DATE_FORMAT),
};

describe('KamalMaryadaPatrak Service', () => {
  let service: KamalMaryadaPatrakService;
  let httpMock: HttpTestingController;
  let expectedResult: IKamalMaryadaPatrak | IKamalMaryadaPatrak[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(KamalMaryadaPatrakService);
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

    it('should create a KamalMaryadaPatrak', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const kamalMaryadaPatrak = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(kamalMaryadaPatrak).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a KamalMaryadaPatrak', () => {
      const kamalMaryadaPatrak = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(kamalMaryadaPatrak).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a KamalMaryadaPatrak', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of KamalMaryadaPatrak', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a KamalMaryadaPatrak', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addKamalMaryadaPatrakToCollectionIfMissing', () => {
      it('should add a KamalMaryadaPatrak to an empty array', () => {
        const kamalMaryadaPatrak: IKamalMaryadaPatrak = sampleWithRequiredData;
        expectedResult = service.addKamalMaryadaPatrakToCollectionIfMissing([], kamalMaryadaPatrak);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(kamalMaryadaPatrak);
      });

      it('should not add a KamalMaryadaPatrak to an array that contains it', () => {
        const kamalMaryadaPatrak: IKamalMaryadaPatrak = sampleWithRequiredData;
        const kamalMaryadaPatrakCollection: IKamalMaryadaPatrak[] = [
          {
            ...kamalMaryadaPatrak,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addKamalMaryadaPatrakToCollectionIfMissing(kamalMaryadaPatrakCollection, kamalMaryadaPatrak);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a KamalMaryadaPatrak to an array that doesn't contain it", () => {
        const kamalMaryadaPatrak: IKamalMaryadaPatrak = sampleWithRequiredData;
        const kamalMaryadaPatrakCollection: IKamalMaryadaPatrak[] = [sampleWithPartialData];
        expectedResult = service.addKamalMaryadaPatrakToCollectionIfMissing(kamalMaryadaPatrakCollection, kamalMaryadaPatrak);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(kamalMaryadaPatrak);
      });

      it('should add only unique KamalMaryadaPatrak to an array', () => {
        const kamalMaryadaPatrakArray: IKamalMaryadaPatrak[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const kamalMaryadaPatrakCollection: IKamalMaryadaPatrak[] = [sampleWithRequiredData];
        expectedResult = service.addKamalMaryadaPatrakToCollectionIfMissing(kamalMaryadaPatrakCollection, ...kamalMaryadaPatrakArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const kamalMaryadaPatrak: IKamalMaryadaPatrak = sampleWithRequiredData;
        const kamalMaryadaPatrak2: IKamalMaryadaPatrak = sampleWithPartialData;
        expectedResult = service.addKamalMaryadaPatrakToCollectionIfMissing([], kamalMaryadaPatrak, kamalMaryadaPatrak2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(kamalMaryadaPatrak);
        expect(expectedResult).toContain(kamalMaryadaPatrak2);
      });

      it('should accept null and undefined values', () => {
        const kamalMaryadaPatrak: IKamalMaryadaPatrak = sampleWithRequiredData;
        expectedResult = service.addKamalMaryadaPatrakToCollectionIfMissing([], null, kamalMaryadaPatrak, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(kamalMaryadaPatrak);
      });

      it('should return initial array if no KamalMaryadaPatrak is added', () => {
        const kamalMaryadaPatrakCollection: IKamalMaryadaPatrak[] = [sampleWithRequiredData];
        expectedResult = service.addKamalMaryadaPatrakToCollectionIfMissing(kamalMaryadaPatrakCollection, undefined, null);
        expect(expectedResult).toEqual(kamalMaryadaPatrakCollection);
      });
    });

    describe('compareKamalMaryadaPatrak', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareKamalMaryadaPatrak(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareKamalMaryadaPatrak(entity1, entity2);
        const compareResult2 = service.compareKamalMaryadaPatrak(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareKamalMaryadaPatrak(entity1, entity2);
        const compareResult2 = service.compareKamalMaryadaPatrak(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareKamalMaryadaPatrak(entity1, entity2);
        const compareResult2 = service.compareKamalMaryadaPatrak(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
