import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { KmMaganiCropFormService } from './km-magani-crop-form.service';
import { KmMaganiCropService } from '../service/km-magani-crop.service';
import { IKmMaganiCrop } from '../km-magani-crop.model';
import { IKmMagani } from 'app/entities/km-magani/km-magani.model';
import { KmMaganiService } from 'app/entities/km-magani/service/km-magani.service';

import { KmMaganiCropUpdateComponent } from './km-magani-crop-update.component';

describe('KmMaganiCrop Management Update Component', () => {
  let comp: KmMaganiCropUpdateComponent;
  let fixture: ComponentFixture<KmMaganiCropUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let kmMaganiCropFormService: KmMaganiCropFormService;
  let kmMaganiCropService: KmMaganiCropService;
  let kmMaganiService: KmMaganiService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [KmMaganiCropUpdateComponent],
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
      .overrideTemplate(KmMaganiCropUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(KmMaganiCropUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    kmMaganiCropFormService = TestBed.inject(KmMaganiCropFormService);
    kmMaganiCropService = TestBed.inject(KmMaganiCropService);
    kmMaganiService = TestBed.inject(KmMaganiService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call KmMagani query and add missing value', () => {
      const kmMaganiCrop: IKmMaganiCrop = { id: 456 };
      const kmMagani: IKmMagani = { id: 75453 };
      kmMaganiCrop.kmMagani = kmMagani;

      const kmMaganiCollection: IKmMagani[] = [{ id: 19708 }];
      jest.spyOn(kmMaganiService, 'query').mockReturnValue(of(new HttpResponse({ body: kmMaganiCollection })));
      const additionalKmMaganis = [kmMagani];
      const expectedCollection: IKmMagani[] = [...additionalKmMaganis, ...kmMaganiCollection];
      jest.spyOn(kmMaganiService, 'addKmMaganiToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ kmMaganiCrop });
      comp.ngOnInit();

      expect(kmMaganiService.query).toHaveBeenCalled();
      expect(kmMaganiService.addKmMaganiToCollectionIfMissing).toHaveBeenCalledWith(
        kmMaganiCollection,
        ...additionalKmMaganis.map(expect.objectContaining)
      );
      expect(comp.kmMaganisSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const kmMaganiCrop: IKmMaganiCrop = { id: 456 };
      const kmMagani: IKmMagani = { id: 8361 };
      kmMaganiCrop.kmMagani = kmMagani;

      activatedRoute.data = of({ kmMaganiCrop });
      comp.ngOnInit();

      expect(comp.kmMaganisSharedCollection).toContain(kmMagani);
      expect(comp.kmMaganiCrop).toEqual(kmMaganiCrop);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IKmMaganiCrop>>();
      const kmMaganiCrop = { id: 123 };
      jest.spyOn(kmMaganiCropFormService, 'getKmMaganiCrop').mockReturnValue(kmMaganiCrop);
      jest.spyOn(kmMaganiCropService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ kmMaganiCrop });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: kmMaganiCrop }));
      saveSubject.complete();

      // THEN
      expect(kmMaganiCropFormService.getKmMaganiCrop).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(kmMaganiCropService.update).toHaveBeenCalledWith(expect.objectContaining(kmMaganiCrop));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IKmMaganiCrop>>();
      const kmMaganiCrop = { id: 123 };
      jest.spyOn(kmMaganiCropFormService, 'getKmMaganiCrop').mockReturnValue({ id: null });
      jest.spyOn(kmMaganiCropService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ kmMaganiCrop: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: kmMaganiCrop }));
      saveSubject.complete();

      // THEN
      expect(kmMaganiCropFormService.getKmMaganiCrop).toHaveBeenCalled();
      expect(kmMaganiCropService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IKmMaganiCrop>>();
      const kmMaganiCrop = { id: 123 };
      jest.spyOn(kmMaganiCropService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ kmMaganiCrop });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(kmMaganiCropService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareKmMagani', () => {
      it('Should forward to kmMaganiService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(kmMaganiService, 'compareKmMagani');
        comp.compareKmMagani(entity, entity2);
        expect(kmMaganiService.compareKmMagani).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
