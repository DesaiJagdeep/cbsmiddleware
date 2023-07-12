import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BatchTransactionDetailComponent } from './batch-transaction-detail.component';

describe('BatchTransaction Management Detail Component', () => {
  let comp: BatchTransactionDetailComponent;
  let fixture: ComponentFixture<BatchTransactionDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BatchTransactionDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ batchTransaction: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(BatchTransactionDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(BatchTransactionDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load batchTransaction on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.batchTransaction).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
