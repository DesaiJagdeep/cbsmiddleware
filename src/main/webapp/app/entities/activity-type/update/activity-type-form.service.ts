import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IActivityType, NewActivityType } from '../activity-type.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IActivityType for edit and NewActivityTypeFormGroupInput for create.
 */
type ActivityTypeFormGroupInput = IActivityType | PartialWithRequiredKeyOf<NewActivityType>;

type ActivityTypeFormDefaults = Pick<NewActivityType, 'id'>;

type ActivityTypeFormGroupContent = {
  id: FormControl<IActivityType['id'] | NewActivityType['id']>;
  activityType: FormControl<IActivityType['activityType']>;
  activityTypeCode: FormControl<IActivityType['activityTypeCode']>;
};

export type ActivityTypeFormGroup = FormGroup<ActivityTypeFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ActivityTypeFormService {
  createActivityTypeFormGroup(activityType: ActivityTypeFormGroupInput = { id: null }): ActivityTypeFormGroup {
    const activityTypeRawValue = {
      ...this.getFormDefaults(),
      ...activityType,
    };
    return new FormGroup<ActivityTypeFormGroupContent>({
      id: new FormControl(
        { value: activityTypeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      activityType: new FormControl(activityTypeRawValue.activityType),
      activityTypeCode: new FormControl(activityTypeRawValue.activityTypeCode),
    });
  }

  getActivityType(form: ActivityTypeFormGroup): IActivityType | NewActivityType {
    return form.getRawValue() as IActivityType | NewActivityType;
  }

  resetForm(form: ActivityTypeFormGroup, activityType: ActivityTypeFormGroupInput): void {
    const activityTypeRawValue = { ...this.getFormDefaults(), ...activityType };
    form.reset(
      {
        ...activityTypeRawValue,
        id: { value: activityTypeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ActivityTypeFormDefaults {
    return {
      id: null,
    };
  }
}
