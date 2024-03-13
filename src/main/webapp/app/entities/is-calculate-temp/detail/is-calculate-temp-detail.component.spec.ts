import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IsCalculateTempDetailComponent } from './is-calculate-temp-detail.component';

describe('IsCalculateTemp Management Detail Component', () => {
  let comp: IsCalculateTempDetailComponent;
  let fixture: ComponentFixture<IsCalculateTempDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [IsCalculateTempDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ isCalculateTemp: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(IsCalculateTempDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(IsCalculateTempDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load isCalculateTemp on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.isCalculateTemp).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
