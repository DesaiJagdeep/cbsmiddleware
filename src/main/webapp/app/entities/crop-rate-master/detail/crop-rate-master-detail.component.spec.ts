import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CropRateMasterDetailComponent } from './crop-rate-master-detail.component';

describe('CropRateMaster Management Detail Component', () => {
  let comp: CropRateMasterDetailComponent;
  let fixture: ComponentFixture<CropRateMasterDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CropRateMasterDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ cropRateMaster: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(CropRateMasterDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(CropRateMasterDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load cropRateMaster on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.cropRateMaster).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
