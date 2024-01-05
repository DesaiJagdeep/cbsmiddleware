import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { KmCropsDetailComponent } from './km-crops-detail.component';

describe('KmCrops Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [KmCropsDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: KmCropsDetailComponent,
              resolve: { kmCrops: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(KmCropsDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load kmCrops on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', KmCropsDetailComponent);

      // THEN
      expect(instance.kmCrops).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
