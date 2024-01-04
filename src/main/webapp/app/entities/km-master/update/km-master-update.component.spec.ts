import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { KmMasterService } from '../service/km-master.service';
import { IKmMaster } from '../km-master.model';
import { KmMasterFormService } from './km-master-form.service';

import { KmMasterUpdateComponent } from './km-master-update.component';

describe('KmMaster Management Update Component', () => {
  let comp: KmMasterUpdateComponent;
  let fixture: ComponentFixture<KmMasterUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let kmMasterFormService: KmMasterFormService;
  let kmMasterService: KmMasterService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), KmMasterUpdateComponent],
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
      .overrideTemplate(KmMasterUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(KmMasterUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    kmMasterFormService = TestBed.inject(KmMasterFormService);
    kmMasterService = TestBed.inject(KmMasterService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const kmMaster: IKmMaster = { id: 456 };

      activatedRoute.data = of({ kmMaster });
      comp.ngOnInit();

      expect(comp.kmMaster).toEqual(kmMaster);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IKmMaster>>();
      const kmMaster = { id: 123 };
      jest.spyOn(kmMasterFormService, 'getKmMaster').mockReturnValue(kmMaster);
      jest.spyOn(kmMasterService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ kmMaster });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: kmMaster }));
      saveSubject.complete();

      // THEN
      expect(kmMasterFormService.getKmMaster).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(kmMasterService.update).toHaveBeenCalledWith(expect.objectContaining(kmMaster));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IKmMaster>>();
      const kmMaster = { id: 123 };
      jest.spyOn(kmMasterFormService, 'getKmMaster').mockReturnValue({ id: null });
      jest.spyOn(kmMasterService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ kmMaster: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: kmMaster }));
      saveSubject.complete();

      // THEN
      expect(kmMasterFormService.getKmMaster).toHaveBeenCalled();
      expect(kmMasterService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IKmMaster>>();
      const kmMaster = { id: 123 };
      jest.spyOn(kmMasterService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ kmMaster });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(kmMasterService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});