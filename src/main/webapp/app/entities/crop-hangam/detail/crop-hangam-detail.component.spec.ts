import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CropHangamDetailComponent } from './crop-hangam-detail.component';

describe('CropHangam Management Detail Component', () => {
  let comp: CropHangamDetailComponent;
  let fixture: ComponentFixture<CropHangamDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CropHangamDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ cropHangam: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(CropHangamDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(CropHangamDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load cropHangam on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.cropHangam).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
