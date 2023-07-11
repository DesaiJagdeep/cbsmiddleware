import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CropCategoryMasterDetailComponent } from './crop-category-master-detail.component';

describe('CropCategoryMaster Management Detail Component', () => {
  let comp: CropCategoryMasterDetailComponent;
  let fixture: ComponentFixture<CropCategoryMasterDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CropCategoryMasterDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ cropCategoryMaster: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(CropCategoryMasterDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(CropCategoryMasterDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load cropCategoryMaster on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.cropCategoryMaster).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
