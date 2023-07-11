import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { RelativeMasterFormService } from './relative-master-form.service';
import { RelativeMasterService } from '../service/relative-master.service';
import { IRelativeMaster } from '../relative-master.model';

import { RelativeMasterUpdateComponent } from './relative-master-update.component';

describe('RelativeMaster Management Update Component', () => {
  let comp: RelativeMasterUpdateComponent;
  let fixture: ComponentFixture<RelativeMasterUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let relativeMasterFormService: RelativeMasterFormService;
  let relativeMasterService: RelativeMasterService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [RelativeMasterUpdateComponent],
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
      .overrideTemplate(RelativeMasterUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RelativeMasterUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    relativeMasterFormService = TestBed.inject(RelativeMasterFormService);
    relativeMasterService = TestBed.inject(RelativeMasterService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const relativeMaster: IRelativeMaster = { id: 456 };

      activatedRoute.data = of({ relativeMaster });
      comp.ngOnInit();

      expect(comp.relativeMaster).toEqual(relativeMaster);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRelativeMaster>>();
      const relativeMaster = { id: 123 };
      jest.spyOn(relativeMasterFormService, 'getRelativeMaster').mockReturnValue(relativeMaster);
      jest.spyOn(relativeMasterService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ relativeMaster });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: relativeMaster }));
      saveSubject.complete();

      // THEN
      expect(relativeMasterFormService.getRelativeMaster).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(relativeMasterService.update).toHaveBeenCalledWith(expect.objectContaining(relativeMaster));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRelativeMaster>>();
      const relativeMaster = { id: 123 };
      jest.spyOn(relativeMasterFormService, 'getRelativeMaster').mockReturnValue({ id: null });
      jest.spyOn(relativeMasterService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ relativeMaster: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: relativeMaster }));
      saveSubject.complete();

      // THEN
      expect(relativeMasterFormService.getRelativeMaster).toHaveBeenCalled();
      expect(relativeMasterService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRelativeMaster>>();
      const relativeMaster = { id: 123 };
      jest.spyOn(relativeMasterService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ relativeMaster });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(relativeMasterService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
