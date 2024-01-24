import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICropHangam, NewCropHangam } from '../crop-hangam.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICropHangam for edit and NewCropHangamFormGroupInput for create.
 */
type CropHangamFormGroupInput = ICropHangam | PartialWithRequiredKeyOf<NewCropHangam>;

type CropHangamFormDefaults = Pick<NewCropHangam, 'id'>;

type CropHangamFormGroupContent = {
  id: FormControl<ICropHangam['id'] | NewCropHangam['id']>;
  hangam: FormControl<ICropHangam['hangam']>;
  hangamMr: FormControl<ICropHangam['hangamMr']>;
};

export type CropHangamFormGroup = FormGroup<CropHangamFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CropHangamFormService {
  createCropHangamFormGroup(cropHangam: CropHangamFormGroupInput = { id: null }): CropHangamFormGroup {
    const cropHangamRawValue = {
      ...this.getFormDefaults(),
      ...cropHangam,
    };
    return new FormGroup<CropHangamFormGroupContent>({
      id: new FormControl(
        { value: cropHangamRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      hangam: new FormControl(cropHangamRawValue.hangam),
      hangamMr: new FormControl(cropHangamRawValue.hangamMr),
    });
  }

  getCropHangam(form: CropHangamFormGroup): ICropHangam | NewCropHangam {
    return form.getRawValue() as ICropHangam | NewCropHangam;
  }

  resetForm(form: CropHangamFormGroup, cropHangam: CropHangamFormGroupInput): void {
    const cropHangamRawValue = { ...this.getFormDefaults(), ...cropHangam };
    form.reset(
      {
        ...cropHangamRawValue,
        id: { value: cropHangamRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): CropHangamFormDefaults {
    return {
      id: null,
    };
  }
}
