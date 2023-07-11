import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PacsMasterDetailComponent } from './pacs-master-detail.component';

describe('PacsMaster Management Detail Component', () => {
  let comp: PacsMasterDetailComponent;
  let fixture: ComponentFixture<PacsMasterDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PacsMasterDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ pacsMaster: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PacsMasterDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PacsMasterDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load pacsMaster on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.pacsMaster).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
