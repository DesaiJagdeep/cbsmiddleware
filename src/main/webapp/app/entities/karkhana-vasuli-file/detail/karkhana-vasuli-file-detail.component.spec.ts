import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { KarkhanaVasuliFileDetailComponent } from './karkhana-vasuli-file-detail.component';

describe('KarkhanaVasuliFile Management Detail Component', () => {
  let comp: KarkhanaVasuliFileDetailComponent;
  let fixture: ComponentFixture<KarkhanaVasuliFileDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [KarkhanaVasuliFileDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ karkhanaVasuliFile: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(KarkhanaVasuliFileDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(KarkhanaVasuliFileDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load karkhanaVasuliFile on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.karkhanaVasuliFile).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
