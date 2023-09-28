import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { KamalMaryadaPatrakFormService } from './kamal-maryada-patrak-form.service';
import { KamalMaryadaPatrakService } from '../service/kamal-maryada-patrak.service';
import { IKamalMaryadaPatrak } from '../kamal-maryada-patrak.model';

import { KamalMaryadaPatrakUpdateComponent } from './kamal-maryada-patrak-update.component';

describe('KamalMaryadaPatrak Management Update Component', () => {
  let comp: KamalMaryadaPatrakUpdateComponent;
  let fixture: ComponentFixture<KamalMaryadaPatrakUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let kamalMaryadaPatrakFormService: KamalMaryadaPatrakFormService;
  let kamalMaryadaPatrakService: KamalMaryadaPatrakService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [KamalMaryadaPatrakUpdateComponent],
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
      .overrideTemplate(KamalMaryadaPatrakUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(KamalMaryadaPatrakUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    kamalMaryadaPatrakFormService = TestBed.inject(KamalMaryadaPatrakFormService);
    kamalMaryadaPatrakService = TestBed.inject(KamalMaryadaPatrakService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const kamalMaryadaPatrak: IKamalMaryadaPatrak = { id: 456 };

      activatedRoute.data = of({ kamalMaryadaPatrak });
      comp.ngOnInit();

      expect(comp.kamalMaryadaPatrak).toEqual(kamalMaryadaPatrak);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IKamalMaryadaPatrak>>();
      const kamalMaryadaPatrak = { id: 123 };
      jest.spyOn(kamalMaryadaPatrakFormService, 'getKamalMaryadaPatrak').mockReturnValue(kamalMaryadaPatrak);
      jest.spyOn(kamalMaryadaPatrakService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ kamalMaryadaPatrak });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: kamalMaryadaPatrak }));
      saveSubject.complete();

      // THEN
      expect(kamalMaryadaPatrakFormService.getKamalMaryadaPatrak).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(kamalMaryadaPatrakService.update).toHaveBeenCalledWith(expect.objectContaining(kamalMaryadaPatrak));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IKamalMaryadaPatrak>>();
      const kamalMaryadaPatrak = { id: 123 };
      jest.spyOn(kamalMaryadaPatrakFormService, 'getKamalMaryadaPatrak').mockReturnValue({ id: null });
      jest.spyOn(kamalMaryadaPatrakService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ kamalMaryadaPatrak: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: kamalMaryadaPatrak }));
      saveSubject.complete();

      // THEN
      expect(kamalMaryadaPatrakFormService.getKamalMaryadaPatrak).toHaveBeenCalled();
      expect(kamalMaryadaPatrakService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IKamalMaryadaPatrak>>();
      const kamalMaryadaPatrak = { id: 123 };
      jest.spyOn(kamalMaryadaPatrakService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ kamalMaryadaPatrak });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(kamalMaryadaPatrakService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
