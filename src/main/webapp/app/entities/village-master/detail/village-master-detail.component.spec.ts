import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { VillageMasterDetailComponent } from './village-master-detail.component';

describe('VillageMaster Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VillageMasterDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: VillageMasterDetailComponent,
              resolve: { villageMaster: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(VillageMasterDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load villageMaster on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', VillageMasterDetailComponent);

      // THEN
      expect(instance.villageMaster).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
