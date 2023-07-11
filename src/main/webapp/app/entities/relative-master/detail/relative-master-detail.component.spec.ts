import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RelativeMasterDetailComponent } from './relative-master-detail.component';

describe('RelativeMaster Management Detail Component', () => {
  let comp: RelativeMasterDetailComponent;
  let fixture: ComponentFixture<RelativeMasterDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RelativeMasterDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ relativeMaster: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(RelativeMasterDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(RelativeMasterDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load relativeMaster on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.relativeMaster).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
