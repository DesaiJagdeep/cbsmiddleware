import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IssPortalFileDetailComponent } from './iss-portal-file-detail.component';

describe('IssPortalFile Management Detail Component', () => {
  let comp: IssPortalFileDetailComponent;
  let fixture: ComponentFixture<IssPortalFileDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [IssPortalFileDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ issPortalFile: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(IssPortalFileDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(IssPortalFileDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load issPortalFile on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.issPortalFile).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
