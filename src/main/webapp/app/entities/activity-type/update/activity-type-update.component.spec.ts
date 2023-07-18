import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ActivityTypeFormService } from './activity-type-form.service';
import { ActivityTypeService } from '../service/activity-type.service';
import { IActivityType } from '../activity-type.model';

import { ActivityTypeUpdateComponent } from './activity-type-update.component';

describe('ActivityType Management Update Component', () => {
  let comp: ActivityTypeUpdateComponent;
  let fixture: ComponentFixture<ActivityTypeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let activityTypeFormService: ActivityTypeFormService;
  let activityTypeService: ActivityTypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ActivityTypeUpdateComponent],
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
      .overrideTemplate(ActivityTypeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ActivityTypeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    activityTypeFormService = TestBed.inject(ActivityTypeFormService);
    activityTypeService = TestBed.inject(ActivityTypeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const activityType: IActivityType = { id: 456 };

      activatedRoute.data = of({ activityType });
      comp.ngOnInit();

      expect(comp.activityType).toEqual(activityType);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IActivityType>>();
      const activityType = { id: 123 };
      jest.spyOn(activityTypeFormService, 'getActivityType').mockReturnValue(activityType);
      jest.spyOn(activityTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ activityType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: activityType }));
      saveSubject.complete();

      // THEN
      expect(activityTypeFormService.getActivityType).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(activityTypeService.update).toHaveBeenCalledWith(expect.objectContaining(activityType));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IActivityType>>();
      const activityType = { id: 123 };
      jest.spyOn(activityTypeFormService, 'getActivityType').mockReturnValue({ id: null });
      jest.spyOn(activityTypeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ activityType: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: activityType }));
      saveSubject.complete();

      // THEN
      expect(activityTypeFormService.getActivityType).toHaveBeenCalled();
      expect(activityTypeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IActivityType>>();
      const activityType = { id: 123 };
      jest.spyOn(activityTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ activityType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(activityTypeService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
