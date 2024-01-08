import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { FactoryMasterFormService } from './factory-master-form.service';
import { FactoryMasterService } from '../service/factory-master.service';
import { IFactoryMaster } from '../factory-master.model';

import { FactoryMasterUpdateComponent } from './factory-master-update.component';

describe('FactoryMaster Management Update Component', () => {
  let comp: FactoryMasterUpdateComponent;
  let fixture: ComponentFixture<FactoryMasterUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let factoryMasterFormService: FactoryMasterFormService;
  let factoryMasterService: FactoryMasterService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [FactoryMasterUpdateComponent],
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
      .overrideTemplate(FactoryMasterUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FactoryMasterUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    factoryMasterFormService = TestBed.inject(FactoryMasterFormService);
    factoryMasterService = TestBed.inject(FactoryMasterService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const factoryMaster: IFactoryMaster = { id: 456 };

      activatedRoute.data = of({ factoryMaster });
      comp.ngOnInit();

      expect(comp.factoryMaster).toEqual(factoryMaster);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFactoryMaster>>();
      const factoryMaster = { id: 123 };
      jest.spyOn(factoryMasterFormService, 'getFactoryMaster').mockReturnValue(factoryMaster);
      jest.spyOn(factoryMasterService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ factoryMaster });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: factoryMaster }));
      saveSubject.complete();

      // THEN
      expect(factoryMasterFormService.getFactoryMaster).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(factoryMasterService.update).toHaveBeenCalledWith(expect.objectContaining(factoryMaster));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFactoryMaster>>();
      const factoryMaster = { id: 123 };
      jest.spyOn(factoryMasterFormService, 'getFactoryMaster').mockReturnValue({ id: null });
      jest.spyOn(factoryMasterService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ factoryMaster: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: factoryMaster }));
      saveSubject.complete();

      // THEN
      expect(factoryMasterFormService.getFactoryMaster).toHaveBeenCalled();
      expect(factoryMasterService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFactoryMaster>>();
      const factoryMaster = { id: 123 };
      jest.spyOn(factoryMasterService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ factoryMaster });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(factoryMasterService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
