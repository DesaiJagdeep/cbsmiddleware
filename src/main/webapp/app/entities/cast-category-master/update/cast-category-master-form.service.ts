import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICastCategoryMaster, NewCastCategoryMaster } from '../cast-category-master.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICastCategoryMaster for edit and NewCastCategoryMasterFormGroupInput for create.
 */
type CastCategoryMasterFormGroupInput = ICastCategoryMaster | PartialWithRequiredKeyOf<NewCastCategoryMaster>;

type CastCategoryMasterFormDefaults = Pick<NewCastCategoryMaster, 'id'>;

type CastCategoryMasterFormGroupContent = {
  id: FormControl<ICastCategoryMaster['id'] | NewCastCategoryMaster['id']>;
  castCategoryCode: FormControl<ICastCategoryMaster['castCategoryCode']>;
  castCategoryName: FormControl<ICastCategoryMaster['castCategoryName']>;
};

export type CastCategoryMasterFormGroup = FormGroup<CastCategoryMasterFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CastCategoryMasterFormService {
  createCastCategoryMasterFormGroup(castCategoryMaster: CastCategoryMasterFormGroupInput = { id: null }): CastCategoryMasterFormGroup {
    const castCategoryMasterRawValue = {
      ...this.getFormDefaults(),
      ...castCategoryMaster,
    };
    return new FormGroup<CastCategoryMasterFormGroupContent>({
      id: new FormControl(
        { value: castCategoryMasterRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      castCategoryCode: new FormControl(castCategoryMasterRawValue.castCategoryCode),
      castCategoryName: new FormControl(castCategoryMasterRawValue.castCategoryName),
    });
  }

  getCastCategoryMaster(form: CastCategoryMasterFormGroup): ICastCategoryMaster | NewCastCategoryMaster {
    return form.getRawValue() as ICastCategoryMaster | NewCastCategoryMaster;
  }

  resetForm(form: CastCategoryMasterFormGroup, castCategoryMaster: CastCategoryMasterFormGroupInput): void {
    const castCategoryMasterRawValue = { ...this.getFormDefaults(), ...castCategoryMaster };
    form.reset(
      {
        ...castCategoryMasterRawValue,
        id: { value: castCategoryMasterRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): CastCategoryMasterFormDefaults {
    return {
      id: null,
    };
  }
}
