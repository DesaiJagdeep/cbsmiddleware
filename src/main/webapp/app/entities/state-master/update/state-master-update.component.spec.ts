import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { StateMasterFormService } from './state-master-form.service';
import { StateMasterService } from '../service/state-master.service';
import { IStateMaster } from '../state-master.model';

import { StateMasterUpdateComponent } from './state-master-update.component';

describe('StateMaster Management Update Component', () => {
  let comp: StateMasterUpdateComponent;
  let fixture: ComponentFixture<StateMasterUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let stateMasterFormService: StateMasterFormService;
  let stateMasterService: StateMasterService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [StateMasterUpdateComponent],
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
      .overrideTemplate(StateMasterUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(StateMasterUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    stateMasterFormService = TestBed.inject(StateMasterFormService);
    stateMasterService = TestBed.inject(StateMasterService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const stateMaster: IStateMaster = { id: 456 };

      activatedRoute.data = of({ stateMaster });
      comp.ngOnInit();

      expect(comp.stateMaster).toEqual(stateMaster);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IStateMaster>>();
      const stateMaster = { id: 123 };
      jest.spyOn(stateMasterFormService, 'getStateMaster').mockReturnValue(stateMaster);
      jest.spyOn(stateMasterService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ stateMaster });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: stateMaster }));
      saveSubject.complete();

      // THEN
      expect(stateMasterFormService.getStateMaster).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(stateMasterService.update).toHaveBeenCalledWith(expect.objectContaining(stateMaster));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IStateMaster>>();
      const stateMaster = { id: 123 };
      jest.spyOn(stateMasterFormService, 'getStateMaster').mockReturnValue({ id: null });
      jest.spyOn(stateMasterService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ stateMaster: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: stateMaster }));
      saveSubject.complete();

      // THEN
      expect(stateMasterFormService.getStateMaster).toHaveBeenCalled();
      expect(stateMasterService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IStateMaster>>();
      const stateMaster = { id: 123 };
      jest.spyOn(stateMasterService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ stateMaster });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(stateMasterService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
