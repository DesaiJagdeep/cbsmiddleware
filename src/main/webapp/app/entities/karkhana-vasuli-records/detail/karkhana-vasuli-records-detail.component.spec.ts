import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { KarkhanaVasuliRecordsDetailComponent } from './karkhana-vasuli-records-detail.component';

describe('KarkhanaVasuliRecords Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [KarkhanaVasuliRecordsDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: KarkhanaVasuliRecordsDetailComponent,
              resolve: { karkhanaVasuliRecords: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(KarkhanaVasuliRecordsDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load karkhanaVasuliRecords on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', KarkhanaVasuliRecordsDetailComponent);

      // THEN
      expect(instance.karkhanaVasuliRecords).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
