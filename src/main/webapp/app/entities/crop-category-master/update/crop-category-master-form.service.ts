import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICropCategoryMaster, NewCropCategoryMaster } from '../crop-category-master.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICropCategoryMaster for edit and NewCropCategoryMasterFormGroupInput for create.
 */
type CropCategoryMasterFormGroupInput = ICropCategoryMaster | PartialWithRequiredKeyOf<NewCropCategoryMaster>;

type CropCategoryMasterFormDefaults = Pick<NewCropCategoryMaster, 'id'>;

type CropCategoryMasterFormGroupContent = {
  id: FormControl<ICropCategoryMaster['id'] | NewCropCategoryMaster['id']>;
  categoryCode: FormControl<ICropCategoryMaster['categoryCode']>;
  categoryName: FormControl<ICropCategoryMaster['categoryName']>;
};

export type CropCategoryMasterFormGroup = FormGroup<CropCategoryMasterFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CropCategoryMasterFormService {
  createCropCategoryMasterFormGroup(cropCategoryMaster: CropCategoryMasterFormGroupInput = { id: null }): CropCategoryMasterFormGroup {
    const cropCategoryMasterRawValue = {
      ...this.getFormDefaults(),
      ...cropCategoryMaster,
    };
    return new FormGroup<CropCategoryMasterFormGroupContent>({
      id: new FormControl(
        { value: cropCategoryMasterRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      categoryCode: new FormControl(cropCategoryMasterRawValue.categoryCode),
      categoryName: new FormControl(cropCategoryMasterRawValue.categoryName),
    });
  }

  getCropCategoryMaster(form: CropCategoryMasterFormGroup): ICropCategoryMaster | NewCropCategoryMaster {
    return form.getRawValue() as ICropCategoryMaster | NewCropCategoryMaster;
  }

  resetForm(form: CropCategoryMasterFormGroup, cropCategoryMaster: CropCategoryMasterFormGroupInput): void {
    const cropCategoryMasterRawValue = { ...this.getFormDefaults(), ...cropCategoryMaster };
    form.reset(
      {
        ...cropCategoryMasterRawValue,
        id: { value: cropCategoryMasterRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): CropCategoryMasterFormDefaults {
    return {
      id: null,
    };
  }
}
