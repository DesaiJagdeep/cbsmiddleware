import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { KMPUploadDetailComponent } from './kmp-upload-detail.component';

describe('KMPUpload Management Detail Component', () => {
  let comp: KMPUploadDetailComponent;
  let fixture: ComponentFixture<KMPUploadDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [KMPUploadDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ kMPUpload: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(KMPUploadDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(KMPUploadDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load kMPUpload on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.kMPUpload).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
