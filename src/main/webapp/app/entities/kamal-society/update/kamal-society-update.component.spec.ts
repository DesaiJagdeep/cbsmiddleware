import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { KamalSocietyFormService } from './kamal-society-form.service';
import { KamalSocietyService } from '../service/kamal-society.service';
import { IKamalSociety } from '../kamal-society.model';

import { KamalSocietyUpdateComponent } from './kamal-society-update.component';

describe('KamalSociety Management Update Component', () => {
  let comp: KamalSocietyUpdateComponent;
  let fixture: ComponentFixture<KamalSocietyUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let kamalSocietyFormService: KamalSocietyFormService;
  let kamalSocietyService: KamalSocietyService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [KamalSocietyUpdateComponent],
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
      .overrideTemplate(KamalSocietyUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(KamalSocietyUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    kamalSocietyFormService = TestBed.inject(KamalSocietyFormService);
    kamalSocietyService = TestBed.inject(KamalSocietyService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const kamalSociety: IKamalSociety = { id: 456 };

      activatedRoute.data = of({ kamalSociety });
      comp.ngOnInit();

      expect(comp.kamalSociety).toEqual(kamalSociety);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IKamalSociety>>();
      const kamalSociety = { id: 123 };
      jest.spyOn(kamalSocietyFormService, 'getKamalSociety').mockReturnValue(kamalSociety);
      jest.spyOn(kamalSocietyService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ kamalSociety });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: kamalSociety }));
      saveSubject.complete();

      // THEN
      expect(kamalSocietyFormService.getKamalSociety).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(kamalSocietyService.update).toHaveBeenCalledWith(expect.objectContaining(kamalSociety));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IKamalSociety>>();
      const kamalSociety = { id: 123 };
      jest.spyOn(kamalSocietyFormService, 'getKamalSociety').mockReturnValue({ id: null });
      jest.spyOn(kamalSocietyService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ kamalSociety: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: kamalSociety }));
      saveSubject.complete();

      // THEN
      expect(kamalSocietyFormService.getKamalSociety).toHaveBeenCalled();
      expect(kamalSocietyService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IKamalSociety>>();
      const kamalSociety = { id: 123 };
      jest.spyOn(kamalSocietyService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ kamalSociety });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(kamalSocietyService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
