import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CbsDataReportDetailComponent } from './cbs-data-report-detail.component';

describe('CbsDataReport Management Detail Component', () => {
  let comp: CbsDataReportDetailComponent;
  let fixture: ComponentFixture<CbsDataReportDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CbsDataReportDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ cbsDataReport: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(CbsDataReportDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(CbsDataReportDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load cbsDataReport on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.cbsDataReport).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
