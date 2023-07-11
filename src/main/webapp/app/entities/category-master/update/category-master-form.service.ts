import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICategoryMaster, NewCategoryMaster } from '../category-master.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICategoryMaster for edit and NewCategoryMasterFormGroupInput for create.
 */
type CategoryMasterFormGroupInput = ICategoryMaster | PartialWithRequiredKeyOf<NewCategoryMaster>;

type CategoryMasterFormDefaults = Pick<NewCategoryMaster, 'id'>;

type CategoryMasterFormGroupContent = {
  id: FormControl<ICategoryMaster['id'] | NewCategoryMaster['id']>;
  castName: FormControl<ICategoryMaster['castName']>;
  castCode: FormControl<ICategoryMaster['castCode']>;
  castCategoryCode: FormControl<ICategoryMaster['castCategoryCode']>;
};

export type CategoryMasterFormGroup = FormGroup<CategoryMasterFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CategoryMasterFormService {
  createCategoryMasterFormGroup(categoryMaster: CategoryMasterFormGroupInput = { id: null }): CategoryMasterFormGroup {
    const categoryMasterRawValue = {
      ...this.getFormDefaults(),
      ...categoryMaster,
    };
    return new FormGroup<CategoryMasterFormGroupContent>({
      id: new FormControl(
        { value: categoryMasterRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      castName: new FormControl(categoryMasterRawValue.castName),
      castCode: new FormControl(categoryMasterRawValue.castCode),
      castCategoryCode: new FormControl(categoryMasterRawValue.castCategoryCode),
    });
  }

  getCategoryMaster(form: CategoryMasterFormGroup): ICategoryMaster | NewCategoryMaster {
    return form.getRawValue() as ICategoryMaster | NewCategoryMaster;
  }

  resetForm(form: CategoryMasterFormGroup, categoryMaster: CategoryMasterFormGroupInput): void {
    const categoryMasterRawValue = { ...this.getFormDefaults(), ...categoryMaster };
    form.reset(
      {
        ...categoryMasterRawValue,
        id: { value: categoryMasterRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): CategoryMasterFormDefaults {
    return {
      id: null,
    };
  }
}
