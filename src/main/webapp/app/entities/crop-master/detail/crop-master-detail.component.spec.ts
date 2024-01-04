import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { CropMasterDetailComponent } from './crop-master-detail.component';

describe('CropMaster Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CropMasterDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: CropMasterDetailComponent,
              resolve: { cropMaster: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(CropMasterDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load cropMaster on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', CropMasterDetailComponent);

      // THEN
      expect(instance.cropMaster).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
