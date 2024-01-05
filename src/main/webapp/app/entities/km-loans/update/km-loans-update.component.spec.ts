import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IKmDetails } from 'app/entities/km-details/km-details.model';
import { KmDetailsService } from 'app/entities/km-details/service/km-details.service';
import { KmLoansService } from '../service/km-loans.service';
import { IKmLoans } from '../km-loans.model';
import { KmLoansFormService } from './km-loans-form.service';

import { KmLoansUpdateComponent } from './km-loans-update.component';

describe('KmLoans Management Update Component', () => {
  let comp: KmLoansUpdateComponent;
  let fixture: ComponentFixture<KmLoansUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let kmLoansFormService: KmLoansFormService;
  let kmLoansService: KmLoansService;
  let kmDetailsService: KmDetailsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), KmLoansUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(KmLoansUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(KmLoansUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    kmLoansFormService = TestBed.inject(KmLoansFormService);
    kmLoansService = TestBed.inject(KmLoansService);
    kmDetailsService = TestBed.inject(KmDetailsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call KmDetails query and add missing value', () => {
      const kmLoans: IKmLoans = { id: 456 };
      const kmDetails: IKmDetails = { id: 27823 };
      kmLoans.kmDetails = kmDetails;

      const kmDetailsCollection: IKmDetails[] = [{ id: 405 }];
      jest.spyOn(kmDetailsService, 'query').mockReturnValue(of(new HttpResponse({ body: kmDetailsCollection })));
      const additionalKmDetails = [kmDetails];
      const expectedCollection: IKmDetails[] = [...additionalKmDetails, ...kmDetailsCollection];
      jest.spyOn(kmDetailsService, 'addKmDetailsToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ kmLoans });
      comp.ngOnInit();

      expect(kmDetailsService.query).toHaveBeenCalled();
      expect(kmDetailsService.addKmDetailsToCollectionIfMissing).toHaveBeenCalledWith(
        kmDetailsCollection,
        ...additionalKmDetails.map(expect.objectContaining),
      );
      expect(comp.kmDetailsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const kmLoans: IKmLoans = { id: 456 };
      const kmDetails: IKmDetails = { id: 30735 };
      kmLoans.kmDetails = kmDetails;

      activatedRoute.data = of({ kmLoans });
      comp.ngOnInit();

      expect(comp.kmDetailsSharedCollection).toContain(kmDetails);
      expect(comp.kmLoans).toEqual(kmLoans);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IKmLoans>>();
      const kmLoans = { id: 123 };
      jest.spyOn(kmLoansFormService, 'getKmLoans').mockReturnValue(kmLoans);
      jest.spyOn(kmLoansService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ kmLoans });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: kmLoans }));
      saveSubject.complete();

      // THEN
      expect(kmLoansFormService.getKmLoans).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(kmLoansService.update).toHaveBeenCalledWith(expect.objectContaining(kmLoans));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IKmLoans>>();
      const kmLoans = { id: 123 };
      jest.spyOn(kmLoansFormService, 'getKmLoans').mockReturnValue({ id: null });
      jest.spyOn(kmLoansService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ kmLoans: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: kmLoans }));
      saveSubject.complete();

      // THEN
      expect(kmLoansFormService.getKmLoans).toHaveBeenCalled();
      expect(kmLoansService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IKmLoans>>();
      const kmLoans = { id: 123 };
      jest.spyOn(kmLoansService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ kmLoans });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(kmLoansService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareKmDetails', () => {
      it('Should forward to kmDetailsService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(kmDetailsService, 'compareKmDetails');
        comp.compareKmDetails(entity, entity2);
        expect(kmDetailsService.compareKmDetails).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
