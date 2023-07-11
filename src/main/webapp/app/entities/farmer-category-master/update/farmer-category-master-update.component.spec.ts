import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { FarmerCategoryMasterFormService } from './farmer-category-master-form.service';
import { FarmerCategoryMasterService } from '../service/farmer-category-master.service';
import { IFarmerCategoryMaster } from '../farmer-category-master.model';

import { FarmerCategoryMasterUpdateComponent } from './farmer-category-master-update.component';

describe('FarmerCategoryMaster Management Update Component', () => {
  let comp: FarmerCategoryMasterUpdateComponent;
  let fixture: ComponentFixture<FarmerCategoryMasterUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let farmerCategoryMasterFormService: FarmerCategoryMasterFormService;
  let farmerCategoryMasterService: FarmerCategoryMasterService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [FarmerCategoryMasterUpdateComponent],
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
      .overrideTemplate(FarmerCategoryMasterUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FarmerCategoryMasterUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    farmerCategoryMasterFormService = TestBed.inject(FarmerCategoryMasterFormService);
    farmerCategoryMasterService = TestBed.inject(FarmerCategoryMasterService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const farmerCategoryMaster: IFarmerCategoryMaster = { id: 456 };

      activatedRoute.data = of({ farmerCategoryMaster });
      comp.ngOnInit();

      expect(comp.farmerCategoryMaster).toEqual(farmerCategoryMaster);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFarmerCategoryMaster>>();
      const farmerCategoryMaster = { id: 123 };
      jest.spyOn(farmerCategoryMasterFormService, 'getFarmerCategoryMaster').mockReturnValue(farmerCategoryMaster);
      jest.spyOn(farmerCategoryMasterService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ farmerCategoryMaster });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: farmerCategoryMaster }));
      saveSubject.complete();

      // THEN
      expect(farmerCategoryMasterFormService.getFarmerCategoryMaster).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(farmerCategoryMasterService.update).toHaveBeenCalledWith(expect.objectContaining(farmerCategoryMaster));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFarmerCategoryMaster>>();
      const farmerCategoryMaster = { id: 123 };
      jest.spyOn(farmerCategoryMasterFormService, 'getFarmerCategoryMaster').mockReturnValue({ id: null });
      jest.spyOn(farmerCategoryMasterService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ farmerCategoryMaster: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: farmerCategoryMaster }));
      saveSubject.complete();

      // THEN
      expect(farmerCategoryMasterFormService.getFarmerCategoryMaster).toHaveBeenCalled();
      expect(farmerCategoryMasterService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFarmerCategoryMaster>>();
      const farmerCategoryMaster = { id: 123 };
      jest.spyOn(farmerCategoryMasterService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ farmerCategoryMaster });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(farmerCategoryMasterService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
