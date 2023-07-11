import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SeasonMasterDetailComponent } from './season-master-detail.component';

describe('SeasonMaster Management Detail Component', () => {
  let comp: SeasonMasterDetailComponent;
  let fixture: ComponentFixture<SeasonMasterDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SeasonMasterDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ seasonMaster: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(SeasonMasterDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SeasonMasterDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load seasonMaster on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.seasonMaster).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
