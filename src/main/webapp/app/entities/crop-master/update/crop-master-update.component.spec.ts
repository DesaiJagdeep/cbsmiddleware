import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CropMasterFormService } from './crop-master-form.service';
import { CropMasterService } from '../service/crop-master.service';
import { ICropMaster } from '../crop-master.model';

import { CropMasterUpdateComponent } from './crop-master-update.component';

describe('CropMaster Management Update Component', () => {
  let comp: CropMasterUpdateComponent;
  let fixture: ComponentFixture<CropMasterUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let cropMasterFormService: CropMasterFormService;
  let cropMasterService: CropMasterService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CropMasterUpdateComponent],
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
      .overrideTemplate(CropMasterUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CropMasterUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    cropMasterFormService = TestBed.inject(CropMasterFormService);
    cropMasterService = TestBed.inject(CropMasterService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const cropMaster: ICropMaster = { id: 456 };

      activatedRoute.data = of({ cropMaster });
      comp.ngOnInit();

      expect(comp.cropMaster).toEqual(cropMaster);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICropMaster>>();
      const cropMaster = { id: 123 };
      jest.spyOn(cropMasterFormService, 'getCropMaster').mockReturnValue(cropMaster);
      jest.spyOn(cropMasterService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cropMaster });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cropMaster }));
      saveSubject.complete();

      // THEN
      expect(cropMasterFormService.getCropMaster).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(cropMasterService.update).toHaveBeenCalledWith(expect.objectContaining(cropMaster));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICropMaster>>();
      const cropMaster = { id: 123 };
      jest.spyOn(cropMasterFormService, 'getCropMaster').mockReturnValue({ id: null });
      jest.spyOn(cropMasterService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cropMaster: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cropMaster }));
      saveSubject.complete();

      // THEN
      expect(cropMasterFormService.getCropMaster).toHaveBeenCalled();
      expect(cropMasterService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICropMaster>>();
      const cropMaster = { id: 123 };
      jest.spyOn(cropMasterService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cropMaster });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(cropMasterService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
