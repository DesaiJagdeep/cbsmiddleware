import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CourtCaseSettingFormService } from './court-case-setting-form.service';
import { CourtCaseSettingService } from '../service/court-case-setting.service';
import { ICourtCaseSetting } from '../court-case-setting.model';

import { CourtCaseSettingUpdateComponent } from './court-case-setting-update.component';

describe('CourtCaseSetting Management Update Component', () => {
  let comp: CourtCaseSettingUpdateComponent;
  let fixture: ComponentFixture<CourtCaseSettingUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let courtCaseSettingFormService: CourtCaseSettingFormService;
  let courtCaseSettingService: CourtCaseSettingService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CourtCaseSettingUpdateComponent],
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
      .overrideTemplate(CourtCaseSettingUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CourtCaseSettingUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    courtCaseSettingFormService = TestBed.inject(CourtCaseSettingFormService);
    courtCaseSettingService = TestBed.inject(CourtCaseSettingService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const courtCaseSetting: ICourtCaseSetting = { id: 456 };

      activatedRoute.data = of({ courtCaseSetting });
      comp.ngOnInit();

      expect(comp.courtCaseSetting).toEqual(courtCaseSetting);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICourtCaseSetting>>();
      const courtCaseSetting = { id: 123 };
      jest.spyOn(courtCaseSettingFormService, 'getCourtCaseSetting').mockReturnValue(courtCaseSetting);
      jest.spyOn(courtCaseSettingService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ courtCaseSetting });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: courtCaseSetting }));
      saveSubject.complete();

      // THEN
      expect(courtCaseSettingFormService.getCourtCaseSetting).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(courtCaseSettingService.update).toHaveBeenCalledWith(expect.objectContaining(courtCaseSetting));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICourtCaseSetting>>();
      const courtCaseSetting = { id: 123 };
      jest.spyOn(courtCaseSettingFormService, 'getCourtCaseSetting').mockReturnValue({ id: null });
      jest.spyOn(courtCaseSettingService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ courtCaseSetting: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: courtCaseSetting }));
      saveSubject.complete();

      // THEN
      expect(courtCaseSettingFormService.getCourtCaseSetting).toHaveBeenCalled();
      expect(courtCaseSettingService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICourtCaseSetting>>();
      const courtCaseSetting = { id: 123 };
      jest.spyOn(courtCaseSettingService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ courtCaseSetting });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(courtCaseSettingService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
