import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { BankMasterFormService } from './bank-master-form.service';
import { BankMasterService } from '../service/bank-master.service';
import { IBankMaster } from '../bank-master.model';

import { BankMasterUpdateComponent } from './bank-master-update.component';

describe('BankMaster Management Update Component', () => {
  let comp: BankMasterUpdateComponent;
  let fixture: ComponentFixture<BankMasterUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let bankMasterFormService: BankMasterFormService;
  let bankMasterService: BankMasterService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [BankMasterUpdateComponent],
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
      .overrideTemplate(BankMasterUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(BankMasterUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    bankMasterFormService = TestBed.inject(BankMasterFormService);
    bankMasterService = TestBed.inject(BankMasterService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const bankMaster: IBankMaster = { id: 456 };

      activatedRoute.data = of({ bankMaster });
      comp.ngOnInit();

      expect(comp.bankMaster).toEqual(bankMaster);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IBankMaster>>();
      const bankMaster = { id: 123 };
      jest.spyOn(bankMasterFormService, 'getBankMaster').mockReturnValue(bankMaster);
      jest.spyOn(bankMasterService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bankMaster });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: bankMaster }));
      saveSubject.complete();

      // THEN
      expect(bankMasterFormService.getBankMaster).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(bankMasterService.update).toHaveBeenCalledWith(expect.objectContaining(bankMaster));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IBankMaster>>();
      const bankMaster = { id: 123 };
      jest.spyOn(bankMasterFormService, 'getBankMaster').mockReturnValue({ id: null });
      jest.spyOn(bankMasterService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bankMaster: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: bankMaster }));
      saveSubject.complete();

      // THEN
      expect(bankMasterFormService.getBankMaster).toHaveBeenCalled();
      expect(bankMasterService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IBankMaster>>();
      const bankMaster = { id: 123 };
      jest.spyOn(bankMasterService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bankMaster });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(bankMasterService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
