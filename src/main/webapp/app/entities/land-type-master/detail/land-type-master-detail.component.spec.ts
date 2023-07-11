import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LandTypeMasterDetailComponent } from './land-type-master-detail.component';

describe('LandTypeMaster Management Detail Component', () => {
  let comp: LandTypeMasterDetailComponent;
  let fixture: ComponentFixture<LandTypeMasterDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LandTypeMasterDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ landTypeMaster: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(LandTypeMasterDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(LandTypeMasterDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load landTypeMaster on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.landTypeMaster).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
