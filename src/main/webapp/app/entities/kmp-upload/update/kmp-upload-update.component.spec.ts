import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { KMPUploadFormService } from './kmp-upload-form.service';
import { KMPUploadService } from '../service/kmp-upload.service';
import { IKMPUpload } from '../kmp-upload.model';

import { KMPUploadUpdateComponent } from './kmp-upload-update.component';

describe('KMPUpload Management Update Component', () => {
  let comp: KMPUploadUpdateComponent;
  let fixture: ComponentFixture<KMPUploadUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let kMPUploadFormService: KMPUploadFormService;
  let kMPUploadService: KMPUploadService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [KMPUploadUpdateComponent],
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
      .overrideTemplate(KMPUploadUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(KMPUploadUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    kMPUploadFormService = TestBed.inject(KMPUploadFormService);
    kMPUploadService = TestBed.inject(KMPUploadService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const kMPUpload: IKMPUpload = { id: 456 };

      activatedRoute.data = of({ kMPUpload });
      comp.ngOnInit();

      expect(comp.kMPUpload).toEqual(kMPUpload);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IKMPUpload>>();
      const kMPUpload = { id: 123 };
      jest.spyOn(kMPUploadFormService, 'getKMPUpload').mockReturnValue(kMPUpload);
      jest.spyOn(kMPUploadService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ kMPUpload });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: kMPUpload }));
      saveSubject.complete();

      // THEN
      expect(kMPUploadFormService.getKMPUpload).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(kMPUploadService.update).toHaveBeenCalledWith(expect.objectContaining(kMPUpload));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IKMPUpload>>();
      const kMPUpload = { id: 123 };
      jest.spyOn(kMPUploadFormService, 'getKMPUpload').mockReturnValue({ id: null });
      jest.spyOn(kMPUploadService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ kMPUpload: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: kMPUpload }));
      saveSubject.complete();

      // THEN
      expect(kMPUploadFormService.getKMPUpload).toHaveBeenCalled();
      expect(kMPUploadService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IKMPUpload>>();
      const kMPUpload = { id: 123 };
      jest.spyOn(kMPUploadService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ kMPUpload });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(kMPUploadService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
