import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { KmCropsDetailComponent } from './km-crops-detail.component';

describe('KmCrops Management Detail Component', () => {
  let comp: KmCropsDetailComponent;
  let fixture: ComponentFixture<KmCropsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [KmCropsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ kmCrops: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(KmCropsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(KmCropsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load kmCrops on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.kmCrops).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
