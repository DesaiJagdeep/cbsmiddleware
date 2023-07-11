import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CastCategoryMasterDetailComponent } from './cast-category-master-detail.component';

describe('CastCategoryMaster Management Detail Component', () => {
  let comp: CastCategoryMasterDetailComponent;
  let fixture: ComponentFixture<CastCategoryMasterDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CastCategoryMasterDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ castCategoryMaster: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(CastCategoryMasterDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(CastCategoryMasterDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load castCategoryMaster on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.castCategoryMaster).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
