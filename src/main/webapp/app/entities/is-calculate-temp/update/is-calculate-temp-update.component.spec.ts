import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IsCalculateTempFormService } from './is-calculate-temp-form.service';
import { IsCalculateTempService } from '../service/is-calculate-temp.service';
import { IIsCalculateTemp } from '../is-calculate-temp.model';

import { IsCalculateTempUpdateComponent } from './is-calculate-temp-update.component';

describe('IsCalculateTemp Management Update Component', () => {
  let comp: IsCalculateTempUpdateComponent;
  let fixture: ComponentFixture<IsCalculateTempUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let isCalculateTempFormService: IsCalculateTempFormService;
  let isCalculateTempService: IsCalculateTempService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [IsCalculateTempUpdateComponent],
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
      .overrideTemplate(IsCalculateTempUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(IsCalculateTempUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    isCalculateTempFormService = TestBed.inject(IsCalculateTempFormService);
    isCalculateTempService = TestBed.inject(IsCalculateTempService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const isCalculateTemp: IIsCalculateTemp = { id: 456 };

      activatedRoute.data = of({ isCalculateTemp });
      comp.ngOnInit();

      expect(comp.isCalculateTemp).toEqual(isCalculateTemp);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IIsCalculateTemp>>();
      const isCalculateTemp = { id: 123 };
      jest.spyOn(isCalculateTempFormService, 'getIsCalculateTemp').mockReturnValue(isCalculateTemp);
      jest.spyOn(isCalculateTempService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ isCalculateTemp });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: isCalculateTemp }));
      saveSubject.complete();

      // THEN
      expect(isCalculateTempFormService.getIsCalculateTemp).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(isCalculateTempService.update).toHaveBeenCalledWith(expect.objectContaining(isCalculateTemp));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IIsCalculateTemp>>();
      const isCalculateTemp = { id: 123 };
      jest.spyOn(isCalculateTempFormService, 'getIsCalculateTemp').mockReturnValue({ id: null });
      jest.spyOn(isCalculateTempService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ isCalculateTemp: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: isCalculateTemp }));
      saveSubject.complete();

      // THEN
      expect(isCalculateTempFormService.getIsCalculateTemp).toHaveBeenCalled();
      expect(isCalculateTempService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IIsCalculateTemp>>();
      const isCalculateTemp = { id: 123 };
      jest.spyOn(isCalculateTempService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ isCalculateTemp });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(isCalculateTempService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
