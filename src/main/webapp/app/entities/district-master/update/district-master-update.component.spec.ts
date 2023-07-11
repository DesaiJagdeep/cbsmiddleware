import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DistrictMasterFormService } from './district-master-form.service';
import { DistrictMasterService } from '../service/district-master.service';
import { IDistrictMaster } from '../district-master.model';

import { DistrictMasterUpdateComponent } from './district-master-update.component';

describe('DistrictMaster Management Update Component', () => {
  let comp: DistrictMasterUpdateComponent;
  let fixture: ComponentFixture<DistrictMasterUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let districtMasterFormService: DistrictMasterFormService;
  let districtMasterService: DistrictMasterService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [DistrictMasterUpdateComponent],
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
      .overrideTemplate(DistrictMasterUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DistrictMasterUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    districtMasterFormService = TestBed.inject(DistrictMasterFormService);
    districtMasterService = TestBed.inject(DistrictMasterService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const districtMaster: IDistrictMaster = { id: 456 };

      activatedRoute.data = of({ districtMaster });
      comp.ngOnInit();

      expect(comp.districtMaster).toEqual(districtMaster);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDistrictMaster>>();
      const districtMaster = { id: 123 };
      jest.spyOn(districtMasterFormService, 'getDistrictMaster').mockReturnValue(districtMaster);
      jest.spyOn(districtMasterService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ districtMaster });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: districtMaster }));
      saveSubject.complete();

      // THEN
      expect(districtMasterFormService.getDistrictMaster).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(districtMasterService.update).toHaveBeenCalledWith(expect.objectContaining(districtMaster));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDistrictMaster>>();
      const districtMaster = { id: 123 };
      jest.spyOn(districtMasterFormService, 'getDistrictMaster').mockReturnValue({ id: null });
      jest.spyOn(districtMasterService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ districtMaster: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: districtMaster }));
      saveSubject.complete();

      // THEN
      expect(districtMasterFormService.getDistrictMaster).toHaveBeenCalled();
      expect(districtMasterService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDistrictMaster>>();
      const districtMaster = { id: 123 };
      jest.spyOn(districtMasterService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ districtMaster });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(districtMasterService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
