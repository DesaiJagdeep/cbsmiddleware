import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { KmLoansDetailComponent } from './km-loans-detail.component';

describe('KmLoans Management Detail Component', () => {
  let comp: KmLoansDetailComponent;
  let fixture: ComponentFixture<KmLoansDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [KmLoansDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ kmLoans: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(KmLoansDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(KmLoansDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load kmLoans on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.kmLoans).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
