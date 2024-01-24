import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { KamalCropFormService } from './kamal-crop-form.service';
import { KamalCropService } from '../service/kamal-crop.service';
import { IKamalCrop } from '../kamal-crop.model';
import { IFarmerTypeMaster } from 'app/entities/farmer-type-master/farmer-type-master.model';
import { FarmerTypeMasterService } from 'app/entities/farmer-type-master/service/farmer-type-master.service';
import { ICropHangam } from 'app/entities/crop-hangam/crop-hangam.model';
import { CropHangamService } from 'app/entities/crop-hangam/service/crop-hangam.service';
import { ICropMaster } from 'app/entities/crop-master/crop-master.model';
import { CropMasterService } from 'app/entities/crop-master/service/crop-master.service';
import { IKamalSociety } from 'app/entities/kamal-society/kamal-society.model';
import { KamalSocietyService } from 'app/entities/kamal-society/service/kamal-society.service';

import { KamalCropUpdateComponent } from './kamal-crop-update.component';

describe('KamalCrop Management Update Component', () => {
  let comp: KamalCropUpdateComponent;
  let fixture: ComponentFixture<KamalCropUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let kamalCropFormService: KamalCropFormService;
  let kamalCropService: KamalCropService;
  let farmerTypeMasterService: FarmerTypeMasterService;
  let cropHangamService: CropHangamService;
  let cropMasterService: CropMasterService;
  let kamalSocietyService: KamalSocietyService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [KamalCropUpdateComponent],
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
      .overrideTemplate(KamalCropUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(KamalCropUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    kamalCropFormService = TestBed.inject(KamalCropFormService);
    kamalCropService = TestBed.inject(KamalCropService);
    farmerTypeMasterService = TestBed.inject(FarmerTypeMasterService);
    cropHangamService = TestBed.inject(CropHangamService);
    cropMasterService = TestBed.inject(CropMasterService);
    kamalSocietyService = TestBed.inject(KamalSocietyService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call FarmerTypeMaster query and add missing value', () => {
      const kamalCrop: IKamalCrop = { id: 456 };
      const farmerTypeMaster: IFarmerTypeMaster = { id: 62633 };
      kamalCrop.farmerTypeMaster = farmerTypeMaster;

      const farmerTypeMasterCollection: IFarmerTypeMaster[] = [{ id: 25756 }];
      jest.spyOn(farmerTypeMasterService, 'query').mockReturnValue(of(new HttpResponse({ body: farmerTypeMasterCollection })));
      const additionalFarmerTypeMasters = [farmerTypeMaster];
      const expectedCollection: IFarmerTypeMaster[] = [...additionalFarmerTypeMasters, ...farmerTypeMasterCollection];
      jest.spyOn(farmerTypeMasterService, 'addFarmerTypeMasterToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ kamalCrop });
      comp.ngOnInit();

      expect(farmerTypeMasterService.query).toHaveBeenCalled();
      expect(farmerTypeMasterService.addFarmerTypeMasterToCollectionIfMissing).toHaveBeenCalledWith(
        farmerTypeMasterCollection,
        ...additionalFarmerTypeMasters.map(expect.objectContaining)
      );
      expect(comp.farmerTypeMastersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call CropHangam query and add missing value', () => {
      const kamalCrop: IKamalCrop = { id: 456 };
      const cropHangam: ICropHangam = { id: 64036 };
      kamalCrop.cropHangam = cropHangam;

      const cropHangamCollection: ICropHangam[] = [{ id: 87029 }];
      jest.spyOn(cropHangamService, 'query').mockReturnValue(of(new HttpResponse({ body: cropHangamCollection })));
      const additionalCropHangams = [cropHangam];
      const expectedCollection: ICropHangam[] = [...additionalCropHangams, ...cropHangamCollection];
      jest.spyOn(cropHangamService, 'addCropHangamToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ kamalCrop });
      comp.ngOnInit();

      expect(cropHangamService.query).toHaveBeenCalled();
      expect(cropHangamService.addCropHangamToCollectionIfMissing).toHaveBeenCalledWith(
        cropHangamCollection,
        ...additionalCropHangams.map(expect.objectContaining)
      );
      expect(comp.cropHangamsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call CropMaster query and add missing value', () => {
      const kamalCrop: IKamalCrop = { id: 456 };
      const cropMaster: ICropMaster = { id: 45657 };
      kamalCrop.cropMaster = cropMaster;

      const cropMasterCollection: ICropMaster[] = [{ id: 47596 }];
      jest.spyOn(cropMasterService, 'query').mockReturnValue(of(new HttpResponse({ body: cropMasterCollection })));
      const additionalCropMasters = [cropMaster];
      const expectedCollection: ICropMaster[] = [...additionalCropMasters, ...cropMasterCollection];
      jest.spyOn(cropMasterService, 'addCropMasterToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ kamalCrop });
      comp.ngOnInit();

      expect(cropMasterService.query).toHaveBeenCalled();
      expect(cropMasterService.addCropMasterToCollectionIfMissing).toHaveBeenCalledWith(
        cropMasterCollection,
        ...additionalCropMasters.map(expect.objectContaining)
      );
      expect(comp.cropMastersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call KamalSociety query and add missing value', () => {
      const kamalCrop: IKamalCrop = { id: 456 };
      const kamalSociety: IKamalSociety = { id: 43958 };
      kamalCrop.kamalSociety = kamalSociety;

      const kamalSocietyCollection: IKamalSociety[] = [{ id: 50267 }];
      jest.spyOn(kamalSocietyService, 'query').mockReturnValue(of(new HttpResponse({ body: kamalSocietyCollection })));
      const additionalKamalSocieties = [kamalSociety];
      const expectedCollection: IKamalSociety[] = [...additionalKamalSocieties, ...kamalSocietyCollection];
      jest.spyOn(kamalSocietyService, 'addKamalSocietyToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ kamalCrop });
      comp.ngOnInit();

      expect(kamalSocietyService.query).toHaveBeenCalled();
      expect(kamalSocietyService.addKamalSocietyToCollectionIfMissing).toHaveBeenCalledWith(
        kamalSocietyCollection,
        ...additionalKamalSocieties.map(expect.objectContaining)
      );
      expect(comp.kamalSocietiesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const kamalCrop: IKamalCrop = { id: 456 };
      const farmerTypeMaster: IFarmerTypeMaster = { id: 55511 };
      kamalCrop.farmerTypeMaster = farmerTypeMaster;
      const cropHangam: ICropHangam = { id: 3172 };
      kamalCrop.cropHangam = cropHangam;
      const cropMaster: ICropMaster = { id: 95278 };
      kamalCrop.cropMaster = cropMaster;
      const kamalSociety: IKamalSociety = { id: 75093 };
      kamalCrop.kamalSociety = kamalSociety;

      activatedRoute.data = of({ kamalCrop });
      comp.ngOnInit();

      expect(comp.farmerTypeMastersSharedCollection).toContain(farmerTypeMaster);
      expect(comp.cropHangamsSharedCollection).toContain(cropHangam);
      expect(comp.cropMastersSharedCollection).toContain(cropMaster);
      expect(comp.kamalSocietiesSharedCollection).toContain(kamalSociety);
      expect(comp.kamalCrop).toEqual(kamalCrop);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IKamalCrop>>();
      const kamalCrop = { id: 123 };
      jest.spyOn(kamalCropFormService, 'getKamalCrop').mockReturnValue(kamalCrop);
      jest.spyOn(kamalCropService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ kamalCrop });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: kamalCrop }));
      saveSubject.complete();

      // THEN
      expect(kamalCropFormService.getKamalCrop).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(kamalCropService.update).toHaveBeenCalledWith(expect.objectContaining(kamalCrop));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IKamalCrop>>();
      const kamalCrop = { id: 123 };
      jest.spyOn(kamalCropFormService, 'getKamalCrop').mockReturnValue({ id: null });
      jest.spyOn(kamalCropService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ kamalCrop: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: kamalCrop }));
      saveSubject.complete();

      // THEN
      expect(kamalCropFormService.getKamalCrop).toHaveBeenCalled();
      expect(kamalCropService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IKamalCrop>>();
      const kamalCrop = { id: 123 };
      jest.spyOn(kamalCropService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ kamalCrop });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(kamalCropService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareFarmerTypeMaster', () => {
      it('Should forward to farmerTypeMasterService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(farmerTypeMasterService, 'compareFarmerTypeMaster');
        comp.compareFarmerTypeMaster(entity, entity2);
        expect(farmerTypeMasterService.compareFarmerTypeMaster).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareCropHangam', () => {
      it('Should forward to cropHangamService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(cropHangamService, 'compareCropHangam');
        comp.compareCropHangam(entity, entity2);
        expect(cropHangamService.compareCropHangam).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareCropMaster', () => {
      it('Should forward to cropMasterService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(cropMasterService, 'compareCropMaster');
        comp.compareCropMaster(entity, entity2);
        expect(cropMasterService.compareCropMaster).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareKamalSociety', () => {
      it('Should forward to kamalSocietyService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(kamalSocietyService, 'compareKamalSociety');
        comp.compareKamalSociety(entity, entity2);
        expect(kamalSocietyService.compareKamalSociety).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
