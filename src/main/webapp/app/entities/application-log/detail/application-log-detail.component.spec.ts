import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ApplicationLogDetailComponent } from './application-log-detail.component';

describe('ApplicationLog Management Detail Component', () => {
  let comp: ApplicationLogDetailComponent;
  let fixture: ComponentFixture<ApplicationLogDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ApplicationLogDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ applicationLog: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ApplicationLogDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ApplicationLogDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load applicationLog on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.applicationLog).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
