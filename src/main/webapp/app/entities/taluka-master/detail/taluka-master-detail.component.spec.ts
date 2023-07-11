import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TalukaMasterDetailComponent } from './taluka-master-detail.component';

describe('TalukaMaster Management Detail Component', () => {
  let comp: TalukaMasterDetailComponent;
  let fixture: ComponentFixture<TalukaMasterDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TalukaMasterDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ talukaMaster: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(TalukaMasterDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TalukaMasterDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load talukaMaster on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.talukaMaster).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
