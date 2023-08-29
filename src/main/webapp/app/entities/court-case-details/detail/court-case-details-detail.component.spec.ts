import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CourtCaseDetailsDetailComponent } from './court-case-details-detail.component';

describe('CourtCaseDetails Management Detail Component', () => {
  let comp: CourtCaseDetailsDetailComponent;
  let fixture: ComponentFixture<CourtCaseDetailsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CourtCaseDetailsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ courtCaseDetails: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(CourtCaseDetailsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(CourtCaseDetailsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load courtCaseDetails on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.courtCaseDetails).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
