import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { FactoryMasterDetailComponent } from './factory-master-detail.component';

describe('FactoryMaster Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FactoryMasterDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: FactoryMasterDetailComponent,
              resolve: { factoryMaster: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(FactoryMasterDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load factoryMaster on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', FactoryMasterDetailComponent);

      // THEN
      expect(instance.factoryMaster).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
