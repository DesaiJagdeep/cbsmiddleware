import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CastCategoryMasterFormService } from './cast-category-master-form.service';
import { CastCategoryMasterService } from '../service/cast-category-master.service';
import { ICastCategoryMaster } from '../cast-category-master.model';

import { CastCategoryMasterUpdateComponent } from './cast-category-master-update.component';

describe('CastCategoryMaster Management Update Component', () => {
  let comp: CastCategoryMasterUpdateComponent;
  let fixture: ComponentFixture<CastCategoryMasterUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let castCategoryMasterFormService: CastCategoryMasterFormService;
  let castCategoryMasterService: CastCategoryMasterService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CastCategoryMasterUpdateComponent],
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
      .overrideTemplate(CastCategoryMasterUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CastCategoryMasterUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    castCategoryMasterFormService = TestBed.inject(CastCategoryMasterFormService);
    castCategoryMasterService = TestBed.inject(CastCategoryMasterService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const castCategoryMaster: ICastCategoryMaster = { id: 456 };

      activatedRoute.data = of({ castCategoryMaster });
      comp.ngOnInit();

      expect(comp.castCategoryMaster).toEqual(castCategoryMaster);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICastCategoryMaster>>();
      const castCategoryMaster = { id: 123 };
      jest.spyOn(castCategoryMasterFormService, 'getCastCategoryMaster').mockReturnValue(castCategoryMaster);
      jest.spyOn(castCategoryMasterService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ castCategoryMaster });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: castCategoryMaster }));
      saveSubject.complete();

      // THEN
      expect(castCategoryMasterFormService.getCastCategoryMaster).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(castCategoryMasterService.update).toHaveBeenCalledWith(expect.objectContaining(castCategoryMaster));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICastCategoryMaster>>();
      const castCategoryMaster = { id: 123 };
      jest.spyOn(castCategoryMasterFormService, 'getCastCategoryMaster').mockReturnValue({ id: null });
      jest.spyOn(castCategoryMasterService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ castCategoryMaster: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: castCategoryMaster }));
      saveSubject.complete();

      // THEN
      expect(castCategoryMasterFormService.getCastCategoryMaster).toHaveBeenCalled();
      expect(castCategoryMasterService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICastCategoryMaster>>();
      const castCategoryMaster = { id: 123 };
      jest.spyOn(castCategoryMasterService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ castCategoryMaster });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(castCategoryMasterService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
