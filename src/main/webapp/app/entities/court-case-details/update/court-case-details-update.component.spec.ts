import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CourtCaseDetailsFormService } from './court-case-details-form.service';
import { CourtCaseDetailsService } from '../service/court-case-details.service';
import { ICourtCaseDetails } from '../court-case-details.model';

import { CourtCaseDetailsUpdateComponent } from './court-case-details-update.component';

describe('CourtCaseDetails Management Update Component', () => {
  let comp: CourtCaseDetailsUpdateComponent;
  let fixture: ComponentFixture<CourtCaseDetailsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let courtCaseDetailsFormService: CourtCaseDetailsFormService;
  let courtCaseDetailsService: CourtCaseDetailsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CourtCaseDetailsUpdateComponent],
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
      .overrideTemplate(CourtCaseDetailsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CourtCaseDetailsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    courtCaseDetailsFormService = TestBed.inject(CourtCaseDetailsFormService);
    courtCaseDetailsService = TestBed.inject(CourtCaseDetailsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const courtCaseDetails: ICourtCaseDetails = { id: 456 };

      activatedRoute.data = of({ courtCaseDetails });
      comp.ngOnInit();

      expect(comp.courtCaseDetails).toEqual(courtCaseDetails);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICourtCaseDetails>>();
      const courtCaseDetails = { id: 123 };
      jest.spyOn(courtCaseDetailsFormService, 'getCourtCaseDetails').mockReturnValue(courtCaseDetails);
      jest.spyOn(courtCaseDetailsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ courtCaseDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: courtCaseDetails }));
      saveSubject.complete();

      // THEN
      expect(courtCaseDetailsFormService.getCourtCaseDetails).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(courtCaseDetailsService.update).toHaveBeenCalledWith(expect.objectContaining(courtCaseDetails));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICourtCaseDetails>>();
      const courtCaseDetails = { id: 123 };
      jest.spyOn(courtCaseDetailsFormService, 'getCourtCaseDetails').mockReturnValue({ id: null });
      jest.spyOn(courtCaseDetailsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ courtCaseDetails: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: courtCaseDetails }));
      saveSubject.complete();

      // THEN
      expect(courtCaseDetailsFormService.getCourtCaseDetails).toHaveBeenCalled();
      expect(courtCaseDetailsService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICourtCaseDetails>>();
      const courtCaseDetails = { id: 123 };
      jest.spyOn(courtCaseDetailsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ courtCaseDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(courtCaseDetailsService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
