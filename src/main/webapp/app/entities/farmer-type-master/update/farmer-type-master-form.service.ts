import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IFarmerTypeMaster, NewFarmerTypeMaster } from '../farmer-type-master.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IFarmerTypeMaster for edit and NewFarmerTypeMasterFormGroupInput for create.
 */
type FarmerTypeMasterFormGroupInput = IFarmerTypeMaster | PartialWithRequiredKeyOf<NewFarmerTypeMaster>;

type FarmerTypeMasterFormDefaults = Pick<NewFarmerTypeMaster, 'id'>;

type FarmerTypeMasterFormGroupContent = {
  id: FormControl<IFarmerTypeMaster['id'] | NewFarmerTypeMaster['id']>;
  farmerTypeCode: FormControl<IFarmerTypeMaster['farmerTypeCode']>;
  farmerType: FormControl<IFarmerTypeMaster['farmerType']>;
};

export type FarmerTypeMasterFormGroup = FormGroup<FarmerTypeMasterFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class FarmerTypeMasterFormService {
  createFarmerTypeMasterFormGroup(farmerTypeMaster: FarmerTypeMasterFormGroupInput = { id: null }): FarmerTypeMasterFormGroup {
    const farmerTypeMasterRawValue = {
      ...this.getFormDefaults(),
      ...farmerTypeMaster,
    };
    return new FormGroup<FarmerTypeMasterFormGroupContent>({
      id: new FormControl(
        { value: farmerTypeMasterRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      farmerTypeCode: new FormControl(farmerTypeMasterRawValue.farmerTypeCode),
      farmerType: new FormControl(farmerTypeMasterRawValue.farmerType),
    });
  }

  getFarmerTypeMaster(form: FarmerTypeMasterFormGroup): IFarmerTypeMaster | NewFarmerTypeMaster {
    return form.getRawValue() as IFarmerTypeMaster | NewFarmerTypeMaster;
  }

  resetForm(form: FarmerTypeMasterFormGroup, farmerTypeMaster: FarmerTypeMasterFormGroupInput): void {
    const farmerTypeMasterRawValue = { ...this.getFormDefaults(), ...farmerTypeMaster };
    form.reset(
      {
        ...farmerTypeMasterRawValue,
        id: { value: farmerTypeMasterRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): FarmerTypeMasterFormDefaults {
    return {
      id: null,
    };
  }
}
