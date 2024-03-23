import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { KmMaganiFormService } from './km-magani-form.service';
import { KmMaganiService } from '../service/km-magani.service';
import { IKmMagani } from '../km-magani.model';

import { KmMaganiUpdateComponent } from './km-magani-update.component';

describe('KmMagani Management Update Component', () => {
  let comp: KmMaganiUpdateComponent;
  let fixture: ComponentFixture<KmMaganiUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let kmMaganiFormService: KmMaganiFormService;
  let kmMaganiService: KmMaganiService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [KmMaganiUpdateComponent],
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
      .overrideTemplate(KmMaganiUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(KmMaganiUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    kmMaganiFormService = TestBed.inject(KmMaganiFormService);
    kmMaganiService = TestBed.inject(KmMaganiService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const kmMagani: IKmMagani = { id: 456 };

      activatedRoute.data = of({ kmMagani });
      comp.ngOnInit();

      expect(comp.kmMagani).toEqual(kmMagani);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IKmMagani>>();
      const kmMagani = { id: 123 };
      jest.spyOn(kmMaganiFormService, 'getKmMagani').mockReturnValue(kmMagani);
      jest.spyOn(kmMaganiService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ kmMagani });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: kmMagani }));
      saveSubject.complete();

      // THEN
      expect(kmMaganiFormService.getKmMagani).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(kmMaganiService.update).toHaveBeenCalledWith(expect.objectContaining(kmMagani));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IKmMagani>>();
      const kmMagani = { id: 123 };
      jest.spyOn(kmMaganiFormService, 'getKmMagani').mockReturnValue({ id: null });
      jest.spyOn(kmMaganiService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ kmMagani: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: kmMagani }));
      saveSubject.complete();

      // THEN
      expect(kmMaganiFormService.getKmMagani).toHaveBeenCalled();
      expect(kmMaganiService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IKmMagani>>();
      const kmMagani = { id: 123 };
      jest.spyOn(kmMaganiService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ kmMagani });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(kmMaganiService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
