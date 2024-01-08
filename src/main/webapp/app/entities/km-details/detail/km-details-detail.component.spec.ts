import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { KmDetailsDetailComponent } from './km-details-detail.component';

describe('KmDetails Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [KmDetailsDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: KmDetailsDetailComponent,
              resolve: { kmDetails: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(KmDetailsDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load kmDetails on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', KmDetailsDetailComponent);

      // THEN
      expect(instance.kmDetails).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
