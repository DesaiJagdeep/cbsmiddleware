import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { KmDetailsDetailComponent } from './km-details-detail.component';

describe('KmDetails Management Detail Component', () => {
  let comp: KmDetailsDetailComponent;
  let fixture: ComponentFixture<KmDetailsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [KmDetailsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ kmDetails: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(KmDetailsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(KmDetailsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load kmDetails on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.kmDetails).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
