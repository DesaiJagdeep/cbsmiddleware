import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CropRateMasterFormService } from './crop-rate-master-form.service';
import { CropRateMasterService } from '../service/crop-rate-master.service';
import { ICropRateMaster } from '../crop-rate-master.model';
import { ICropMaster } from 'app/entities/crop-master/crop-master.model';
import { CropMasterService } from 'app/entities/crop-master/service/crop-master.service';

import { CropRateMasterUpdateComponent } from './crop-rate-master-update.component';

describe('CropRateMaster Management Update Component', () => {
  let comp: CropRateMasterUpdateComponent;
  let fixture: ComponentFixture<CropRateMasterUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let cropRateMasterFormService: CropRateMasterFormService;
  let cropRateMasterService: CropRateMasterService;
  let cropMasterService: CropMasterService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CropRateMasterUpdateComponent],
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
      .overrideTemplate(CropRateMasterUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CropRateMasterUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    cropRateMasterFormService = TestBed.inject(CropRateMasterFormService);
    cropRateMasterService = TestBed.inject(CropRateMasterService);
    cropMasterService = TestBed.inject(CropMasterService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call CropMaster query and add missing value', () => {
      const cropRateMaster: ICropRateMaster = { id: 456 };
      const cropMaster: ICropMaster = { id: 87150 };
      cropRateMaster.cropMaster = cropMaster;

      const cropMasterCollection: ICropMaster[] = [{ id: 34685 }];
      jest.spyOn(cropMasterService, 'query').mockReturnValue(of(new HttpResponse({ body: cropMasterCollection })));
      const additionalCropMasters = [cropMaster];
      const expectedCollection: ICropMaster[] = [...additionalCropMasters, ...cropMasterCollection];
      jest.spyOn(cropMasterService, 'addCropMasterToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ cropRateMaster });
      comp.ngOnInit();

      expect(cropMasterService.query).toHaveBeenCalled();
      expect(cropMasterService.addCropMasterToCollectionIfMissing).toHaveBeenCalledWith(
        cropMasterCollection,
        ...additionalCropMasters.map(expect.objectContaining)
      );
      expect(comp.cropMastersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const cropRateMaster: ICropRateMaster = { id: 456 };
      const cropMaster: ICropMaster = { id: 4179 };
      cropRateMaster.cropMaster = cropMaster;

      activatedRoute.data = of({ cropRateMaster });
      comp.ngOnInit();

      expect(comp.cropMastersSharedCollection).toContain(cropMaster);
      expect(comp.cropRateMaster).toEqual(cropRateMaster);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICropRateMaster>>();
      const cropRateMaster = { id: 123 };
      jest.spyOn(cropRateMasterFormService, 'getCropRateMaster').mockReturnValue(cropRateMaster);
      jest.spyOn(cropRateMasterService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cropRateMaster });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cropRateMaster }));
      saveSubject.complete();

      // THEN
      expect(cropRateMasterFormService.getCropRateMaster).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(cropRateMasterService.update).toHaveBeenCalledWith(expect.objectContaining(cropRateMaster));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICropRateMaster>>();
      const cropRateMaster = { id: 123 };
      jest.spyOn(cropRateMasterFormService, 'getCropRateMaster').mockReturnValue({ id: null });
      jest.spyOn(cropRateMasterService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cropRateMaster: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cropRateMaster }));
      saveSubject.complete();

      // THEN
      expect(cropRateMasterFormService.getCropRateMaster).toHaveBeenCalled();
      expect(cropRateMasterService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICropRateMaster>>();
      const cropRateMaster = { id: 123 };
      jest.spyOn(cropRateMasterService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cropRateMaster });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(cropRateMasterService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareCropMaster', () => {
      it('Should forward to cropMasterService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(cropMasterService, 'compareCropMaster');
        comp.compareCropMaster(entity, entity2);
        expect(cropMasterService.compareCropMaster).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
