import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IVillageMaster, NewVillageMaster } from '../village-master.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IVillageMaster for edit and NewVillageMasterFormGroupInput for create.
 */
type VillageMasterFormGroupInput = IVillageMaster | PartialWithRequiredKeyOf<NewVillageMaster>;

type VillageMasterFormDefaults = Pick<NewVillageMaster, 'id'>;

type VillageMasterFormGroupContent = {
  id: FormControl<IVillageMaster['id'] | NewVillageMaster['id']>;
  villageName: FormControl<IVillageMaster['villageName']>;
  villageNameMr: FormControl<IVillageMaster['villageNameMr']>;
  villageCode: FormControl<IVillageMaster['villageCode']>;
  villageCodeMr: FormControl<IVillageMaster['villageCodeMr']>;
  talukaMaster: FormControl<IVillageMaster['talukaMaster']>;
};

export type VillageMasterFormGroup = FormGroup<VillageMasterFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class VillageMasterFormService {
  createVillageMasterFormGroup(villageMaster: VillageMasterFormGroupInput = { id: null }): VillageMasterFormGroup {
    const villageMasterRawValue = {
      ...this.getFormDefaults(),
      ...villageMaster,
    };
    return new FormGroup<VillageMasterFormGroupContent>({
      id: new FormControl(
        { value: villageMasterRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      villageName: new FormControl(villageMasterRawValue.villageName, {
        validators: [Validators.required],
      }),
      villageNameMr: new FormControl(villageMasterRawValue.villageNameMr),
      villageCode: new FormControl(villageMasterRawValue.villageCode),
      villageCodeMr: new FormControl(villageMasterRawValue.villageCodeMr),
      talukaMaster: new FormControl(villageMasterRawValue.talukaMaster),
    });
  }

  getVillageMaster(form: VillageMasterFormGroup): IVillageMaster | NewVillageMaster {
    return form.getRawValue() as IVillageMaster | NewVillageMaster;
  }

  resetForm(form: VillageMasterFormGroup, villageMaster: VillageMasterFormGroupInput): void {
    const villageMasterRawValue = { ...this.getFormDefaults(), ...villageMaster };
    form.reset(
      {
        ...villageMasterRawValue,
        id: { value: villageMasterRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): VillageMasterFormDefaults {
    return {
      id: null,
    };
  }
}
