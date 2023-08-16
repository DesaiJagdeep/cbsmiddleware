import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CbsDataReportFormService } from './cbs-data-report-form.service';
import { CbsDataReportService } from '../service/cbs-data-report.service';
import { ICbsDataReport } from '../cbs-data-report.model';

import { CbsDataReportUpdateComponent } from './cbs-data-report-update.component';

describe('CbsDataReport Management Update Component', () => {
  let comp: CbsDataReportUpdateComponent;
  let fixture: ComponentFixture<CbsDataReportUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let cbsDataReportFormService: CbsDataReportFormService;
  let cbsDataReportService: CbsDataReportService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CbsDataReportUpdateComponent],
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
      .overrideTemplate(CbsDataReportUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CbsDataReportUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    cbsDataReportFormService = TestBed.inject(CbsDataReportFormService);
    cbsDataReportService = TestBed.inject(CbsDataReportService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const cbsDataReport: ICbsDataReport = { id: 456 };

      activatedRoute.data = of({ cbsDataReport });
      comp.ngOnInit();

      expect(comp.cbsDataReport).toEqual(cbsDataReport);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICbsDataReport>>();
      const cbsDataReport = { id: 123 };
      jest.spyOn(cbsDataReportFormService, 'getCbsDataReport').mockReturnValue(cbsDataReport);
      jest.spyOn(cbsDataReportService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cbsDataReport });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cbsDataReport }));
      saveSubject.complete();

      // THEN
      expect(cbsDataReportFormService.getCbsDataReport).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(cbsDataReportService.update).toHaveBeenCalledWith(expect.objectContaining(cbsDataReport));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICbsDataReport>>();
      const cbsDataReport = { id: 123 };
      jest.spyOn(cbsDataReportFormService, 'getCbsDataReport').mockReturnValue({ id: null });
      jest.spyOn(cbsDataReportService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cbsDataReport: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cbsDataReport }));
      saveSubject.complete();

      // THEN
      expect(cbsDataReportFormService.getCbsDataReport).toHaveBeenCalled();
      expect(cbsDataReportService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICbsDataReport>>();
      const cbsDataReport = { id: 123 };
      jest.spyOn(cbsDataReportService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cbsDataReport });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(cbsDataReportService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
