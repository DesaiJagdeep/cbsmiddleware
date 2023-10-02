import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { KarkhanaVasuliFormService } from './karkhana-vasuli-form.service';
import { KarkhanaVasuliService } from '../service/karkhana-vasuli.service';
import { IKarkhanaVasuli } from '../karkhana-vasuli.model';

import { KarkhanaVasuliUpdateComponent } from './karkhana-vasuli-update.component';

describe('KarkhanaVasuli Management Update Component', () => {
  let comp: KarkhanaVasuliUpdateComponent;
  let fixture: ComponentFixture<KarkhanaVasuliUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let karkhanaVasuliFormService: KarkhanaVasuliFormService;
  let karkhanaVasuliService: KarkhanaVasuliService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [KarkhanaVasuliUpdateComponent],
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
      .overrideTemplate(KarkhanaVasuliUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(KarkhanaVasuliUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    karkhanaVasuliFormService = TestBed.inject(KarkhanaVasuliFormService);
    karkhanaVasuliService = TestBed.inject(KarkhanaVasuliService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const karkhanaVasuli: IKarkhanaVasuli = { id: 456 };

      activatedRoute.data = of({ karkhanaVasuli });
      comp.ngOnInit();

      expect(comp.karkhanaVasuli).toEqual(karkhanaVasuli);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IKarkhanaVasuli>>();
      const karkhanaVasuli = { id: 123 };
      jest.spyOn(karkhanaVasuliFormService, 'getKarkhanaVasuli').mockReturnValue(karkhanaVasuli);
      jest.spyOn(karkhanaVasuliService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ karkhanaVasuli });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: karkhanaVasuli }));
      saveSubject.complete();

      // THEN
      expect(karkhanaVasuliFormService.getKarkhanaVasuli).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(karkhanaVasuliService.update).toHaveBeenCalledWith(expect.objectContaining(karkhanaVasuli));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IKarkhanaVasuli>>();
      const karkhanaVasuli = { id: 123 };
      jest.spyOn(karkhanaVasuliFormService, 'getKarkhanaVasuli').mockReturnValue({ id: null });
      jest.spyOn(karkhanaVasuliService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ karkhanaVasuli: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: karkhanaVasuli }));
      saveSubject.complete();

      // THEN
      expect(karkhanaVasuliFormService.getKarkhanaVasuli).toHaveBeenCalled();
      expect(karkhanaVasuliService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IKarkhanaVasuli>>();
      const karkhanaVasuli = { id: 123 };
      jest.spyOn(karkhanaVasuliService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ karkhanaVasuli });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(karkhanaVasuliService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
