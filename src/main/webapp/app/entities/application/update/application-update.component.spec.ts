import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ApplicationFormService } from './application-form.service';
import { ApplicationService } from '../service/application.service';
import { IApplication } from '../application.model';
import { IIssFileParser } from 'app/entities/iss-file-parser/iss-file-parser.model';
import { IssFileParserService } from 'app/entities/iss-file-parser/service/iss-file-parser.service';

import { ApplicationUpdateComponent } from './application-update.component';

describe('Application Management Update Component', () => {
  let comp: ApplicationUpdateComponent;
  let fixture: ComponentFixture<ApplicationUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let applicationFormService: ApplicationFormService;
  let applicationService: ApplicationService;
  let issFileParserService: IssFileParserService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ApplicationUpdateComponent],
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
      .overrideTemplate(ApplicationUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ApplicationUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    applicationFormService = TestBed.inject(ApplicationFormService);
    applicationService = TestBed.inject(ApplicationService);
    issFileParserService = TestBed.inject(IssFileParserService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call IssFileParser query and add missing value', () => {
      const application: IApplication = { id: 456 };
      const issFileParser: IIssFileParser = { id: 4798 };
      application.issFileParser = issFileParser;

      const issFileParserCollection: IIssFileParser[] = [{ id: 29690 }];
      jest.spyOn(issFileParserService, 'query').mockReturnValue(of(new HttpResponse({ body: issFileParserCollection })));
      const additionalIssFileParsers = [issFileParser];
      const expectedCollection: IIssFileParser[] = [...additionalIssFileParsers, ...issFileParserCollection];
      jest.spyOn(issFileParserService, 'addIssFileParserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ application });
      comp.ngOnInit();

      expect(issFileParserService.query).toHaveBeenCalled();
      expect(issFileParserService.addIssFileParserToCollectionIfMissing).toHaveBeenCalledWith(
        issFileParserCollection,
        ...additionalIssFileParsers.map(expect.objectContaining)
      );
      expect(comp.issFileParsersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const application: IApplication = { id: 456 };
      const issFileParser: IIssFileParser = { id: 36290 };
      application.issFileParser = issFileParser;

      activatedRoute.data = of({ application });
      comp.ngOnInit();

      expect(comp.issFileParsersSharedCollection).toContain(issFileParser);
      expect(comp.application).toEqual(application);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IApplication>>();
      const application = { id: 123 };
      jest.spyOn(applicationFormService, 'getApplication').mockReturnValue(application);
      jest.spyOn(applicationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ application });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: application }));
      saveSubject.complete();

      // THEN
      expect(applicationFormService.getApplication).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(applicationService.update).toHaveBeenCalledWith(expect.objectContaining(application));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IApplication>>();
      const application = { id: 123 };
      jest.spyOn(applicationFormService, 'getApplication').mockReturnValue({ id: null });
      jest.spyOn(applicationService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ application: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: application }));
      saveSubject.complete();

      // THEN
      expect(applicationFormService.getApplication).toHaveBeenCalled();
      expect(applicationService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IApplication>>();
      const application = { id: 123 };
      jest.spyOn(applicationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ application });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(applicationService.update).toHaveBeenCalled();
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
