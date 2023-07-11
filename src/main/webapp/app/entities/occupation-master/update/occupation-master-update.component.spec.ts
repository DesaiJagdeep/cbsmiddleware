import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { OccupationMasterFormService } from './occupation-master-form.service';
import { OccupationMasterService } from '../service/occupation-master.service';
import { IOccupationMaster } from '../occupation-master.model';

import { OccupationMasterUpdateComponent } from './occupation-master-update.component';

describe('OccupationMaster Management Update Component', () => {
  let comp: OccupationMasterUpdateComponent;
  let fixture: ComponentFixture<OccupationMasterUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let occupationMasterFormService: OccupationMasterFormService;
  let occupationMasterService: OccupationMasterService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [OccupationMasterUpdateComponent],
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
      .overrideTemplate(OccupationMasterUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(OccupationMasterUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    occupationMasterFormService = TestBed.inject(OccupationMasterFormService);
    occupationMasterService = TestBed.inject(OccupationMasterService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const occupationMaster: IOccupationMaster = { id: 456 };

      activatedRoute.data = of({ occupationMaster });
      comp.ngOnInit();

      expect(comp.occupationMaster).toEqual(occupationMaster);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOccupationMaster>>();
      const occupationMaster = { id: 123 };
      jest.spyOn(occupationMasterFormService, 'getOccupationMaster').mockReturnValue(occupationMaster);
      jest.spyOn(occupationMasterService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ occupationMaster });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: occupationMaster }));
      saveSubject.complete();

      // THEN
      expect(occupationMasterFormService.getOccupationMaster).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(occupationMasterService.update).toHaveBeenCalledWith(expect.objectContaining(occupationMaster));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOccupationMaster>>();
      const occupationMaster = { id: 123 };
      jest.spyOn(occupationMasterFormService, 'getOccupationMaster').mockReturnValue({ id: null });
      jest.spyOn(occupationMasterService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ occupationMaster: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: occupationMaster }));
      saveSubject.complete();

      // THEN
      expect(occupationMasterFormService.getOccupationMaster).toHaveBeenCalled();
      expect(occupationMasterService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOccupationMaster>>();
      const occupationMaster = { id: 123 };
      jest.spyOn(occupationMasterService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ occupationMaster });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(occupationMasterService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
