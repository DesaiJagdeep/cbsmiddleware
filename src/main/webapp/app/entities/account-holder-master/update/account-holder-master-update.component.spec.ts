import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AccountHolderMasterFormService } from './account-holder-master-form.service';
import { AccountHolderMasterService } from '../service/account-holder-master.service';
import { IAccountHolderMaster } from '../account-holder-master.model';

import { AccountHolderMasterUpdateComponent } from './account-holder-master-update.component';

describe('AccountHolderMaster Management Update Component', () => {
  let comp: AccountHolderMasterUpdateComponent;
  let fixture: ComponentFixture<AccountHolderMasterUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let accountHolderMasterFormService: AccountHolderMasterFormService;
  let accountHolderMasterService: AccountHolderMasterService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AccountHolderMasterUpdateComponent],
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
      .overrideTemplate(AccountHolderMasterUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AccountHolderMasterUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    accountHolderMasterFormService = TestBed.inject(AccountHolderMasterFormService);
    accountHolderMasterService = TestBed.inject(AccountHolderMasterService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const accountHolderMaster: IAccountHolderMaster = { id: 456 };

      activatedRoute.data = of({ accountHolderMaster });
      comp.ngOnInit();

      expect(comp.accountHolderMaster).toEqual(accountHolderMaster);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAccountHolderMaster>>();
      const accountHolderMaster = { id: 123 };
      jest.spyOn(accountHolderMasterFormService, 'getAccountHolderMaster').mockReturnValue(accountHolderMaster);
      jest.spyOn(accountHolderMasterService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ accountHolderMaster });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: accountHolderMaster }));
      saveSubject.complete();

      // THEN
      expect(accountHolderMasterFormService.getAccountHolderMaster).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(accountHolderMasterService.update).toHaveBeenCalledWith(expect.objectContaining(accountHolderMaster));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAccountHolderMaster>>();
      const accountHolderMaster = { id: 123 };
      jest.spyOn(accountHolderMasterFormService, 'getAccountHolderMaster').mockReturnValue({ id: null });
      jest.spyOn(accountHolderMasterService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ accountHolderMaster: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: accountHolderMaster }));
      saveSubject.complete();

      // THEN
      expect(accountHolderMasterFormService.getAccountHolderMaster).toHaveBeenCalled();
      expect(accountHolderMasterService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAccountHolderMaster>>();
      const accountHolderMaster = { id: 123 };
      jest.spyOn(accountHolderMasterService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ accountHolderMaster });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(accountHolderMasterService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
