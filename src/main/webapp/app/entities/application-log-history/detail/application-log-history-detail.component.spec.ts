import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ApplicationLogHistoryDetailComponent } from './application-log-history-detail.component';

describe('ApplicationLogHistory Management Detail Component', () => {
  let comp: ApplicationLogHistoryDetailComponent;
  let fixture: ComponentFixture<ApplicationLogHistoryDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ApplicationLogHistoryDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ applicationLogHistory: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ApplicationLogHistoryDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ApplicationLogHistoryDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load applicationLogHistory on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.applicationLogHistory).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
