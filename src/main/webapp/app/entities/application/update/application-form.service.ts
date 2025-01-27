import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IApplication, NewApplication } from '../application.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IApplication for edit and NewApplicationFormGroupInput for create.
 */
type ApplicationFormGroupInput = IApplication | PartialWithRequiredKeyOf<NewApplication>;

type ApplicationFormDefaults = Pick<NewApplication, 'id'>;

type ApplicationFormGroupContent = {
  id: FormControl<IApplication['id'] | NewApplication['id']>;
  batchId: FormControl<IApplication['batchId']>;
  uniqueId: FormControl<IApplication['uniqueId']>;
  recordStatus: FormControl<IApplication['recordStatus']>;
  applicationStatus: FormControl<IApplication['applicationStatus']>;
  kccStatus: FormControl<IApplication['kccStatus']>;
  recipientUniqueId: FormControl<IApplication['recipientUniqueId']>;
  farmerId: FormControl<IApplication['farmerId']>;
  issFileParser: FormControl<IApplication['issFileParser']>;
};

export type ApplicationFormGroup = FormGroup<ApplicationFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ApplicationFormService {
  createApplicationFormGroup(application: ApplicationFormGroupInput = { id: null }): ApplicationFormGroup {
    const applicationRawValue = {
      ...this.getFormDefaults(),
      ...application,
    };
    return new FormGroup<ApplicationFormGroupContent>({
      id: new FormControl(
        { value: applicationRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      batchId: new FormControl(applicationRawValue.batchId),
      uniqueId: new FormControl(applicationRawValue.uniqueId),
      recordStatus: new FormControl(applicationRawValue.recordStatus),
      applicationStatus: new FormControl(applicationRawValue.applicationStatus),
      kccStatus: new FormControl(applicationRawValue.kccStatus),
      recipientUniqueId: new FormControl(applicationRawValue.recipientUniqueId),
      farmerId: new FormControl(applicationRawValue.farmerId),
      issFileParser: new FormControl(applicationRawValue.issFileParser),
    });
  }

  getApplication(form: ApplicationFormGroup): IApplication | NewApplication {
    return form.getRawValue() as IApplication | NewApplication;
  }

  resetForm(form: ApplicationFormGroup, application: ApplicationFormGroupInput): void {
    const applicationRawValue = { ...this.getFormDefaults(), ...application };
    form.reset(
      {
        ...applicationRawValue,
        id: { value: applicationRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ApplicationFormDefaults {
    return {
      id: null,
    };
  }
}
