import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IDesignationMaster, NewDesignationMaster } from '../designation-master.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDesignationMaster for edit and NewDesignationMasterFormGroupInput for create.
 */
type DesignationMasterFormGroupInput = IDesignationMaster | PartialWithRequiredKeyOf<NewDesignationMaster>;

type DesignationMasterFormDefaults = Pick<NewDesignationMaster, 'id'>;

type DesignationMasterFormGroupContent = {
  id: FormControl<IDesignationMaster['id'] | NewDesignationMaster['id']>;
  designationCode: FormControl<IDesignationMaster['designationCode']>;
  designationName: FormControl<IDesignationMaster['designationName']>;
};

export type DesignationMasterFormGroup = FormGroup<DesignationMasterFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DesignationMasterFormService {
  createDesignationMasterFormGroup(designationMaster: DesignationMasterFormGroupInput = { id: null }): DesignationMasterFormGroup {
    const designationMasterRawValue = {
      ...this.getFormDefaults(),
      ...designationMaster,
    };
    return new FormGroup<DesignationMasterFormGroupContent>({
      id: new FormControl(
        { value: designationMasterRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      designationCode: new FormControl(designationMasterRawValue.designationCode),
      designationName: new FormControl(designationMasterRawValue.designationName),
    });
  }

  getDesignationMaster(form: DesignationMasterFormGroup): IDesignationMaster | NewDesignationMaster {
    return form.getRawValue() as IDesignationMaster | NewDesignationMaster;
  }

  resetForm(form: DesignationMasterFormGroup, designationMaster: DesignationMasterFormGroupInput): void {
    const designationMasterRawValue = { ...this.getFormDefaults(), ...designationMaster };
    form.reset(
      {
        ...designationMasterRawValue,
        id: { value: designationMasterRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): DesignationMasterFormDefaults {
    return {
      id: null,
    };
  }
}
