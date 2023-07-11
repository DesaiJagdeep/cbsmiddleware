import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AccountHolderMasterDetailComponent } from './account-holder-master-detail.component';

describe('AccountHolderMaster Management Detail Component', () => {
  let comp: AccountHolderMasterDetailComponent;
  let fixture: ComponentFixture<AccountHolderMasterDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AccountHolderMasterDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ accountHolderMaster: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(AccountHolderMasterDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AccountHolderMasterDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load accountHolderMaster on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.accountHolderMaster).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
