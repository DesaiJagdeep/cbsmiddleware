import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { VillageMasterDetailComponent } from './village-master-detail.component';

describe('VillageMaster Management Detail Component', () => {
  let comp: VillageMasterDetailComponent;
  let fixture: ComponentFixture<VillageMasterDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [VillageMasterDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ villageMaster: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(VillageMasterDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(VillageMasterDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load villageMaster on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.villageMaster).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
