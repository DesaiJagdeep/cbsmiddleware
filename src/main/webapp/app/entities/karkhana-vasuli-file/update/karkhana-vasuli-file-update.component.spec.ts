import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IFactoryMaster } from 'app/entities/factory-master/factory-master.model';
import { FactoryMasterService } from 'app/entities/factory-master/service/factory-master.service';
import { KarkhanaVasuliFileService } from '../service/karkhana-vasuli-file.service';
import { IKarkhanaVasuliFile } from '../karkhana-vasuli-file.model';
import { KarkhanaVasuliFileFormService } from './karkhana-vasuli-file-form.service';

import { KarkhanaVasuliFileUpdateComponent } from './karkhana-vasuli-file-update.component';

describe('KarkhanaVasuliFile Management Update Component', () => {
  let comp: KarkhanaVasuliFileUpdateComponent;
  let fixture: ComponentFixture<KarkhanaVasuliFileUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let karkhanaVasuliFileFormService: KarkhanaVasuliFileFormService;
  let karkhanaVasuliFileService: KarkhanaVasuliFileService;
  let factoryMasterService: FactoryMasterService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), KarkhanaVasuliFileUpdateComponent],
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
      .overrideTemplate(KarkhanaVasuliFileUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(KarkhanaVasuliFileUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    karkhanaVasuliFileFormService = TestBed.inject(KarkhanaVasuliFileFormService);
    karkhanaVasuliFileService = TestBed.inject(KarkhanaVasuliFileService);
    factoryMasterService = TestBed.inject(FactoryMasterService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call factoryMaster query and add missing value', () => {
      const karkhanaVasuliFile: IKarkhanaVasuliFile = { id: 456 };
      const factoryMaster: IFactoryMaster = { id: 28627 };
      karkhanaVasuliFile.factoryMaster = factoryMaster;

      const factoryMasterCollection: IFactoryMaster[] = [{ id: 25066 }];
      jest.spyOn(factoryMasterService, 'query').mockReturnValue(of(new HttpResponse({ body: factoryMasterCollection })));
      const expectedCollection: IFactoryMaster[] = [factoryMaster, ...factoryMasterCollection];
      jest.spyOn(factoryMasterService, 'addFactoryMasterToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ karkhanaVasuliFile });
      comp.ngOnInit();

      expect(factoryMasterService.query).toHaveBeenCalled();
      expect(factoryMasterService.addFactoryMasterToCollectionIfMissing).toHaveBeenCalledWith(factoryMasterCollection, factoryMaster);
      expect(comp.factoryMastersCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const karkhanaVasuliFile: IKarkhanaVasuliFile = { id: 456 };
      const factoryMaster: IFactoryMaster = { id: 22972 };
      karkhanaVasuliFile.factoryMaster = factoryMaster;

      activatedRoute.data = of({ karkhanaVasuliFile });
      comp.ngOnInit();

      expect(comp.factoryMastersCollection).toContain(factoryMaster);
      expect(comp.karkhanaVasuliFile).toEqual(karkhanaVasuliFile);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IKarkhanaVasuliFile>>();
      const karkhanaVasuliFile = { id: 123 };
      jest.spyOn(karkhanaVasuliFileFormService, 'getKarkhanaVasuliFile').mockReturnValue(karkhanaVasuliFile);
      jest.spyOn(karkhanaVasuliFileService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ karkhanaVasuliFile });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: karkhanaVasuliFile }));
      saveSubject.complete();

      // THEN
      expect(karkhanaVasuliFileFormService.getKarkhanaVasuliFile).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(karkhanaVasuliFileService.update).toHaveBeenCalledWith(expect.objectContaining(karkhanaVasuliFile));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IKarkhanaVasuliFile>>();
      const karkhanaVasuliFile = { id: 123 };
      jest.spyOn(karkhanaVasuliFileFormService, 'getKarkhanaVasuliFile').mockReturnValue({ id: null });
      jest.spyOn(karkhanaVasuliFileService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ karkhanaVasuliFile: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: karkhanaVasuliFile }));
      saveSubject.complete();

      // THEN
      expect(karkhanaVasuliFileFormService.getKarkhanaVasuliFile).toHaveBeenCalled();
      expect(karkhanaVasuliFileService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IKarkhanaVasuliFile>>();
      const karkhanaVasuliFile = { id: 123 };
      jest.spyOn(karkhanaVasuliFileService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ karkhanaVasuliFile });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(karkhanaVasuliFileService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareFactoryMaster', () => {
      it('Should forward to factoryMasterService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(factoryMasterService, 'compareFactoryMaster');
        comp.compareFactoryMaster(entity, entity2);
        expect(factoryMasterService.compareFactoryMaster).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
