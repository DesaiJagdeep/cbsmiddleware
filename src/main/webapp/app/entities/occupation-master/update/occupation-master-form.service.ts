import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IOccupationMaster, NewOccupationMaster } from '../occupation-master.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IOccupationMaster for edit and NewOccupationMasterFormGroupInput for create.
 */
type OccupationMasterFormGroupInput = IOccupationMaster | PartialWithRequiredKeyOf<NewOccupationMaster>;

type OccupationMasterFormDefaults = Pick<NewOccupationMaster, 'id'>;

type OccupationMasterFormGroupContent = {
  id: FormControl<IOccupationMaster['id'] | NewOccupationMaster['id']>;
  occupationCode: FormControl<IOccupationMaster['occupationCode']>;
  occupationName: FormControl<IOccupationMaster['occupationName']>;
};

export type OccupationMasterFormGroup = FormGroup<OccupationMasterFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class OccupationMasterFormService {
  createOccupationMasterFormGroup(occupationMaster: OccupationMasterFormGroupInput = { id: null }): OccupationMasterFormGroup {
    const occupationMasterRawValue = {
      ...this.getFormDefaults(),
      ...occupationMaster,
    };
    return new FormGroup<OccupationMasterFormGroupContent>({
      id: new FormControl(
        { value: occupationMasterRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      occupationCode: new FormControl(occupationMasterRawValue.occupationCode),
      occupationName: new FormControl(occupationMasterRawValue.occupationName),
    });
  }

  getOccupationMaster(form: OccupationMasterFormGroup): IOccupationMaster | NewOccupationMaster {
    return form.getRawValue() as IOccupationMaster | NewOccupationMaster;
  }

  resetForm(form: OccupationMasterFormGroup, occupationMaster: OccupationMasterFormGroupInput): void {
    const occupationMasterRawValue = { ...this.getFormDefaults(), ...occupationMaster };
    form.reset(
      {
        ...occupationMasterRawValue,
        id: { value: occupationMasterRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): OccupationMasterFormDefaults {
    return {
      id: null,
    };
  }
}
