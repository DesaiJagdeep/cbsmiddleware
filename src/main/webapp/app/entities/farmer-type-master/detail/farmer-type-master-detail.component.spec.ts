import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FarmerTypeMasterDetailComponent } from './farmer-type-master-detail.component';

describe('FarmerTypeMaster Management Detail Component', () => {
  let comp: FarmerTypeMasterDetailComponent;
  let fixture: ComponentFixture<FarmerTypeMasterDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FarmerTypeMasterDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ farmerTypeMaster: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(FarmerTypeMasterDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(FarmerTypeMasterDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load farmerTypeMaster on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.farmerTypeMaster).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
