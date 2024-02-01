import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { KamalSocietyDetailComponent } from './kamal-society-detail.component';

describe('KamalSociety Management Detail Component', () => {
  let comp: KamalSocietyDetailComponent;
  let fixture: ComponentFixture<KamalSocietyDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [KamalSocietyDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ kamalSociety: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(KamalSocietyDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(KamalSocietyDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load kamalSociety on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.kamalSociety).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
