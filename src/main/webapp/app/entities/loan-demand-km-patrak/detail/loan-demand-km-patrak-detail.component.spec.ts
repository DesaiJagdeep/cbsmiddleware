import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LoanDemandKMPatrakDetailComponent } from './loan-demand-km-patrak-detail.component';

describe('LoanDemandKMPatrak Management Detail Component', () => {
  let comp: LoanDemandKMPatrakDetailComponent;
  let fixture: ComponentFixture<LoanDemandKMPatrakDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LoanDemandKMPatrakDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ loanDemandKMPatrak: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(LoanDemandKMPatrakDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(LoanDemandKMPatrakDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load loanDemandKMPatrak on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.loanDemandKMPatrak).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
