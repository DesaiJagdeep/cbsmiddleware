import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IIsCalculateTemp, NewIsCalculateTemp } from '../is-calculate-temp.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IIsCalculateTemp for edit and NewIsCalculateTempFormGroupInput for create.
 */
type IsCalculateTempFormGroupInput = IIsCalculateTemp | PartialWithRequiredKeyOf<NewIsCalculateTemp>;

type IsCalculateTempFormDefaults = Pick<NewIsCalculateTemp, 'id'>;

type IsCalculateTempFormGroupContent = {
  id: FormControl<IIsCalculateTemp['id'] | NewIsCalculateTemp['id']>;
  serialNo: FormControl<IIsCalculateTemp['serialNo']>;
  financialYear: FormControl<IIsCalculateTemp['financialYear']>;
};

export type IsCalculateTempFormGroup = FormGroup<IsCalculateTempFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class IsCalculateTempFormService {
  createIsCalculateTempFormGroup(isCalculateTemp: IsCalculateTempFormGroupInput = { id: null }): IsCalculateTempFormGroup {
    const isCalculateTempRawValue = {
      ...this.getFormDefaults(),
      ...isCalculateTemp,
    };
    return new FormGroup<IsCalculateTempFormGroupContent>({
      id: new FormControl(
        { value: isCalculateTempRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      serialNo: new FormControl(isCalculateTempRawValue.serialNo),
      financialYear: new FormControl(isCalculateTempRawValue.financialYear),
    });
  }

  getIsCalculateTemp(form: IsCalculateTempFormGroup): IIsCalculateTemp | NewIsCalculateTemp {
    return form.getRawValue() as IIsCalculateTemp | NewIsCalculateTemp;
  }

  resetForm(form: IsCalculateTempFormGroup, isCalculateTemp: IsCalculateTempFormGroupInput): void {
    const isCalculateTempRawValue = { ...this.getFormDefaults(), ...isCalculateTemp };
    form.reset(
      {
        ...isCalculateTempRawValue,
        id: { value: isCalculateTempRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): IsCalculateTempFormDefaults {
    return {
      id: null,
    };
  }
}
