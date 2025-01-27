import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CourtCaseFormService } from './court-case-form.service';
import { CourtCaseService } from '../service/court-case.service';
import { ICourtCase } from '../court-case.model';

import { CourtCaseUpdateComponent } from './court-case-update.component';

describe('CourtCase Management Update Component', () => {
  let comp: CourtCaseUpdateComponent;
  let fixture: ComponentFixture<CourtCaseUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let courtCaseFormService: CourtCaseFormService;
  let courtCaseService: CourtCaseService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CourtCaseUpdateComponent],
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
      .overrideTemplate(CourtCaseUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CourtCaseUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    courtCaseFormService = TestBed.inject(CourtCaseFormService);
    courtCaseService = TestBed.inject(CourtCaseService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const courtCase: ICourtCase = { id: 456 };

      activatedRoute.data = of({ courtCase });
      comp.ngOnInit();

      expect(comp.courtCase).toEqual(courtCase);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICourtCase>>();
      const courtCase = { id: 123 };
      jest.spyOn(courtCaseFormService, 'getCourtCase').mockReturnValue(courtCase);
      jest.spyOn(courtCaseService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ courtCase });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: courtCase }));
      saveSubject.complete();

      // THEN
      expect(courtCaseFormService.getCourtCase).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(courtCaseService.update).toHaveBeenCalledWith(expect.objectContaining(courtCase));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICourtCase>>();
      const courtCase = { id: 123 };
      jest.spyOn(courtCaseFormService, 'getCourtCase').mockReturnValue({ id: null });
      jest.spyOn(courtCaseService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ courtCase: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: courtCase }));
      saveSubject.complete();

      // THEN
      expect(courtCaseFormService.getCourtCase).toHaveBeenCalled();
      expect(courtCaseService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICourtCase>>();
      const courtCase = { id: 123 };
      jest.spyOn(courtCaseService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ courtCase });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(courtCaseService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
