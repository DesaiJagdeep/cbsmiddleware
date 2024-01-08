import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FactoryMasterDetailComponent } from './factory-master-detail.component';

describe('FactoryMaster Management Detail Component', () => {
  let comp: FactoryMasterDetailComponent;
  let fixture: ComponentFixture<FactoryMasterDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FactoryMasterDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ factoryMaster: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(FactoryMasterDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(FactoryMasterDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load factoryMaster on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.factoryMaster).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
