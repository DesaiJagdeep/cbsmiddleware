import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FarmerCategoryMasterDetailComponent } from './farmer-category-master-detail.component';

describe('FarmerCategoryMaster Management Detail Component', () => {
  let comp: FarmerCategoryMasterDetailComponent;
  let fixture: ComponentFixture<FarmerCategoryMasterDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FarmerCategoryMasterDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ farmerCategoryMaster: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(FarmerCategoryMasterDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(FarmerCategoryMasterDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load farmerCategoryMaster on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.farmerCategoryMaster).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
