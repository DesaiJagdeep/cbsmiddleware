import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IFarmerCategoryMaster, NewFarmerCategoryMaster } from '../farmer-category-master.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IFarmerCategoryMaster for edit and NewFarmerCategoryMasterFormGroupInput for create.
 */
type FarmerCategoryMasterFormGroupInput = IFarmerCategoryMaster | PartialWithRequiredKeyOf<NewFarmerCategoryMaster>;

type FarmerCategoryMasterFormDefaults = Pick<NewFarmerCategoryMaster, 'id'>;

type FarmerCategoryMasterFormGroupContent = {
  id: FormControl<IFarmerCategoryMaster['id'] | NewFarmerCategoryMaster['id']>;
  farmerCategoryCode: FormControl<IFarmerCategoryMaster['farmerCategoryCode']>;
  farmerCategory: FormControl<IFarmerCategoryMaster['farmerCategory']>;
};

export type FarmerCategoryMasterFormGroup = FormGroup<FarmerCategoryMasterFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class FarmerCategoryMasterFormService {
  createFarmerCategoryMasterFormGroup(
    farmerCategoryMaster: FarmerCategoryMasterFormGroupInput = { id: null }
  ): FarmerCategoryMasterFormGroup {
    const farmerCategoryMasterRawValue = {
      ...this.getFormDefaults(),
      ...farmerCategoryMaster,
    };
    return new FormGroup<FarmerCategoryMasterFormGroupContent>({
      id: new FormControl(
        { value: farmerCategoryMasterRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      farmerCategoryCode: new FormControl(farmerCategoryMasterRawValue.farmerCategoryCode),
      farmerCategory: new FormControl(farmerCategoryMasterRawValue.farmerCategory),
    });
  }

  getFarmerCategoryMaster(form: FarmerCategoryMasterFormGroup): IFarmerCategoryMaster | NewFarmerCategoryMaster {
    return form.getRawValue() as IFarmerCategoryMaster | NewFarmerCategoryMaster;
  }

  resetForm(form: FarmerCategoryMasterFormGroup, farmerCategoryMaster: FarmerCategoryMasterFormGroupInput): void {
    const farmerCategoryMasterRawValue = { ...this.getFormDefaults(), ...farmerCategoryMaster };
    form.reset(
      {
        ...farmerCategoryMasterRawValue,
        id: { value: farmerCategoryMasterRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): FarmerCategoryMasterFormDefaults {
    return {
      id: null,
    };
  }
}
