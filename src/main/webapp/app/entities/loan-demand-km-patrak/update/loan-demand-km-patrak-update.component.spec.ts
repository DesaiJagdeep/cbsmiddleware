import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { LoanDemandKMPatrakFormService } from './loan-demand-km-patrak-form.service';
import { LoanDemandKMPatrakService } from '../service/loan-demand-km-patrak.service';
import { ILoanDemandKMPatrak } from '../loan-demand-km-patrak.model';

import { LoanDemandKMPatrakUpdateComponent } from './loan-demand-km-patrak-update.component';

describe('LoanDemandKMPatrak Management Update Component', () => {
  let comp: LoanDemandKMPatrakUpdateComponent;
  let fixture: ComponentFixture<LoanDemandKMPatrakUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let loanDemandKMPatrakFormService: LoanDemandKMPatrakFormService;
  let loanDemandKMPatrakService: LoanDemandKMPatrakService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [LoanDemandKMPatrakUpdateComponent],
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
      .overrideTemplate(LoanDemandKMPatrakUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(LoanDemandKMPatrakUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    loanDemandKMPatrakFormService = TestBed.inject(LoanDemandKMPatrakFormService);
    loanDemandKMPatrakService = TestBed.inject(LoanDemandKMPatrakService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const loanDemandKMPatrak: ILoanDemandKMPatrak = { id: 456 };

      activatedRoute.data = of({ loanDemandKMPatrak });
      comp.ngOnInit();

      expect(comp.loanDemandKMPatrak).toEqual(loanDemandKMPatrak);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILoanDemandKMPatrak>>();
      const loanDemandKMPatrak = { id: 123 };
      jest.spyOn(loanDemandKMPatrakFormService, 'getLoanDemandKMPatrak').mockReturnValue(loanDemandKMPatrak);
      jest.spyOn(loanDemandKMPatrakService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ loanDemandKMPatrak });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: loanDemandKMPatrak }));
      saveSubject.complete();

      // THEN
      expect(loanDemandKMPatrakFormService.getLoanDemandKMPatrak).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(loanDemandKMPatrakService.update).toHaveBeenCalledWith(expect.objectContaining(loanDemandKMPatrak));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILoanDemandKMPatrak>>();
      const loanDemandKMPatrak = { id: 123 };
      jest.spyOn(loanDemandKMPatrakFormService, 'getLoanDemandKMPatrak').mockReturnValue({ id: null });
      jest.spyOn(loanDemandKMPatrakService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ loanDemandKMPatrak: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: loanDemandKMPatrak }));
      saveSubject.complete();

      // THEN
      expect(loanDemandKMPatrakFormService.getLoanDemandKMPatrak).toHaveBeenCalled();
      expect(loanDemandKMPatrakService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILoanDemandKMPatrak>>();
      const loanDemandKMPatrak = { id: 123 };
      jest.spyOn(loanDemandKMPatrakService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ loanDemandKMPatrak });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(loanDemandKMPatrakService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
