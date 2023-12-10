import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ITalukaMaster } from 'app/entities/taluka-master/taluka-master.model';
import { TalukaMasterService } from 'app/entities/taluka-master/service/taluka-master.service';
import { VillageMasterService } from '../service/village-master.service';
import { IVillageMaster } from '../village-master.model';
import { VillageMasterFormService } from './village-master-form.service';

import { VillageMasterUpdateComponent } from './village-master-update.component';

describe('VillageMaster Management Update Component', () => {
  let comp: VillageMasterUpdateComponent;
  let fixture: ComponentFixture<VillageMasterUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let villageMasterFormService: VillageMasterFormService;
  let villageMasterService: VillageMasterService;
  let talukaMasterService: TalukaMasterService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), VillageMasterUpdateComponent],
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
      .overrideTemplate(VillageMasterUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(VillageMasterUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    villageMasterFormService = TestBed.inject(VillageMasterFormService);
    villageMasterService = TestBed.inject(VillageMasterService);
    talukaMasterService = TestBed.inject(TalukaMasterService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call talukaMaster query and add missing value', () => {
      const villageMaster: IVillageMaster = { id: 456 };
      const talukaMaster: ITalukaMaster = { id: 14817 };
      villageMaster.talukaMaster = talukaMaster;

      const talukaMasterCollection: ITalukaMaster[] = [{ id: 27593 }];
      jest.spyOn(talukaMasterService, 'query').mockReturnValue(of(new HttpResponse({ body: talukaMasterCollection })));
      const expectedCollection: ITalukaMaster[] = [talukaMaster, ...talukaMasterCollection];
      jest.spyOn(talukaMasterService, 'addTalukaMasterToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ villageMaster });
      comp.ngOnInit();

      expect(talukaMasterService.query).toHaveBeenCalled();
      expect(talukaMasterService.addTalukaMasterToCollectionIfMissing).toHaveBeenCalledWith(talukaMasterCollection, talukaMaster);
      expect(comp.talukaMastersCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const villageMaster: IVillageMaster = { id: 456 };
      const talukaMaster: ITalukaMaster = { id: 25694 };
      villageMaster.talukaMaster = talukaMaster;

      activatedRoute.data = of({ villageMaster });
      comp.ngOnInit();

      expect(comp.talukaMastersCollection).toContain(talukaMaster);
      expect(comp.villageMaster).toEqual(villageMaster);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IVillageMaster>>();
      const villageMaster = { id: 123 };
      jest.spyOn(villageMasterFormService, 'getVillageMaster').mockReturnValue(villageMaster);
      jest.spyOn(villageMasterService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ villageMaster });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: villageMaster }));
      saveSubject.complete();

      // THEN
      expect(villageMasterFormService.getVillageMaster).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(villageMasterService.update).toHaveBeenCalledWith(expect.objectContaining(villageMaster));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IVillageMaster>>();
      const villageMaster = { id: 123 };
      jest.spyOn(villageMasterFormService, 'getVillageMaster').mockReturnValue({ id: null });
      jest.spyOn(villageMasterService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ villageMaster: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: villageMaster }));
      saveSubject.complete();

      // THEN
      expect(villageMasterFormService.getVillageMaster).toHaveBeenCalled();
      expect(villageMasterService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IVillageMaster>>();
      const villageMaster = { id: 123 };
      jest.spyOn(villageMasterService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ villageMaster });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(villageMasterService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareTalukaMaster', () => {
      it('Should forward to talukaMasterService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(talukaMasterService, 'compareTalukaMaster');
        comp.compareTalukaMaster(entity, entity2);
        expect(talukaMasterService.compareTalukaMaster).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
