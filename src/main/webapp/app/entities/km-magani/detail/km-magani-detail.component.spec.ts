import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { KmMaganiDetailComponent } from './km-magani-detail.component';

describe('KmMagani Management Detail Component', () => {
  let comp: KmMaganiDetailComponent;
  let fixture: ComponentFixture<KmMaganiDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [KmMaganiDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ kmMagani: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(KmMaganiDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(KmMaganiDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load kmMagani on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.kmMagani).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
