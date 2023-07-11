import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DesignationMasterDetailComponent } from './designation-master-detail.component';

describe('DesignationMaster Management Detail Component', () => {
  let comp: DesignationMasterDetailComponent;
  let fixture: ComponentFixture<DesignationMasterDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DesignationMasterDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ designationMaster: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(DesignationMasterDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(DesignationMasterDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load designationMaster on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.designationMaster).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
