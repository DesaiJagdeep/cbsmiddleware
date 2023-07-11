import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CropCategoryMasterFormService } from './crop-category-master-form.service';
import { CropCategoryMasterService } from '../service/crop-category-master.service';
import { ICropCategoryMaster } from '../crop-category-master.model';

import { CropCategoryMasterUpdateComponent } from './crop-category-master-update.component';

describe('CropCategoryMaster Management Update Component', () => {
  let comp: CropCategoryMasterUpdateComponent;
  let fixture: ComponentFixture<CropCategoryMasterUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let cropCategoryMasterFormService: CropCategoryMasterFormService;
  let cropCategoryMasterService: CropCategoryMasterService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CropCategoryMasterUpdateComponent],
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
      .overrideTemplate(CropCategoryMasterUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CropCategoryMasterUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    cropCategoryMasterFormService = TestBed.inject(CropCategoryMasterFormService);
    cropCategoryMasterService = TestBed.inject(CropCategoryMasterService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const cropCategoryMaster: ICropCategoryMaster = { id: 456 };

      activatedRoute.data = of({ cropCategoryMaster });
      comp.ngOnInit();

      expect(comp.cropCategoryMaster).toEqual(cropCategoryMaster);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICropCategoryMaster>>();
      const cropCategoryMaster = { id: 123 };
      jest.spyOn(cropCategoryMasterFormService, 'getCropCategoryMaster').mockReturnValue(cropCategoryMaster);
      jest.spyOn(cropCategoryMasterService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cropCategoryMaster });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cropCategoryMaster }));
      saveSubject.complete();

      // THEN
      expect(cropCategoryMasterFormService.getCropCategoryMaster).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(cropCategoryMasterService.update).toHaveBeenCalledWith(expect.objectContaining(cropCategoryMaster));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICropCategoryMaster>>();
      const cropCategoryMaster = { id: 123 };
      jest.spyOn(cropCategoryMasterFormService, 'getCropCategoryMaster').mockReturnValue({ id: null });
      jest.spyOn(cropCategoryMasterService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cropCategoryMaster: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cropCategoryMaster }));
      saveSubject.complete();

      // THEN
      expect(cropCategoryMasterFormService.getCropCategoryMaster).toHaveBeenCalled();
      expect(cropCategoryMasterService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICropCategoryMaster>>();
      const cropCategoryMaster = { id: 123 };
      jest.spyOn(cropCategoryMasterService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cropCategoryMaster });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(cropCategoryMasterService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
