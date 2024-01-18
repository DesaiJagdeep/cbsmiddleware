import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { KarkhanaVasuliRecordsDetailComponent } from './karkhana-vasuli-records-detail.component';

describe('KarkhanaVasuliRecords Management Detail Component', () => {
  let comp: KarkhanaVasuliRecordsDetailComponent;
  let fixture: ComponentFixture<KarkhanaVasuliRecordsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [KarkhanaVasuliRecordsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ karkhanaVasuliRecords: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(KarkhanaVasuliRecordsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(KarkhanaVasuliRecordsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load karkhanaVasuliRecords on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.karkhanaVasuliRecords).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
