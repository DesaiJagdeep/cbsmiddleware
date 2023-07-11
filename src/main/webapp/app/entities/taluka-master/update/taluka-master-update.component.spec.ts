import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TalukaMasterFormService } from './taluka-master-form.service';
import { TalukaMasterService } from '../service/taluka-master.service';
import { ITalukaMaster } from '../taluka-master.model';

import { TalukaMasterUpdateComponent } from './taluka-master-update.component';

describe('TalukaMaster Management Update Component', () => {
  let comp: TalukaMasterUpdateComponent;
  let fixture: ComponentFixture<TalukaMasterUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let talukaMasterFormService: TalukaMasterFormService;
  let talukaMasterService: TalukaMasterService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [TalukaMasterUpdateComponent],
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
      .overrideTemplate(TalukaMasterUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TalukaMasterUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    talukaMasterFormService = TestBed.inject(TalukaMasterFormService);
    talukaMasterService = TestBed.inject(TalukaMasterService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const talukaMaster: ITalukaMaster = { id: 456 };

      activatedRoute.data = of({ talukaMaster });
      comp.ngOnInit();

      expect(comp.talukaMaster).toEqual(talukaMaster);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITalukaMaster>>();
      const talukaMaster = { id: 123 };
      jest.spyOn(talukaMasterFormService, 'getTalukaMaster').mockReturnValue(talukaMaster);
      jest.spyOn(talukaMasterService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ talukaMaster });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: talukaMaster }));
      saveSubject.complete();

      // THEN
      expect(talukaMasterFormService.getTalukaMaster).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(talukaMasterService.update).toHaveBeenCalledWith(expect.objectContaining(talukaMaster));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITalukaMaster>>();
      const talukaMaster = { id: 123 };
      jest.spyOn(talukaMasterFormService, 'getTalukaMaster').mockReturnValue({ id: null });
      jest.spyOn(talukaMasterService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ talukaMaster: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: talukaMaster }));
      saveSubject.complete();

      // THEN
      expect(talukaMasterFormService.getTalukaMaster).toHaveBeenCalled();
      expect(talukaMasterService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITalukaMaster>>();
      const talukaMaster = { id: 123 };
      jest.spyOn(talukaMasterService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ talukaMaster });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(talukaMasterService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
