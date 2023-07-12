import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { BatchTransactionFormService } from './batch-transaction-form.service';
import { BatchTransactionService } from '../service/batch-transaction.service';
import { IBatchTransaction } from '../batch-transaction.model';

import { BatchTransactionUpdateComponent } from './batch-transaction-update.component';

describe('BatchTransaction Management Update Component', () => {
  let comp: BatchTransactionUpdateComponent;
  let fixture: ComponentFixture<BatchTransactionUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let batchTransactionFormService: BatchTransactionFormService;
  let batchTransactionService: BatchTransactionService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [BatchTransactionUpdateComponent],
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
      .overrideTemplate(BatchTransactionUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(BatchTransactionUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    batchTransactionFormService = TestBed.inject(BatchTransactionFormService);
    batchTransactionService = TestBed.inject(BatchTransactionService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const batchTransaction: IBatchTransaction = { id: 456 };

      activatedRoute.data = of({ batchTransaction });
      comp.ngOnInit();

      expect(comp.batchTransaction).toEqual(batchTransaction);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IBatchTransaction>>();
      const batchTransaction = { id: 123 };
      jest.spyOn(batchTransactionFormService, 'getBatchTransaction').mockReturnValue(batchTransaction);
      jest.spyOn(batchTransactionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ batchTransaction });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: batchTransaction }));
      saveSubject.complete();

      // THEN
      expect(batchTransactionFormService.getBatchTransaction).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(batchTransactionService.update).toHaveBeenCalledWith(expect.objectContaining(batchTransaction));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IBatchTransaction>>();
      const batchTransaction = { id: 123 };
      jest.spyOn(batchTransactionFormService, 'getBatchTransaction').mockReturnValue({ id: null });
      jest.spyOn(batchTransactionService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ batchTransaction: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: batchTransaction }));
      saveSubject.complete();

      // THEN
      expect(batchTransactionFormService.getBatchTransaction).toHaveBeenCalled();
      expect(batchTransactionService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IBatchTransaction>>();
      const batchTransaction = { id: 123 };
      jest.spyOn(batchTransactionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ batchTransaction });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(batchTransactionService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
