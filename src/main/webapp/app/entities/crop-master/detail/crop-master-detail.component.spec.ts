import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CropMasterDetailComponent } from './crop-master-detail.component';

describe('CropMaster Management Detail Component', () => {
  let comp: CropMasterDetailComponent;
  let fixture: ComponentFixture<CropMasterDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CropMasterDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ cropMaster: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(CropMasterDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(CropMasterDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load cropMaster on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.cropMaster).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
