import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { StateMasterDetailComponent } from './state-master-detail.component';

describe('StateMaster Management Detail Component', () => {
  let comp: StateMasterDetailComponent;
  let fixture: ComponentFixture<StateMasterDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [StateMasterDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ stateMaster: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(StateMasterDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(StateMasterDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load stateMaster on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.stateMaster).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
