import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { BankBranchMasterFormService } from './bank-branch-master-form.service';
import { BankBranchMasterService } from '../service/bank-branch-master.service';
import { IBankBranchMaster } from '../bank-branch-master.model';
import { IBankMaster } from 'app/entities/bank-master/bank-master.model';
import { BankMasterService } from 'app/entities/bank-master/service/bank-master.service';

import { BankBranchMasterUpdateComponent } from './bank-branch-master-update.component';

describe('BankBranchMaster Management Update Component', () => {
  let comp: BankBranchMasterUpdateComponent;
  let fixture: ComponentFixture<BankBranchMasterUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let bankBranchMasterFormService: BankBranchMasterFormService;
  let bankBranchMasterService: BankBranchMasterService;
  let bankMasterService: BankMasterService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [BankBranchMasterUpdateComponent],
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
      .overrideTemplate(BankBranchMasterUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(BankBranchMasterUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    bankBranchMasterFormService = TestBed.inject(BankBranchMasterFormService);
    bankBranchMasterService = TestBed.inject(BankBranchMasterService);
    bankMasterService = TestBed.inject(BankMasterService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call BankMaster query and add missing value', () => {
      const bankBranchMaster: IBankBranchMaster = { id: 456 };
      const bankMaster: IBankMaster = { id: 85 };
      bankBranchMaster.bankMaster = bankMaster;

      const bankMasterCollection: IBankMaster[] = [{ id: 75793 }];
      jest.spyOn(bankMasterService, 'query').mockReturnValue(of(new HttpResponse({ body: bankMasterCollection })));
      const additionalBankMasters = [bankMaster];
      const expectedCollection: IBankMaster[] = [...additionalBankMasters, ...bankMasterCollection];
      jest.spyOn(bankMasterService, 'addBankMasterToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ bankBranchMaster });
      comp.ngOnInit();

      expect(bankMasterService.query).toHaveBeenCalled();
      expect(bankMasterService.addBankMasterToCollectionIfMissing).toHaveBeenCalledWith(
        bankMasterCollection,
        ...additionalBankMasters.map(expect.objectContaining)
      );
      expect(comp.bankMastersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const bankBranchMaster: IBankBranchMaster = { id: 456 };
      const bankMaster: IBankMaster = { id: 16847 };
      bankBranchMaster.bankMaster = bankMaster;

      activatedRoute.data = of({ bankBranchMaster });
      comp.ngOnInit();

      expect(comp.bankMastersSharedCollection).toContain(bankMaster);
      expect(comp.bankBranchMaster).toEqual(bankBranchMaster);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IBankBranchMaster>>();
      const bankBranchMaster = { id: 123 };
      jest.spyOn(bankBranchMasterFormService, 'getBankBranchMaster').mockReturnValue(bankBranchMaster);
      jest.spyOn(bankBranchMasterService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bankBranchMaster });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: bankBranchMaster }));
      saveSubject.complete();

      // THEN
      expect(bankBranchMasterFormService.getBankBranchMaster).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(bankBranchMasterService.update).toHaveBeenCalledWith(expect.objectContaining(bankBranchMaster));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IBankBranchMaster>>();
      const bankBranchMaster = { id: 123 };
      jest.spyOn(bankBranchMasterFormService, 'getBankBranchMaster').mockReturnValue({ id: null });
      jest.spyOn(bankBranchMasterService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bankBranchMaster: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: bankBranchMaster }));
      saveSubject.complete();

      // THEN
      expect(bankBranchMasterFormService.getBankBranchMaster).toHaveBeenCalled();
      expect(bankBranchMasterService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IBankBranchMaster>>();
      const bankBranchMaster = { id: 123 };
      jest.spyOn(bankBranchMasterService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bankBranchMaster });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(bankBranchMasterService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareBankMaster', () => {
      it('Should forward to bankMasterService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(bankMasterService, 'compareBankMaster');
        comp.compareBankMaster(entity, entity2);
        expect(bankMasterService.compareBankMaster).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
