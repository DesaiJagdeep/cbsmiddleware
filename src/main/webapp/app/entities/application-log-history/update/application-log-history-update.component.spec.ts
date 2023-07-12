import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ApplicationLogHistoryFormService } from './application-log-history-form.service';
import { ApplicationLogHistoryService } from '../service/application-log-history.service';
import { IApplicationLogHistory } from '../application-log-history.model';
import { IIssFileParser } from 'app/entities/iss-file-parser/iss-file-parser.model';
import { IssFileParserService } from 'app/entities/iss-file-parser/service/iss-file-parser.service';

import { ApplicationLogHistoryUpdateComponent } from './application-log-history-update.component';

describe('ApplicationLogHistory Management Update Component', () => {
  let comp: ApplicationLogHistoryUpdateComponent;
  let fixture: ComponentFixture<ApplicationLogHistoryUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let applicationLogHistoryFormService: ApplicationLogHistoryFormService;
  let applicationLogHistoryService: ApplicationLogHistoryService;
  let issFileParserService: IssFileParserService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ApplicationLogHistoryUpdateComponent],
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
      .overrideTemplate(ApplicationLogHistoryUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ApplicationLogHistoryUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    applicationLogHistoryFormService = TestBed.inject(ApplicationLogHistoryFormService);
    applicationLogHistoryService = TestBed.inject(ApplicationLogHistoryService);
    issFileParserService = TestBed.inject(IssFileParserService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call IssFileParser query and add missing value', () => {
      const applicationLogHistory: IApplicationLogHistory = { id: 456 };
      const issFileParser: IIssFileParser = { id: 98582 };
      applicationLogHistory.issFileParser = issFileParser;

      const issFileParserCollection: IIssFileParser[] = [{ id: 37139 }];
      jest.spyOn(issFileParserService, 'query').mockReturnValue(of(new HttpResponse({ body: issFileParserCollection })));
      const additionalIssFileParsers = [issFileParser];
      const expectedCollection: IIssFileParser[] = [...additionalIssFileParsers, ...issFileParserCollection];
      jest.spyOn(issFileParserService, 'addIssFileParserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ applicationLogHistory });
      comp.ngOnInit();

      expect(issFileParserService.query).toHaveBeenCalled();
      expect(issFileParserService.addIssFileParserToCollectionIfMissing).toHaveBeenCalledWith(
        issFileParserCollection,
        ...additionalIssFileParsers.map(expect.objectContaining)
      );
      expect(comp.issFileParsersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const applicationLogHistory: IApplicationLogHistory = { id: 456 };
      const issFileParser: IIssFileParser = { id: 17588 };
      applicationLogHistory.issFileParser = issFileParser;

      activatedRoute.data = of({ applicationLogHistory });
      comp.ngOnInit();

      expect(comp.issFileParsersSharedCollection).toContain(issFileParser);
      expect(comp.applicationLogHistory).toEqual(applicationLogHistory);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IApplicationLogHistory>>();
      const applicationLogHistory = { id: 123 };
      jest.spyOn(applicationLogHistoryFormService, 'getApplicationLogHistory').mockReturnValue(applicationLogHistory);
      jest.spyOn(applicationLogHistoryService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ applicationLogHistory });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: applicationLogHistory }));
      saveSubject.complete();

      // THEN
      expect(applicationLogHistoryFormService.getApplicationLogHistory).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(applicationLogHistoryService.update).toHaveBeenCalledWith(expect.objectContaining(applicationLogHistory));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IApplicationLogHistory>>();
      const applicationLogHistory = { id: 123 };
      jest.spyOn(applicationLogHistoryFormService, 'getApplicationLogHistory').mockReturnValue({ id: null });
      jest.spyOn(applicationLogHistoryService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ applicationLogHistory: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: applicationLogHistory }));
      saveSubject.complete();

      // THEN
      expect(applicationLogHistoryFormService.getApplicationLogHistory).toHaveBeenCalled();
      expect(applicationLogHistoryService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IApplicationLogHistory>>();
      const applicationLogHistory = { id: 123 };
      jest.spyOn(applicationLogHistoryService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ applicationLogHistory });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(applicationLogHistoryService.update).toHaveBeenCalled();
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
