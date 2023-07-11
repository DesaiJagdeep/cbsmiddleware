import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IDistrictMaster, NewDistrictMaster } from '../district-master.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDistrictMaster for edit and NewDistrictMasterFormGroupInput for create.
 */
type DistrictMasterFormGroupInput = IDistrictMaster | PartialWithRequiredKeyOf<NewDistrictMaster>;

type DistrictMasterFormDefaults = Pick<NewDistrictMaster, 'id'>;

type DistrictMasterFormGroupContent = {
  id: FormControl<IDistrictMaster['id'] | NewDistrictMaster['id']>;
  districtCode: FormControl<IDistrictMaster['districtCode']>;
  districtName: FormControl<IDistrictMaster['districtName']>;
  stateCode: FormControl<IDistrictMaster['stateCode']>;
};

export type DistrictMasterFormGroup = FormGroup<DistrictMasterFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DistrictMasterFormService {
  createDistrictMasterFormGroup(districtMaster: DistrictMasterFormGroupInput = { id: null }): DistrictMasterFormGroup {
    const districtMasterRawValue = {
      ...this.getFormDefaults(),
      ...districtMaster,
    };
    return new FormGroup<DistrictMasterFormGroupContent>({
      id: new FormControl(
        { value: districtMasterRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      districtCode: new FormControl(districtMasterRawValue.districtCode),
      districtName: new FormControl(districtMasterRawValue.districtName),
      stateCode: new FormControl(districtMasterRawValue.stateCode),
    });
  }

  getDistrictMaster(form: DistrictMasterFormGroup): IDistrictMaster | NewDistrictMaster {
    return form.getRawValue() as IDistrictMaster | NewDistrictMaster;
  }

  resetForm(form: DistrictMasterFormGroup, districtMaster: DistrictMasterFormGroupInput): void {
    const districtMasterRawValue = { ...this.getFormDefaults(), ...districtMaster };
    form.reset(
      {
        ...districtMasterRawValue,
        id: { value: districtMasterRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): DistrictMasterFormDefaults {
    return {
      id: null,
    };
  }
}
