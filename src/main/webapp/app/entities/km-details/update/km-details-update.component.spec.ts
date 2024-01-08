import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IKmMaster } from 'app/entities/km-master/km-master.model';
import { KmMasterService } from 'app/entities/km-master/service/km-master.service';
import { KmDetailsService } from '../service/km-details.service';
import { IKmDetails } from '../km-details.model';
import { KmDetailsFormService } from './km-details-form.service';

import { KmDetailsUpdateComponent } from './km-details-update.component';

describe('KmDetails Management Update Component', () => {
  let comp: KmDetailsUpdateComponent;
  let fixture: ComponentFixture<KmDetailsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let kmDetailsFormService: KmDetailsFormService;
  let kmDetailsService: KmDetailsService;
  let kmMasterService: KmMasterService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), KmDetailsUpdateComponent],
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
      .overrideTemplate(KmDetailsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(KmDetailsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    kmDetailsFormService = TestBed.inject(KmDetailsFormService);
    kmDetailsService = TestBed.inject(KmDetailsService);
    kmMasterService = TestBed.inject(KmMasterService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call KmMaster query and add missing value', () => {
      const kmDetails: IKmDetails = { id: 456 };
      const kmMaster: IKmMaster = { id: 27717 };
      kmDetails.kmMaster = kmMaster;

      const kmMasterCollection: IKmMaster[] = [{ id: 11786 }];
      jest.spyOn(kmMasterService, 'query').mockReturnValue(of(new HttpResponse({ body: kmMasterCollection })));
      const additionalKmMasters = [kmMaster];
      const expectedCollection: IKmMaster[] = [...additionalKmMasters, ...kmMasterCollection];
      jest.spyOn(kmMasterService, 'addKmMasterToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ kmDetails });
      comp.ngOnInit();

      expect(kmMasterService.query).toHaveBeenCalled();
      expect(kmMasterService.addKmMasterToCollectionIfMissing).toHaveBeenCalledWith(
        kmMasterCollection,
        ...additionalKmMasters.map(expect.objectContaining),
      );
      expect(comp.kmMastersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const kmDetails: IKmDetails = { id: 456 };
      const kmMaster: IKmMaster = { id: 14759 };
      kmDetails.kmMaster = kmMaster;

      activatedRoute.data = of({ kmDetails });
      comp.ngOnInit();

      expect(comp.kmMastersSharedCollection).toContain(kmMaster);
      expect(comp.kmDetails).toEqual(kmDetails);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IKmDetails>>();
      const kmDetails = { id: 123 };
      jest.spyOn(kmDetailsFormService, 'getKmDetails').mockReturnValue(kmDetails);
      jest.spyOn(kmDetailsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ kmDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: kmDetails }));
      saveSubject.complete();

      // THEN
      expect(kmDetailsFormService.getKmDetails).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(kmDetailsService.update).toHaveBeenCalledWith(expect.objectContaining(kmDetails));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IKmDetails>>();
      const kmDetails = { id: 123 };
      jest.spyOn(kmDetailsFormService, 'getKmDetails').mockReturnValue({ id: null });
      jest.spyOn(kmDetailsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ kmDetails: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: kmDetails }));
      saveSubject.complete();

      // THEN
      expect(kmDetailsFormService.getKmDetails).toHaveBeenCalled();
      expect(kmDetailsService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IKmDetails>>();
      const kmDetails = { id: 123 };
      jest.spyOn(kmDetailsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ kmDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(kmDetailsService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareKmMaster', () => {
      it('Should forward to kmMasterService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(kmMasterService, 'compareKmMaster');
        comp.compareKmMaster(entity, entity2);
        expect(kmMasterService.compareKmMaster).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
