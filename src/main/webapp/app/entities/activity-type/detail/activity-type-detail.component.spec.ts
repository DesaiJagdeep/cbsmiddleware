import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ActivityTypeDetailComponent } from './activity-type-detail.component';

describe('ActivityType Management Detail Component', () => {
  let comp: ActivityTypeDetailComponent;
  let fixture: ComponentFixture<ActivityTypeDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ActivityTypeDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ activityType: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ActivityTypeDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ActivityTypeDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load activityType on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.activityType).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
