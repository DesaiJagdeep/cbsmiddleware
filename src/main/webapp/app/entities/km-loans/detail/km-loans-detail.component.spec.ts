import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { KmLoansDetailComponent } from './km-loans-detail.component';

describe('KmLoans Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [KmLoansDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: KmLoansDetailComponent,
              resolve: { kmLoans: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(KmLoansDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load kmLoans on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', KmLoansDetailComponent);

      // THEN
      expect(instance.kmLoans).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
