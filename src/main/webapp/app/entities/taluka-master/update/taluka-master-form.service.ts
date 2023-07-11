import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ITalukaMaster, NewTalukaMaster } from '../taluka-master.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITalukaMaster for edit and NewTalukaMasterFormGroupInput for create.
 */
type TalukaMasterFormGroupInput = ITalukaMaster | PartialWithRequiredKeyOf<NewTalukaMaster>;

type TalukaMasterFormDefaults = Pick<NewTalukaMaster, 'id'>;

type TalukaMasterFormGroupContent = {
  id: FormControl<ITalukaMaster['id'] | NewTalukaMaster['id']>;
  talukaCode: FormControl<ITalukaMaster['talukaCode']>;
  talukaName: FormControl<ITalukaMaster['talukaName']>;
  districtCode: FormControl<ITalukaMaster['districtCode']>;
};

export type TalukaMasterFormGroup = FormGroup<TalukaMasterFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TalukaMasterFormService {
  createTalukaMasterFormGroup(talukaMaster: TalukaMasterFormGroupInput = { id: null }): TalukaMasterFormGroup {
    const talukaMasterRawValue = {
      ...this.getFormDefaults(),
      ...talukaMaster,
    };
    return new FormGroup<TalukaMasterFormGroupContent>({
      id: new FormControl(
        { value: talukaMasterRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      talukaCode: new FormControl(talukaMasterRawValue.talukaCode),
      talukaName: new FormControl(talukaMasterRawValue.talukaName),
      districtCode: new FormControl(talukaMasterRawValue.districtCode),
    });
  }

  getTalukaMaster(form: TalukaMasterFormGroup): ITalukaMaster | NewTalukaMaster {
    return form.getRawValue() as ITalukaMaster | NewTalukaMaster;
  }

  resetForm(form: TalukaMasterFormGroup, talukaMaster: TalukaMasterFormGroupInput): void {
    const talukaMasterRawValue = { ...this.getFormDefaults(), ...talukaMaster };
    form.reset(
      {
        ...talukaMasterRawValue,
        id: { value: talukaMasterRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): TalukaMasterFormDefaults {
    return {
      id: null,
    };
  }
}
