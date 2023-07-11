import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IssFileParserFormService } from './iss-file-parser-form.service';
import { IssFileParserService } from '../service/iss-file-parser.service';
import { IIssFileParser } from '../iss-file-parser.model';
import { IIssPortalFile } from 'app/entities/iss-portal-file/iss-portal-file.model';
import { IssPortalFileService } from 'app/entities/iss-portal-file/service/iss-portal-file.service';

import { IssFileParserUpdateComponent } from './iss-file-parser-update.component';

describe('IssFileParser Management Update Component', () => {
  let comp: IssFileParserUpdateComponent;
  let fixture: ComponentFixture<IssFileParserUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let issFileParserFormService: IssFileParserFormService;
  let issFileParserService: IssFileParserService;
  let issPortalFileService: IssPortalFileService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [IssFileParserUpdateComponent],
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
      .overrideTemplate(IssFileParserUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(IssFileParserUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    issFileParserFormService = TestBed.inject(IssFileParserFormService);
    issFileParserService = TestBed.inject(IssFileParserService);
    issPortalFileService = TestBed.inject(IssPortalFileService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call IssPortalFile query and add missing value', () => {
      const issFileParser: IIssFileParser = { id: 456 };
      const issPortalFile: IIssPortalFile = { id: 44952 };
      issFileParser.issPortalFile = issPortalFile;

      const issPortalFileCollection: IIssPortalFile[] = [{ id: 1837 }];
      jest.spyOn(issPortalFileService, 'query').mockReturnValue(of(new HttpResponse({ body: issPortalFileCollection })));
      const additionalIssPortalFiles = [issPortalFile];
      const expectedCollection: IIssPortalFile[] = [...additionalIssPortalFiles, ...issPortalFileCollection];
      jest.spyOn(issPortalFileService, 'addIssPortalFileToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ issFileParser });
      comp.ngOnInit();

      expect(issPortalFileService.query).toHaveBeenCalled();
      expect(issPortalFileService.addIssPortalFileToCollectionIfMissing).toHaveBeenCalledWith(
        issPortalFileCollection,
        ...additionalIssPortalFiles.map(expect.objectContaining)
      );
      expect(comp.issPortalFilesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const issFileParser: IIssFileParser = { id: 456 };
      const issPortalFile: IIssPortalFile = { id: 40928 };
      issFileParser.issPortalFile = issPortalFile;

      activatedRoute.data = of({ issFileParser });
      comp.ngOnInit();

      expect(comp.issPortalFilesSharedCollection).toContain(issPortalFile);
      expect(comp.issFileParser).toEqual(issFileParser);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IIssFileParser>>();
      const issFileParser = { id: 123 };
      jest.spyOn(issFileParserFormService, 'getIssFileParser').mockReturnValue(issFileParser);
      jest.spyOn(issFileParserService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ issFileParser });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: issFileParser }));
      saveSubject.complete();

      // THEN
      expect(issFileParserFormService.getIssFileParser).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(issFileParserService.update).toHaveBeenCalledWith(expect.objectContaining(issFileParser));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IIssFileParser>>();
      const issFileParser = { id: 123 };
      jest.spyOn(issFileParserFormService, 'getIssFileParser').mockReturnValue({ id: null });
      jest.spyOn(issFileParserService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ issFileParser: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: issFileParser }));
      saveSubject.complete();

      // THEN
      expect(issFileParserFormService.getIssFileParser).toHaveBeenCalled();
      expect(issFileParserService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IIssFileParser>>();
      const issFileParser = { id: 123 };
      jest.spyOn(issFileParserService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ issFileParser });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(issFileParserService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareIssPortalFile', () => {
      it('Should forward to issPortalFileService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(issPortalFileService, 'compareIssPortalFile');
        comp.compareIssPortalFile(entity, entity2);
        expect(issPortalFileService.compareIssPortalFile).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
