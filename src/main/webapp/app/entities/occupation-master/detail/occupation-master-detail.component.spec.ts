import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OccupationMasterDetailComponent } from './occupation-master-detail.component';

describe('OccupationMaster Management Detail Component', () => {
  let comp: OccupationMasterDetailComponent;
  let fixture: ComponentFixture<OccupationMasterDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [OccupationMasterDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ occupationMaster: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(OccupationMasterDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(OccupationMasterDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load occupationMaster on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.occupationMaster).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
