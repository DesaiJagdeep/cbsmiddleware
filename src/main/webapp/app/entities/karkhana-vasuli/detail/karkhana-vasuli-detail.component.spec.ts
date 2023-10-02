import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { KarkhanaVasuliDetailComponent } from './karkhana-vasuli-detail.component';

describe('KarkhanaVasuli Management Detail Component', () => {
  let comp: KarkhanaVasuliDetailComponent;
  let fixture: ComponentFixture<KarkhanaVasuliDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [KarkhanaVasuliDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ karkhanaVasuli: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(KarkhanaVasuliDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(KarkhanaVasuliDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load karkhanaVasuli on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.karkhanaVasuli).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
