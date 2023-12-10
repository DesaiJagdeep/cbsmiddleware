import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IKarkhanaVasuliFile } from 'app/entities/karkhana-vasuli-file/karkhana-vasuli-file.model';
import { KarkhanaVasuliFileService } from 'app/entities/karkhana-vasuli-file/service/karkhana-vasuli-file.service';
import { KarkhanaVasuliRecordsService } from '../service/karkhana-vasuli-records.service';
import { IKarkhanaVasuliRecords } from '../karkhana-vasuli-records.model';
import { KarkhanaVasuliRecordsFormService } from './karkhana-vasuli-records-form.service';

import { KarkhanaVasuliRecordsUpdateComponent } from './karkhana-vasuli-records-update.component';

describe('KarkhanaVasuliRecords Management Update Component', () => {
  let comp: KarkhanaVasuliRecordsUpdateComponent;
  let fixture: ComponentFixture<KarkhanaVasuliRecordsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let karkhanaVasuliRecordsFormService: KarkhanaVasuliRecordsFormService;
  let karkhanaVasuliRecordsService: KarkhanaVasuliRecordsService;
  let karkhanaVasuliFileService: KarkhanaVasuliFileService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), KarkhanaVasuliRecordsUpdateComponent],
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
      .overrideTemplate(KarkhanaVasuliRecordsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(KarkhanaVasuliRecordsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    karkhanaVasuliRecordsFormService = TestBed.inject(KarkhanaVasuliRecordsFormService);
    karkhanaVasuliRecordsService = TestBed.inject(KarkhanaVasuliRecordsService);
    karkhanaVasuliFileService = TestBed.inject(KarkhanaVasuliFileService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call KarkhanaVasuliFile query and add missing value', () => {
      const karkhanaVasuliRecords: IKarkhanaVasuliRecords = { id: 456 };
      const karkhanaVasuliFile: IKarkhanaVasuliFile = { id: 21809 };
      karkhanaVasuliRecords.karkhanaVasuliFile = karkhanaVasuliFile;

      const karkhanaVasuliFileCollection: IKarkhanaVasuliFile[] = [{ id: 30655 }];
      jest.spyOn(karkhanaVasuliFileService, 'query').mockReturnValue(of(new HttpResponse({ body: karkhanaVasuliFileCollection })));
      const additionalKarkhanaVasuliFiles = [karkhanaVasuliFile];
      const expectedCollection: IKarkhanaVasuliFile[] = [...additionalKarkhanaVasuliFiles, ...karkhanaVasuliFileCollection];
      jest.spyOn(karkhanaVasuliFileService, 'addKarkhanaVasuliFileToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ karkhanaVasuliRecords });
      comp.ngOnInit();

      expect(karkhanaVasuliFileService.query).toHaveBeenCalled();
      expect(karkhanaVasuliFileService.addKarkhanaVasuliFileToCollectionIfMissing).toHaveBeenCalledWith(
        karkhanaVasuliFileCollection,
        ...additionalKarkhanaVasuliFiles.map(expect.objectContaining),
      );
      expect(comp.karkhanaVasuliFilesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const karkhanaVasuliRecords: IKarkhanaVasuliRecords = { id: 456 };
      const karkhanaVasuliFile: IKarkhanaVasuliFile = { id: 15096 };
      karkhanaVasuliRecords.karkhanaVasuliFile = karkhanaVasuliFile;

      activatedRoute.data = of({ karkhanaVasuliRecords });
      comp.ngOnInit();

      expect(comp.karkhanaVasuliFilesSharedCollection).toContain(karkhanaVasuliFile);
      expect(comp.karkhanaVasuliRecords).toEqual(karkhanaVasuliRecords);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IKarkhanaVasuliRecords>>();
      const karkhanaVasuliRecords = { id: 123 };
      jest.spyOn(karkhanaVasuliRecordsFormService, 'getKarkhanaVasuliRecords').mockReturnValue(karkhanaVasuliRecords);
      jest.spyOn(karkhanaVasuliRecordsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ karkhanaVasuliRecords });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: karkhanaVasuliRecords }));
      saveSubject.complete();

      // THEN
      expect(karkhanaVasuliRecordsFormService.getKarkhanaVasuliRecords).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(karkhanaVasuliRecordsService.update).toHaveBeenCalledWith(expect.objectContaining(karkhanaVasuliRecords));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IKarkhanaVasuliRecords>>();
      const karkhanaVasuliRecords = { id: 123 };
      jest.spyOn(karkhanaVasuliRecordsFormService, 'getKarkhanaVasuliRecords').mockReturnValue({ id: null });
      jest.spyOn(karkhanaVasuliRecordsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ karkhanaVasuliRecords: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: karkhanaVasuliRecords }));
      saveSubject.complete();

      // THEN
      expect(karkhanaVasuliRecordsFormService.getKarkhanaVasuliRecords).toHaveBeenCalled();
      expect(karkhanaVasuliRecordsService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IKarkhanaVasuliRecords>>();
      const karkhanaVasuliRecords = { id: 123 };
      jest.spyOn(karkhanaVasuliRecordsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ karkhanaVasuliRecords });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(karkhanaVasuliRecordsService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareKarkhanaVasuliFile', () => {
      it('Should forward to karkhanaVasuliFileService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(karkhanaVasuliFileService, 'compareKarkhanaVasuliFile');
        comp.compareKarkhanaVasuliFile(entity, entity2);
        expect(karkhanaVasuliFileService.compareKarkhanaVasuliFile).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
