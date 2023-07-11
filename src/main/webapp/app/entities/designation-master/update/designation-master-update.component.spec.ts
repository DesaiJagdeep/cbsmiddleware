import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DesignationMasterFormService } from './designation-master-form.service';
import { DesignationMasterService } from '../service/designation-master.service';
import { IDesignationMaster } from '../designation-master.model';

import { DesignationMasterUpdateComponent } from './designation-master-update.component';

describe('DesignationMaster Management Update Component', () => {
  let comp: DesignationMasterUpdateComponent;
  let fixture: ComponentFixture<DesignationMasterUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let designationMasterFormService: DesignationMasterFormService;
  let designationMasterService: DesignationMasterService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [DesignationMasterUpdateComponent],
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
      .overrideTemplate(DesignationMasterUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DesignationMasterUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    designationMasterFormService = TestBed.inject(DesignationMasterFormService);
    designationMasterService = TestBed.inject(DesignationMasterService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const designationMaster: IDesignationMaster = { id: 456 };

      activatedRoute.data = of({ designationMaster });
      comp.ngOnInit();

      expect(comp.designationMaster).toEqual(designationMaster);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDesignationMaster>>();
      const designationMaster = { id: 123 };
      jest.spyOn(designationMasterFormService, 'getDesignationMaster').mockReturnValue(designationMaster);
      jest.spyOn(designationMasterService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ designationMaster });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: designationMaster }));
      saveSubject.complete();

      // THEN
      expect(designationMasterFormService.getDesignationMaster).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(designationMasterService.update).toHaveBeenCalledWith(expect.objectContaining(designationMaster));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDesignationMaster>>();
      const designationMaster = { id: 123 };
      jest.spyOn(designationMasterFormService, 'getDesignationMaster').mockReturnValue({ id: null });
      jest.spyOn(designationMasterService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ designationMaster: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: designationMaster }));
      saveSubject.complete();

      // THEN
      expect(designationMasterFormService.getDesignationMaster).toHaveBeenCalled();
      expect(designationMasterService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDesignationMaster>>();
      const designationMaster = { id: 123 };
      jest.spyOn(designationMasterService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ designationMaster });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(designationMasterService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
