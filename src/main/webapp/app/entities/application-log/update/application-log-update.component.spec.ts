import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ApplicationLogFormService } from './application-log-form.service';
import { ApplicationLogService } from '../service/application-log.service';
import { IApplicationLog } from '../application-log.model';
import { IIssFileParser } from 'app/entities/iss-file-parser/iss-file-parser.model';
import { IssFileParserService } from 'app/entities/iss-file-parser/service/iss-file-parser.service';

import { ApplicationLogUpdateComponent } from './application-log-update.component';

describe('ApplicationLog Management Update Component', () => {
  let comp: ApplicationLogUpdateComponent;
  let fixture: ComponentFixture<ApplicationLogUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let applicationLogFormService: ApplicationLogFormService;
  let applicationLogService: ApplicationLogService;
  let issFileParserService: IssFileParserService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ApplicationLogUpdateComponent],
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
      .overrideTemplate(ApplicationLogUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ApplicationLogUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    applicationLogFormService = TestBed.inject(ApplicationLogFormService);
    applicationLogService = TestBed.inject(ApplicationLogService);
    issFileParserService = TestBed.inject(IssFileParserService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call IssFileParser query and add missing value', () => {
      const applicationLog: IApplicationLog = { id: 456 };
      const issFileParser: IIssFileParser = { id: 25743 };
      applicationLog.issFileParser = issFileParser;

      const issFileParserCollection: IIssFileParser[] = [{ id: 54129 }];
      jest.spyOn(issFileParserService, 'query').mockReturnValue(of(new HttpResponse({ body: issFileParserCollection })));
      const additionalIssFileParsers = [issFileParser];
      const expectedCollection: IIssFileParser[] = [...additionalIssFileParsers, ...issFileParserCollection];
      jest.spyOn(issFileParserService, 'addIssFileParserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ applicationLog });
      comp.ngOnInit();

      expect(issFileParserService.query).toHaveBeenCalled();
      expect(issFileParserService.addIssFileParserToCollectionIfMissing).toHaveBeenCalledWith(
        issFileParserCollection,
        ...additionalIssFileParsers.map(expect.objectContaining)
      );
      expect(comp.issFileParsersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const applicationLog: IApplicationLog = { id: 456 };
      const issFileParser: IIssFileParser = { id: 44964 };
      applicationLog.issFileParser = issFileParser;

      activatedRoute.data = of({ applicationLog });
      comp.ngOnInit();

      expect(comp.issFileParsersSharedCollection).toContain(issFileParser);
      expect(comp.applicationLog).toEqual(applicationLog);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IApplicationLog>>();
      const applicationLog = { id: 123 };
      jest.spyOn(applicationLogFormService, 'getApplicationLog').mockReturnValue(applicationLog);
      jest.spyOn(applicationLogService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ applicationLog });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: applicationLog }));
      saveSubject.complete();

      // THEN
      expect(applicationLogFormService.getApplicationLog).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(applicationLogService.update).toHaveBeenCalledWith(expect.objectContaining(applicationLog));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IApplicationLog>>();
      const applicationLog = { id: 123 };
      jest.spyOn(applicationLogFormService, 'getApplicationLog').mockReturnValue({ id: null });
      jest.spyOn(applicationLogService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ applicationLog: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: applicationLog }));
      saveSubject.complete();

      // THEN
      expect(applicationLogFormService.getApplicationLog).toHaveBeenCalled();
      expect(applicationLogService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IApplicationLog>>();
      const applicationLog = { id: 123 };
      jest.spyOn(applicationLogService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ applicationLog });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(applicationLogService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareIssFileParser', () => {
      it('Should forward to issFileParserService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(issFileParserService, 'compareIssFileParser');
        comp.compareIssFileParser(entity, entity2);
        expect(issFileParserService.compareIssFileParser).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
