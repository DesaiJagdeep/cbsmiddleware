import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { SeasonMasterFormService } from './season-master-form.service';
import { SeasonMasterService } from '../service/season-master.service';
import { ISeasonMaster } from '../season-master.model';

import { SeasonMasterUpdateComponent } from './season-master-update.component';

describe('SeasonMaster Management Update Component', () => {
  let comp: SeasonMasterUpdateComponent;
  let fixture: ComponentFixture<SeasonMasterUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let seasonMasterFormService: SeasonMasterFormService;
  let seasonMasterService: SeasonMasterService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [SeasonMasterUpdateComponent],
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
      .overrideTemplate(SeasonMasterUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SeasonMasterUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    seasonMasterFormService = TestBed.inject(SeasonMasterFormService);
    seasonMasterService = TestBed.inject(SeasonMasterService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const seasonMaster: ISeasonMaster = { id: 456 };

      activatedRoute.data = of({ seasonMaster });
      comp.ngOnInit();

      expect(comp.seasonMaster).toEqual(seasonMaster);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISeasonMaster>>();
      const seasonMaster = { id: 123 };
      jest.spyOn(seasonMasterFormService, 'getSeasonMaster').mockReturnValue(seasonMaster);
      jest.spyOn(seasonMasterService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ seasonMaster });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: seasonMaster }));
      saveSubject.complete();

      // THEN
      expect(seasonMasterFormService.getSeasonMaster).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(seasonMasterService.update).toHaveBeenCalledWith(expect.objectContaining(seasonMaster));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISeasonMaster>>();
      const seasonMaster = { id: 123 };
      jest.spyOn(seasonMasterFormService, 'getSeasonMaster').mockReturnValue({ id: null });
      jest.spyOn(seasonMasterService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ seasonMaster: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: seasonMaster }));
      saveSubject.complete();

      // THEN
      expect(seasonMasterFormService.getSeasonMaster).toHaveBeenCalled();
      expect(seasonMasterService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISeasonMaster>>();
      const seasonMaster = { id: 123 };
      jest.spyOn(seasonMasterService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ seasonMaster });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(seasonMasterService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
