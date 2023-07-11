import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CategoryMasterDetailComponent } from './category-master-detail.component';

describe('CategoryMaster Management Detail Component', () => {
  let comp: CategoryMasterDetailComponent;
  let fixture: ComponentFixture<CategoryMasterDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CategoryMasterDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ categoryMaster: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(CategoryMasterDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(CategoryMasterDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load categoryMaster on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.categoryMaster).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
