import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IRelativeMaster, NewRelativeMaster } from '../relative-master.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IRelativeMaster for edit and NewRelativeMasterFormGroupInput for create.
 */
type RelativeMasterFormGroupInput = IRelativeMaster | PartialWithRequiredKeyOf<NewRelativeMaster>;

type RelativeMasterFormDefaults = Pick<NewRelativeMaster, 'id'>;

type RelativeMasterFormGroupContent = {
  id: FormControl<IRelativeMaster['id'] | NewRelativeMaster['id']>;
  relativeCode: FormControl<IRelativeMaster['relativeCode']>;
  relativeName: FormControl<IRelativeMaster['relativeName']>;
};

export type RelativeMasterFormGroup = FormGroup<RelativeMasterFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class RelativeMasterFormService {
  createRelativeMasterFormGroup(relativeMaster: RelativeMasterFormGroupInput = { id: null }): RelativeMasterFormGroup {
    const relativeMasterRawValue = {
      ...this.getFormDefaults(),
      ...relativeMaster,
    };
    return new FormGroup<RelativeMasterFormGroupContent>({
      id: new FormControl(
        { value: relativeMasterRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      relativeCode: new FormControl(relativeMasterRawValue.relativeCode),
      relativeName: new FormControl(relativeMasterRawValue.relativeName),
    });
  }

  getRelativeMaster(form: RelativeMasterFormGroup): IRelativeMaster | NewRelativeMaster {
    return form.getRawValue() as IRelativeMaster | NewRelativeMaster;
  }

  resetForm(form: RelativeMasterFormGroup, relativeMaster: RelativeMasterFormGroupInput): void {
    const relativeMasterRawValue = { ...this.getFormDefaults(), ...relativeMaster };
    form.reset(
      {
        ...relativeMasterRawValue,
        id: { value: relativeMasterRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): RelativeMasterFormDefaults {
    return {
      id: null,
    };
  }
}
