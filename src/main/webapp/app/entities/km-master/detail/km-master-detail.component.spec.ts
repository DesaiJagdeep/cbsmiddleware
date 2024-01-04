import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { KmMasterDetailComponent } from './km-master-detail.component';

describe('KmMaster Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [KmMasterDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: KmMasterDetailComponent,
              resolve: { kmMaster: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(KmMasterDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load kmMaster on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', KmMasterDetailComponent);

      // THEN
      expect(instance.kmMaster).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
