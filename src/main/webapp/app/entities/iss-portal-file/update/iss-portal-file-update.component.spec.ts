import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IssPortalFileFormService } from './iss-portal-file-form.service';
import { IssPortalFileService } from '../service/iss-portal-file.service';
import { IIssPortalFile } from '../iss-portal-file.model';

import { IssPortalFileUpdateComponent } from './iss-portal-file-update.component';

describe('IssPortalFile Management Update Component', () => {
  let comp: IssPortalFileUpdateComponent;
  let fixture: ComponentFixture<IssPortalFileUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let issPortalFileFormService: IssPortalFileFormService;
  let issPortalFileService: IssPortalFileService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [IssPortalFileUpdateComponent],
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
      .overrideTemplate(IssPortalFileUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(IssPortalFileUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    issPortalFileFormService = TestBed.inject(IssPortalFileFormService);
    issPortalFileService = TestBed.inject(IssPortalFileService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const issPortalFile: IIssPortalFile = { id: 456 };

      activatedRoute.data = of({ issPortalFile });
      comp.ngOnInit();

      expect(comp.issPortalFile).toEqual(issPortalFile);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IIssPortalFile>>();
      const issPortalFile = { id: 123 };
      jest.spyOn(issPortalFileFormService, 'getIssPortalFile').mockReturnValue(issPortalFile);
      jest.spyOn(issPortalFileService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ issPortalFile });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: issPortalFile }));
      saveSubject.complete();

      // THEN
      expect(issPortalFileFormService.getIssPortalFile).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(issPortalFileService.update).toHaveBeenCalledWith(expect.objectContaining(issPortalFile));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IIssPortalFile>>();
      const issPortalFile = { id: 123 };
      jest.spyOn(issPortalFileFormService, 'getIssPortalFile').mockReturnValue({ id: null });
      jest.spyOn(issPortalFileService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ issPortalFile: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: issPortalFile }));
      saveSubject.complete();

      // THEN
      expect(issPortalFileFormService.getIssPortalFile).toHaveBeenCalled();
      expect(issPortalFileService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IIssPortalFile>>();
      const issPortalFile = { id: 123 };
      jest.spyOn(issPortalFileService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ issPortalFile });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(issPortalFileService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
