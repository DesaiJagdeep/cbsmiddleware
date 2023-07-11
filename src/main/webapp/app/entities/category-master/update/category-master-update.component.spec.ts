import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CategoryMasterFormService } from './category-master-form.service';
import { CategoryMasterService } from '../service/category-master.service';
import { ICategoryMaster } from '../category-master.model';

import { CategoryMasterUpdateComponent } from './category-master-update.component';

describe('CategoryMaster Management Update Component', () => {
  let comp: CategoryMasterUpdateComponent;
  let fixture: ComponentFixture<CategoryMasterUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let categoryMasterFormService: CategoryMasterFormService;
  let categoryMasterService: CategoryMasterService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CategoryMasterUpdateComponent],
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
      .overrideTemplate(CategoryMasterUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CategoryMasterUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    categoryMasterFormService = TestBed.inject(CategoryMasterFormService);
    categoryMasterService = TestBed.inject(CategoryMasterService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const categoryMaster: ICategoryMaster = { id: 456 };

      activatedRoute.data = of({ categoryMaster });
      comp.ngOnInit();

      expect(comp.categoryMaster).toEqual(categoryMaster);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICategoryMaster>>();
      const categoryMaster = { id: 123 };
      jest.spyOn(categoryMasterFormService, 'getCategoryMaster').mockReturnValue(categoryMaster);
      jest.spyOn(categoryMasterService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ categoryMaster });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: categoryMaster }));
      saveSubject.complete();

      // THEN
      expect(categoryMasterFormService.getCategoryMaster).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(categoryMasterService.update).toHaveBeenCalledWith(expect.objectContaining(categoryMaster));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICategoryMaster>>();
      const categoryMaster = { id: 123 };
      jest.spyOn(categoryMasterFormService, 'getCategoryMaster').mockReturnValue({ id: null });
      jest.spyOn(categoryMasterService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ categoryMaster: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: categoryMaster }));
      saveSubject.complete();

      // THEN
      expect(categoryMasterFormService.getCategoryMaster).toHaveBeenCalled();
      expect(categoryMasterService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICategoryMaster>>();
      const categoryMaster = { id: 123 };
      jest.spyOn(categoryMasterService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ categoryMaster });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(categoryMasterService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
