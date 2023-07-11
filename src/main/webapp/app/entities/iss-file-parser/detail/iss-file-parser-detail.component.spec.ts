import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IssFileParserDetailComponent } from './iss-file-parser-detail.component';

describe('IssFileParser Management Detail Component', () => {
  let comp: IssFileParserDetailComponent;
  let fixture: ComponentFixture<IssFileParserDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [IssFileParserDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ issFileParser: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(IssFileParserDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(IssFileParserDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load issFileParser on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.issFileParser).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
