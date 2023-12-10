import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { KarkhanaVasuliFileDetailComponent } from './karkhana-vasuli-file-detail.component';

describe('KarkhanaVasuliFile Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [KarkhanaVasuliFileDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: KarkhanaVasuliFileDetailComponent,
              resolve: { karkhanaVasuliFile: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(KarkhanaVasuliFileDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load karkhanaVasuliFile on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', KarkhanaVasuliFileDetailComponent);

      // THEN
      expect(instance.karkhanaVasuliFile).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
