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

import { BankBranchMasterUpdateComponent } from './bank-branch-master-update.component';

describe('BankBranchMaster Management Update Component', () => {
  let comp: BankBranchMasterUpdateComponent;
  let fixture: ComponentFixture<BankBranchMasterUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let bankBranchMasterFormService: BankBranchMasterFormService;
  let bankBranchMasterService: BankBranchMasterService;

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

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const bankBranchMaster: IBankBranchMaster = { id: 456 };

      activatedRoute.data = of({ bankBranchMaster });
      comp.ngOnInit();

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
});
