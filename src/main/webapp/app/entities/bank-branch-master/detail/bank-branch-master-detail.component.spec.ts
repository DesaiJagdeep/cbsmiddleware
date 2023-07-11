import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BankBranchMasterDetailComponent } from './bank-branch-master-detail.component';

describe('BankBranchMaster Management Detail Component', () => {
  let comp: BankBranchMasterDetailComponent;
  let fixture: ComponentFixture<BankBranchMasterDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BankBranchMasterDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ bankBranchMaster: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(BankBranchMasterDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(BankBranchMasterDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load bankBranchMaster on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.bankBranchMaster).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
