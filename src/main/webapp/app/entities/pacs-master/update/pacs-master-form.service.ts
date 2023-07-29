import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPacsMaster, NewPacsMaster } from '../pacs-master.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPacsMaster for edit and NewPacsMasterFormGroupInput for create.
 */
type PacsMasterFormGroupInput = IPacsMaster | PartialWithRequiredKeyOf<NewPacsMaster>;

type PacsMasterFormDefaults = Pick<NewPacsMaster, 'id'>;

type PacsMasterFormGroupContent = {
  id: FormControl<IPacsMaster['id'] | NewPacsMaster['id']>;
  pacsName: FormControl<IPacsMaster['pacsName']>;
  pacsNumber: FormControl<IPacsMaster['pacsNumber']>;
  bankBranchMaster: FormControl<IPacsMaster['bankBranchMaster']>;
};

export type PacsMasterFormGroup = FormGroup<PacsMasterFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PacsMasterFormService {
  createPacsMasterFormGroup(pacsMaster: PacsMasterFormGroupInput = { id: null }): PacsMasterFormGroup {
    const pacsMasterRawValue = {
      ...this.getFormDefaults(),
      ...pacsMaster,
    };
    return new FormGroup<PacsMasterFormGroupContent>({
      id: new FormControl(
        { value: pacsMasterRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      pacsName: new FormControl(pacsMasterRawValue.pacsName),
      pacsNumber: new FormControl(pacsMasterRawValue.pacsNumber),
      bankBranchMaster: new FormControl(pacsMasterRawValue.bankBranchMaster),
    });
  }

  getPacsMaster(form: PacsMasterFormGroup): IPacsMaster | NewPacsMaster {
    return form.getRawValue() as IPacsMaster | NewPacsMaster;
  }

  resetForm(form: PacsMasterFormGroup, pacsMaster: PacsMasterFormGroupInput): void {
    const pacsMasterRawValue = { ...this.getFormDefaults(), ...pacsMaster };
    form.reset(
      {
        ...pacsMasterRawValue,
        id: { value: pacsMasterRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): PacsMasterFormDefaults {
    return {
      id: null,
    };
  }
}
