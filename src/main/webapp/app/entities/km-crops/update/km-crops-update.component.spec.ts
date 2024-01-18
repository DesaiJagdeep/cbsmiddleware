import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { KmCropsFormService } from './km-crops-form.service';
import { KmCropsService } from '../service/km-crops.service';
import { IKmCrops } from '../km-crops.model';
import { ICropMaster } from 'app/entities/crop-master/crop-master.model';
import { CropMasterService } from 'app/entities/crop-master/service/crop-master.service';
import { IKmDetails } from 'app/entities/km-details/km-details.model';
import { KmDetailsService } from 'app/entities/km-details/service/km-details.service';

import { KmCropsUpdateComponent } from './km-crops-update.component';

describe('KmCrops Management Update Component', () => {
  let comp: KmCropsUpdateComponent;
  let fixture: ComponentFixture<KmCropsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let kmCropsFormService: KmCropsFormService;
  let kmCropsService: KmCropsService;
  let cropMasterService: CropMasterService;
  let kmDetailsService: KmDetailsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [KmCropsUpdateComponent],
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
      .overrideTemplate(KmCropsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(KmCropsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    kmCropsFormService = TestBed.inject(KmCropsFormService);
    kmCropsService = TestBed.inject(KmCropsService);
    cropMasterService = TestBed.inject(CropMasterService);
    kmDetailsService = TestBed.inject(KmDetailsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call CropMaster query and add missing value', () => {
      const kmCrops: IKmCrops = { id: 456 };
      const cropMaster: ICropMaster = { id: 17345 };
      kmCrops.cropMaster = cropMaster;

      const cropMasterCollection: ICropMaster[] = [{ id: 61416 }];
      jest.spyOn(cropMasterService, 'query').mockReturnValue(of(new HttpResponse({ body: cropMasterCollection })));
      const additionalCropMasters = [cropMaster];
      const expectedCollection: ICropMaster[] = [...additionalCropMasters, ...cropMasterCollection];
      jest.spyOn(cropMasterService, 'addCropMasterToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ kmCrops });
      comp.ngOnInit();

      expect(cropMasterService.query).toHaveBeenCalled();
      expect(cropMasterService.addCropMasterToCollectionIfMissing).toHaveBeenCalledWith(
        cropMasterCollection,
        ...additionalCropMasters.map(expect.objectContaining)
      );
      expect(comp.cropMastersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call KmDetails query and add missing value', () => {
      const kmCrops: IKmCrops = { id: 456 };
      const kmDetails: IKmDetails = { id: 74523 };
      kmCrops.kmDetails = kmDetails;

      const kmDetailsCollection: IKmDetails[] = [{ id: 85767 }];
      jest.spyOn(kmDetailsService, 'query').mockReturnValue(of(new HttpResponse({ body: kmDetailsCollection })));
      const additionalKmDetails = [kmDetails];
      const expectedCollection: IKmDetails[] = [...additionalKmDetails, ...kmDetailsCollection];
      jest.spyOn(kmDetailsService, 'addKmDetailsToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ kmCrops });
      comp.ngOnInit();

      expect(kmDetailsService.query).toHaveBeenCalled();
      expect(kmDetailsService.addKmDetailsToCollectionIfMissing).toHaveBeenCalledWith(
        kmDetailsCollection,
        ...additionalKmDetails.map(expect.objectContaining)
      );
      expect(comp.kmDetailsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const kmCrops: IKmCrops = { id: 456 };
      const cropMaster: ICropMaster = { id: 84677 };
      kmCrops.cropMaster = cropMaster;
      const kmDetails: IKmDetails = { id: 82586 };
      kmCrops.kmDetails = kmDetails;

      activatedRoute.data = of({ kmCrops });
      comp.ngOnInit();

      expect(comp.cropMastersSharedCollection).toContain(cropMaster);
      expect(comp.kmDetailsSharedCollection).toContain(kmDetails);
      expect(comp.kmCrops).toEqual(kmCrops);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IKmCrops>>();
      const kmCrops = { id: 123 };
      jest.spyOn(kmCropsFormService, 'getKmCrops').mockReturnValue(kmCrops);
      jest.spyOn(kmCropsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ kmCrops });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: kmCrops }));
      saveSubject.complete();

      // THEN
      expect(kmCropsFormService.getKmCrops).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(kmCropsService.update).toHaveBeenCalledWith(expect.objectContaining(kmCrops));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IKmCrops>>();
      const kmCrops = { id: 123 };
      jest.spyOn(kmCropsFormService, 'getKmCrops').mockReturnValue({ id: null });
      jest.spyOn(kmCropsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ kmCrops: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: kmCrops }));
      saveSubject.complete();

      // THEN
      expect(kmCropsFormService.getKmCrops).toHaveBeenCalled();
      expect(kmCropsService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IKmCrops>>();
      const kmCrops = { id: 123 };
      jest.spyOn(kmCropsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ kmCrops });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(kmCropsService.update).toHaveBeenCalled();
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

    describe('compareKmDetails', () => {
      it('Should forward to kmDetailsService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(kmDetailsService, 'compareKmDetails');
        comp.compareKmDetails(entity, entity2);
        expect(kmDetailsService.compareKmDetails).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
