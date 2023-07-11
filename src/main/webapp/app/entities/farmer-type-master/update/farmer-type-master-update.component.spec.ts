import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { FarmerTypeMasterFormService } from './farmer-type-master-form.service';
import { FarmerTypeMasterService } from '../service/farmer-type-master.service';
import { IFarmerTypeMaster } from '../farmer-type-master.model';

import { FarmerTypeMasterUpdateComponent } from './farmer-type-master-update.component';

describe('FarmerTypeMaster Management Update Component', () => {
  let comp: FarmerTypeMasterUpdateComponent;
  let fixture: ComponentFixture<FarmerTypeMasterUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let farmerTypeMasterFormService: FarmerTypeMasterFormService;
  let farmerTypeMasterService: FarmerTypeMasterService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [FarmerTypeMasterUpdateComponent],
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
      .overrideTemplate(FarmerTypeMasterUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FarmerTypeMasterUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    farmerTypeMasterFormService = TestBed.inject(FarmerTypeMasterFormService);
    farmerTypeMasterService = TestBed.inject(FarmerTypeMasterService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const farmerTypeMaster: IFarmerTypeMaster = { id: 456 };

      activatedRoute.data = of({ farmerTypeMaster });
      comp.ngOnInit();

      expect(comp.farmerTypeMaster).toEqual(farmerTypeMaster);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFarmerTypeMaster>>();
      const farmerTypeMaster = { id: 123 };
      jest.spyOn(farmerTypeMasterFormService, 'getFarmerTypeMaster').mockReturnValue(farmerTypeMaster);
      jest.spyOn(farmerTypeMasterService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ farmerTypeMaster });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: farmerTypeMaster }));
      saveSubject.complete();

      // THEN
      expect(farmerTypeMasterFormService.getFarmerTypeMaster).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(farmerTypeMasterService.update).toHaveBeenCalledWith(expect.objectContaining(farmerTypeMaster));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFarmerTypeMaster>>();
      const farmerTypeMaster = { id: 123 };
      jest.spyOn(farmerTypeMasterFormService, 'getFarmerTypeMaster').mockReturnValue({ id: null });
      jest.spyOn(farmerTypeMasterService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ farmerTypeMaster: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: farmerTypeMaster }));
      saveSubject.complete();

      // THEN
      expect(farmerTypeMasterFormService.getFarmerTypeMaster).toHaveBeenCalled();
      expect(farmerTypeMasterService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFarmerTypeMaster>>();
      const farmerTypeMaster = { id: 123 };
      jest.spyOn(farmerTypeMasterService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ farmerTypeMaster });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(farmerTypeMasterService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
