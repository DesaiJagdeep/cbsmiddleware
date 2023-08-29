import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CourtCaseSettingDetailComponent } from './court-case-setting-detail.component';

describe('CourtCaseSetting Management Detail Component', () => {
  let comp: CourtCaseSettingDetailComponent;
  let fixture: ComponentFixture<CourtCaseSettingDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CourtCaseSettingDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ courtCaseSetting: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(CourtCaseSettingDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(CourtCaseSettingDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load courtCaseSetting on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.courtCaseSetting).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
