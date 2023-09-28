import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { KamalMaryadaPatrakDetailComponent } from './kamal-maryada-patrak-detail.component';

describe('KamalMaryadaPatrak Management Detail Component', () => {
  let comp: KamalMaryadaPatrakDetailComponent;
  let fixture: ComponentFixture<KamalMaryadaPatrakDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [KamalMaryadaPatrakDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ kamalMaryadaPatrak: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(KamalMaryadaPatrakDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(KamalMaryadaPatrakDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load kamalMaryadaPatrak on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.kamalMaryadaPatrak).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
