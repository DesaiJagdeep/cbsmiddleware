import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DistrictMasterDetailComponent } from './district-master-detail.component';

describe('DistrictMaster Management Detail Component', () => {
  let comp: DistrictMasterDetailComponent;
  let fixture: ComponentFixture<DistrictMasterDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DistrictMasterDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ districtMaster: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(DistrictMasterDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(DistrictMasterDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load districtMaster on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.districtMaster).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
