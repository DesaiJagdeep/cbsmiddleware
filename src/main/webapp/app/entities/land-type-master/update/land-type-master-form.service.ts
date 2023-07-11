import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ILandTypeMaster, NewLandTypeMaster } from '../land-type-master.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ILandTypeMaster for edit and NewLandTypeMasterFormGroupInput for create.
 */
type LandTypeMasterFormGroupInput = ILandTypeMaster | PartialWithRequiredKeyOf<NewLandTypeMaster>;

type LandTypeMasterFormDefaults = Pick<NewLandTypeMaster, 'id'>;

type LandTypeMasterFormGroupContent = {
  id: FormControl<ILandTypeMaster['id'] | NewLandTypeMaster['id']>;
  landTypeCode: FormControl<ILandTypeMaster['landTypeCode']>;
  landType: FormControl<ILandTypeMaster['landType']>;
};

export type LandTypeMasterFormGroup = FormGroup<LandTypeMasterFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class LandTypeMasterFormService {
  createLandTypeMasterFormGroup(landTypeMaster: LandTypeMasterFormGroupInput = { id: null }): LandTypeMasterFormGroup {
    const landTypeMasterRawValue = {
      ...this.getFormDefaults(),
      ...landTypeMaster,
    };
    return new FormGroup<LandTypeMasterFormGroupContent>({
      id: new FormControl(
        { value: landTypeMasterRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      landTypeCode: new FormControl(landTypeMasterRawValue.landTypeCode),
      landType: new FormControl(landTypeMasterRawValue.landType),
    });
  }

  getLandTypeMaster(form: LandTypeMasterFormGroup): ILandTypeMaster | NewLandTypeMaster {
    return form.getRawValue() as ILandTypeMaster | NewLandTypeMaster;
  }

  resetForm(form: LandTypeMasterFormGroup, landTypeMaster: LandTypeMasterFormGroupInput): void {
    const landTypeMasterRawValue = { ...this.getFormDefaults(), ...landTypeMaster };
    form.reset(
      {
        ...landTypeMasterRawValue,
        id: { value: landTypeMasterRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): LandTypeMasterFormDefaults {
    return {
      id: null,
    };
  }
}
