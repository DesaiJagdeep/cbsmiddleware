import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PacsMasterFormService } from './pacs-master-form.service';
import { PacsMasterService } from '../service/pacs-master.service';
import { IPacsMaster } from '../pacs-master.model';
import { IBankBranchMaster } from 'app/entities/bank-branch-master/bank-branch-master.model';
import { BankBranchMasterService } from 'app/entities/bank-branch-master/service/bank-branch-master.service';

import { PacsMasterUpdateComponent } from './pacs-master-update.component';

describe('PacsMaster Management Update Component', () => {
  let comp: PacsMasterUpdateComponent;
  let fixture: ComponentFixture<PacsMasterUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let pacsMasterFormService: PacsMasterFormService;
  let pacsMasterService: PacsMasterService;
  let bankBranchMasterService: BankBranchMasterService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PacsMasterUpdateComponent],
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
      .overrideTemplate(PacsMasterUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PacsMasterUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    pacsMasterFormService = TestBed.inject(PacsMasterFormService);
    pacsMasterService = TestBed.inject(PacsMasterService);
    bankBranchMasterService = TestBed.inject(BankBranchMasterService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call BankBranchMaster query and add missing value', () => {
      const pacsMaster: IPacsMaster = { id: 456 };
      const bankBranchMaster: IBankBranchMaster = { id: 35632 };
      pacsMaster.bankBranchMaster = bankBranchMaster;

      const bankBranchMasterCollection: IBankBranchMaster[] = [{ id: 68510 }];
      jest.spyOn(bankBranchMasterService, 'query').mockReturnValue(of(new HttpResponse({ body: bankBranchMasterCollection })));
      const additionalBankBranchMasters = [bankBranchMaster];
      const expectedCollection: IBankBranchMaster[] = [...additionalBankBranchMasters, ...bankBranchMasterCollection];
      jest.spyOn(bankBranchMasterService, 'addBankBranchMasterToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ pacsMaster });
      comp.ngOnInit();

      expect(bankBranchMasterService.query).toHaveBeenCalled();
      expect(bankBranchMasterService.addBankBranchMasterToCollectionIfMissing).toHaveBeenCalledWith(
        bankBranchMasterCollection,
        ...additionalBankBranchMasters.map(expect.objectContaining)
      );
      expect(comp.bankBranchMastersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const pacsMaster: IPacsMaster = { id: 456 };
      const bankBranchMaster: IBankBranchMaster = { id: 10010 };
      pacsMaster.bankBranchMaster = bankBranchMaster;

      activatedRoute.data = of({ pacsMaster });
      comp.ngOnInit();

      expect(comp.bankBranchMastersSharedCollection).toContain(bankBranchMaster);
      expect(comp.pacsMaster).toEqual(pacsMaster);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPacsMaster>>();
      const pacsMaster = { id: 123 };
      jest.spyOn(pacsMasterFormService, 'getPacsMaster').mockReturnValue(pacsMaster);
      jest.spyOn(pacsMasterService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pacsMaster });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: pacsMaster }));
      saveSubject.complete();

      // THEN
      expect(pacsMasterFormService.getPacsMaster).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(pacsMasterService.update).toHaveBeenCalledWith(expect.objectContaining(pacsMaster));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPacsMaster>>();
      const pacsMaster = { id: 123 };
      jest.spyOn(pacsMasterFormService, 'getPacsMaster').mockReturnValue({ id: null });
      jest.spyOn(pacsMasterService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pacsMaster: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: pacsMaster }));
      saveSubject.complete();

      // THEN
      expect(pacsMasterFormService.getPacsMaster).toHaveBeenCalled();
      expect(pacsMasterService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPacsMaster>>();
      const pacsMaster = { id: 123 };
      jest.spyOn(pacsMasterService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pacsMaster });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(pacsMasterService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareBankBranchMaster', () => {
      it('Should forward to bankBranchMasterService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(bankBranchMasterService, 'compareBankBranchMaster');
        comp.compareBankBranchMaster(entity, entity2);
        expect(bankBranchMasterService.compareBankBranchMaster).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
