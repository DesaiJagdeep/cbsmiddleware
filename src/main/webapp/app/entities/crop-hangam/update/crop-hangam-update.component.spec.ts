import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CropHangamFormService } from './crop-hangam-form.service';
import { CropHangamService } from '../service/crop-hangam.service';
import { ICropHangam } from '../crop-hangam.model';

import { CropHangamUpdateComponent } from './crop-hangam-update.component';

describe('CropHangam Management Update Component', () => {
  let comp: CropHangamUpdateComponent;
  let fixture: ComponentFixture<CropHangamUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let cropHangamFormService: CropHangamFormService;
  let cropHangamService: CropHangamService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CropHangamUpdateComponent],
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
      .overrideTemplate(CropHangamUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CropHangamUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    cropHangamFormService = TestBed.inject(CropHangamFormService);
    cropHangamService = TestBed.inject(CropHangamService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const cropHangam: ICropHangam = { id: 456 };

      activatedRoute.data = of({ cropHangam });
      comp.ngOnInit();

      expect(comp.cropHangam).toEqual(cropHangam);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICropHangam>>();
      const cropHangam = { id: 123 };
      jest.spyOn(cropHangamFormService, 'getCropHangam').mockReturnValue(cropHangam);
      jest.spyOn(cropHangamService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cropHangam });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cropHangam }));
      saveSubject.complete();

      // THEN
      expect(cropHangamFormService.getCropHangam).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(cropHangamService.update).toHaveBeenCalledWith(expect.objectContaining(cropHangam));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICropHangam>>();
      const cropHangam = { id: 123 };
      jest.spyOn(cropHangamFormService, 'getCropHangam').mockReturnValue({ id: null });
      jest.spyOn(cropHangamService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cropHangam: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cropHangam }));
      saveSubject.complete();

      // THEN
      expect(cropHangamFormService.getCropHangam).toHaveBeenCalled();
      expect(cropHangamService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICropHangam>>();
      const cropHangam = { id: 123 };
      jest.spyOn(cropHangamService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cropHangam });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(cropHangamService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
