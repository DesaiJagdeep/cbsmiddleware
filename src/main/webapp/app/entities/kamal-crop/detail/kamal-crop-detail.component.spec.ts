import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { KamalCropDetailComponent } from './kamal-crop-detail.component';

describe('KamalCrop Management Detail Component', () => {
  let comp: KamalCropDetailComponent;
  let fixture: ComponentFixture<KamalCropDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [KamalCropDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ kamalCrop: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(KamalCropDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(KamalCropDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load kamalCrop on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.kamalCrop).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
