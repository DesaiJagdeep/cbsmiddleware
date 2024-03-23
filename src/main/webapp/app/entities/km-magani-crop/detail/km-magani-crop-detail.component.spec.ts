import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { KmMaganiCropDetailComponent } from './km-magani-crop-detail.component';

describe('KmMaganiCrop Management Detail Component', () => {
  let comp: KmMaganiCropDetailComponent;
  let fixture: ComponentFixture<KmMaganiCropDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [KmMaganiCropDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ kmMaganiCrop: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(KmMaganiCropDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(KmMaganiCropDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load kmMaganiCrop on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.kmMaganiCrop).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
