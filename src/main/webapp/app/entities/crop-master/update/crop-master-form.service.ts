import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICropMaster, NewCropMaster } from '../crop-master.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICropMaster for edit and NewCropMasterFormGroupInput for create.
 */
type CropMasterFormGroupInput = ICropMaster | PartialWithRequiredKeyOf<NewCropMaster>;

type CropMasterFormDefaults = Pick<NewCropMaster, 'id'>;

type CropMasterFormGroupContent = {
  id: FormControl<ICropMaster['id'] | NewCropMaster['id']>;
  cropCode: FormControl<ICropMaster['cropCode']>;
  cropName: FormControl<ICropMaster['cropName']>;
  categoryCode: FormControl<ICropMaster['categoryCode']>;
};

export type CropMasterFormGroup = FormGroup<CropMasterFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CropMasterFormService {
  createCropMasterFormGroup(cropMaster: CropMasterFormGroupInput = { id: null }): CropMasterFormGroup {
    const cropMasterRawValue = {
      ...this.getFormDefaults(),
      ...cropMaster,
    };
    return new FormGroup<CropMasterFormGroupContent>({
      id: new FormControl(
        { value: cropMasterRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      cropCode: new FormControl(cropMasterRawValue.cropCode),
      cropName: new FormControl(cropMasterRawValue.cropName),
      categoryCode: new FormControl(cropMasterRawValue.categoryCode),
    });
  }

  getCropMaster(form: CropMasterFormGroup): ICropMaster | NewCropMaster {
    return form.getRawValue() as ICropMaster | NewCropMaster;
  }

  resetForm(form: CropMasterFormGroup, cropMaster: CropMasterFormGroupInput): void {
    const cropMasterRawValue = { ...this.getFormDefaults(), ...cropMaster };
    form.reset(
      {
        ...cropMasterRawValue,
        id: { value: cropMasterRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): CropMasterFormDefaults {
    return {
      id: null,
    };
  }
}
