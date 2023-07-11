import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { LandTypeMasterFormService } from './land-type-master-form.service';
import { LandTypeMasterService } from '../service/land-type-master.service';
import { ILandTypeMaster } from '../land-type-master.model';

import { LandTypeMasterUpdateComponent } from './land-type-master-update.component';

describe('LandTypeMaster Management Update Component', () => {
  let comp: LandTypeMasterUpdateComponent;
  let fixture: ComponentFixture<LandTypeMasterUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let landTypeMasterFormService: LandTypeMasterFormService;
  let landTypeMasterService: LandTypeMasterService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [LandTypeMasterUpdateComponent],
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
      .overrideTemplate(LandTypeMasterUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(LandTypeMasterUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    landTypeMasterFormService = TestBed.inject(LandTypeMasterFormService);
    landTypeMasterService = TestBed.inject(LandTypeMasterService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const landTypeMaster: ILandTypeMaster = { id: 456 };

      activatedRoute.data = of({ landTypeMaster });
      comp.ngOnInit();

      expect(comp.landTypeMaster).toEqual(landTypeMaster);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILandTypeMaster>>();
      const landTypeMaster = { id: 123 };
      jest.spyOn(landTypeMasterFormService, 'getLandTypeMaster').mockReturnValue(landTypeMaster);
      jest.spyOn(landTypeMasterService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ landTypeMaster });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: landTypeMaster }));
      saveSubject.complete();

      // THEN
      expect(landTypeMasterFormService.getLandTypeMaster).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(landTypeMasterService.update).toHaveBeenCalledWith(expect.objectContaining(landTypeMaster));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILandTypeMaster>>();
      const landTypeMaster = { id: 123 };
      jest.spyOn(landTypeMasterFormService, 'getLandTypeMaster').mockReturnValue({ id: null });
      jest.spyOn(landTypeMasterService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ landTypeMaster: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: landTypeMaster }));
      saveSubject.complete();

      // THEN
      expect(landTypeMasterFormService.getLandTypeMaster).toHaveBeenCalled();
      expect(landTypeMasterService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILandTypeMaster>>();
      const landTypeMaster = { id: 123 };
      jest.spyOn(landTypeMasterService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ landTypeMaster });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(landTypeMasterService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
