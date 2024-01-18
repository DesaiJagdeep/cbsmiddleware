import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { KmMasterDetailComponent } from './km-master-detail.component';

describe('KmMaster Management Detail Component', () => {
  let comp: KmMasterDetailComponent;
  let fixture: ComponentFixture<KmMasterDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [KmMasterDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ kmMaster: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(KmMasterDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(KmMasterDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load kmMaster on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.kmMaster).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
