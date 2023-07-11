import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IStateMaster, NewStateMaster } from '../state-master.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IStateMaster for edit and NewStateMasterFormGroupInput for create.
 */
type StateMasterFormGroupInput = IStateMaster | PartialWithRequiredKeyOf<NewStateMaster>;

type StateMasterFormDefaults = Pick<NewStateMaster, 'id'>;

type StateMasterFormGroupContent = {
  id: FormControl<IStateMaster['id'] | NewStateMaster['id']>;
  stateCode: FormControl<IStateMaster['stateCode']>;
  stateName: FormControl<IStateMaster['stateName']>;
};

export type StateMasterFormGroup = FormGroup<StateMasterFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class StateMasterFormService {
  createStateMasterFormGroup(stateMaster: StateMasterFormGroupInput = { id: null }): StateMasterFormGroup {
    const stateMasterRawValue = {
      ...this.getFormDefaults(),
      ...stateMaster,
    };
    return new FormGroup<StateMasterFormGroupContent>({
      id: new FormControl(
        { value: stateMasterRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      stateCode: new FormControl(stateMasterRawValue.stateCode),
      stateName: new FormControl(stateMasterRawValue.stateName),
    });
  }

  getStateMaster(form: StateMasterFormGroup): IStateMaster | NewStateMaster {
    return form.getRawValue() as IStateMaster | NewStateMaster;
  }

  resetForm(form: StateMasterFormGroup, stateMaster: StateMasterFormGroupInput): void {
    const stateMasterRawValue = { ...this.getFormDefaults(), ...stateMaster };
    form.reset(
      {
        ...stateMasterRawValue,
        id: { value: stateMasterRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): StateMasterFormDefaults {
    return {
      id: null,
    };
  }
}
