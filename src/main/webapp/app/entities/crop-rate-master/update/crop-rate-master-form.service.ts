import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICropRateMaster, NewCropRateMaster } from '../crop-rate-master.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICropRateMaster for edit and NewCropRateMasterFormGroupInput for create.
 */
type CropRateMasterFormGroupInput = ICropRateMaster | PartialWithRequiredKeyOf<NewCropRateMaster>;

type CropRateMasterFormDefaults = Pick<NewCropRateMaster, 'id' | 'activeFlag'>;

type CropRateMasterFormGroupContent = {
  id: FormControl<ICropRateMaster['id'] | NewCropRateMaster['id']>;
  financialYear: FormControl<ICropRateMaster['financialYear']>;
  currentAmount: FormControl<ICropRateMaster['currentAmount']>;
  currentAmountMr: FormControl<ICropRateMaster['currentAmountMr']>;
  percentage: FormControl<ICropRateMaster['percentage']>;
  activeFlag: FormControl<ICropRateMaster['activeFlag']>;
  cropMaster: FormControl<ICropRateMaster['cropMaster']>;
};

export type CropRateMasterFormGroup = FormGroup<CropRateMasterFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CropRateMasterFormService {
  createCropRateMasterFormGroup(cropRateMaster: CropRateMasterFormGroupInput = { id: null }): CropRateMasterFormGroup {
    const cropRateMasterRawValue = {
      ...this.getFormDefaults(),
      ...cropRateMaster,
    };
    return new FormGroup<CropRateMasterFormGroupContent>({
      id: new FormControl(
        { value: cropRateMasterRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      financialYear: new FormControl(cropRateMasterRawValue.financialYear, {
        validators: [Validators.required],
      }),
      currentAmount: new FormControl(cropRateMasterRawValue.currentAmount, {
        validators: [Validators.required],
      }),
      currentAmountMr: new FormControl(cropRateMasterRawValue.currentAmountMr),
      percentage: new FormControl(cropRateMasterRawValue.percentage),
      activeFlag: new FormControl(cropRateMasterRawValue.activeFlag),
      cropMaster: new FormControl(cropRateMasterRawValue.cropMaster),
    });
  }

  getCropRateMaster(form: CropRateMasterFormGroup): ICropRateMaster | NewCropRateMaster {
    return form.getRawValue() as ICropRateMaster | NewCropRateMaster;
  }

  resetForm(form: CropRateMasterFormGroup, cropRateMaster: CropRateMasterFormGroupInput): void {
    const cropRateMasterRawValue = { ...this.getFormDefaults(), ...cropRateMaster };
    form.reset(
      {
        ...cropRateMasterRawValue,
        id: { value: cropRateMasterRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): CropRateMasterFormDefaults {
    return {
      id: null,
      activeFlag: false,
    };
  }
}
