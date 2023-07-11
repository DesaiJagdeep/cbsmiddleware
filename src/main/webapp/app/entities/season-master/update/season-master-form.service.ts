import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ISeasonMaster, NewSeasonMaster } from '../season-master.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISeasonMaster for edit and NewSeasonMasterFormGroupInput for create.
 */
type SeasonMasterFormGroupInput = ISeasonMaster | PartialWithRequiredKeyOf<NewSeasonMaster>;

type SeasonMasterFormDefaults = Pick<NewSeasonMaster, 'id'>;

type SeasonMasterFormGroupContent = {
  id: FormControl<ISeasonMaster['id'] | NewSeasonMaster['id']>;
  seasonCode: FormControl<ISeasonMaster['seasonCode']>;
  seasonName: FormControl<ISeasonMaster['seasonName']>;
};

export type SeasonMasterFormGroup = FormGroup<SeasonMasterFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SeasonMasterFormService {
  createSeasonMasterFormGroup(seasonMaster: SeasonMasterFormGroupInput = { id: null }): SeasonMasterFormGroup {
    const seasonMasterRawValue = {
      ...this.getFormDefaults(),
      ...seasonMaster,
    };
    return new FormGroup<SeasonMasterFormGroupContent>({
      id: new FormControl(
        { value: seasonMasterRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      seasonCode: new FormControl(seasonMasterRawValue.seasonCode),
      seasonName: new FormControl(seasonMasterRawValue.seasonName),
    });
  }

  getSeasonMaster(form: SeasonMasterFormGroup): ISeasonMaster | NewSeasonMaster {
    return form.getRawValue() as ISeasonMaster | NewSeasonMaster;
  }

  resetForm(form: SeasonMasterFormGroup, seasonMaster: SeasonMasterFormGroupInput): void {
    const seasonMasterRawValue = { ...this.getFormDefaults(), ...seasonMaster };
    form.reset(
      {
        ...seasonMasterRawValue,
        id: { value: seasonMasterRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): SeasonMasterFormDefaults {
    return {
      id: null,
    };
  }
}
