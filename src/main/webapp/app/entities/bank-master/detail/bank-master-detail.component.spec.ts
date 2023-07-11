import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BankMasterDetailComponent } from './bank-master-detail.component';

describe('BankMaster Management Detail Component', () => {
  let comp: BankMasterDetailComponent;
  let fixture: ComponentFixture<BankMasterDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BankMasterDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ bankMaster: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(BankMasterDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(BankMasterDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load bankMaster on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.bankMaster).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
