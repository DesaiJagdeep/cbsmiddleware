import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IFactoryMaster, NewFactoryMaster } from '../factory-master.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IFactoryMaster for edit and NewFactoryMasterFormGroupInput for create.
 */
type FactoryMasterFormGroupInput = IFactoryMaster | PartialWithRequiredKeyOf<NewFactoryMaster>;

type FactoryMasterFormDefaults = Pick<NewFactoryMaster, 'id' | 'status'>;

type FactoryMasterFormGroupContent = {
  id: FormControl<IFactoryMaster['id'] | NewFactoryMaster['id']>;
  factoryName: FormControl<IFactoryMaster['factoryName']>;
  factoryNameMr: FormControl<IFactoryMaster['factoryNameMr']>;
  factoryCode: FormControl<IFactoryMaster['factoryCode']>;
  factoryCodeMr: FormControl<IFactoryMaster['factoryCodeMr']>;
  factoryAddress: FormControl<IFactoryMaster['factoryAddress']>;
  factoryAddressMr: FormControl<IFactoryMaster['factoryAddressMr']>;
  description: FormControl<IFactoryMaster['description']>;
  status: FormControl<IFactoryMaster['status']>;
};

export type FactoryMasterFormGroup = FormGroup<FactoryMasterFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class FactoryMasterFormService {
  createFactoryMasterFormGroup(factoryMaster: FactoryMasterFormGroupInput = { id: null }): FactoryMasterFormGroup {
    const factoryMasterRawValue = {
      ...this.getFormDefaults(),
      ...factoryMaster,
    };
    return new FormGroup<FactoryMasterFormGroupContent>({
      id: new FormControl(
        { value: factoryMasterRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      factoryName: new FormControl(factoryMasterRawValue.factoryName),
      factoryNameMr: new FormControl(factoryMasterRawValue.factoryNameMr),
      factoryCode: new FormControl(factoryMasterRawValue.factoryCode),
      factoryCodeMr: new FormControl(factoryMasterRawValue.factoryCodeMr),
      factoryAddress: new FormControl(factoryMasterRawValue.factoryAddress),
      factoryAddressMr: new FormControl(factoryMasterRawValue.factoryAddressMr),
      description: new FormControl(factoryMasterRawValue.description),
      status: new FormControl(factoryMasterRawValue.status),
    });
  }

  getFactoryMaster(form: FactoryMasterFormGroup): IFactoryMaster | NewFactoryMaster {
    return form.getRawValue() as IFactoryMaster | NewFactoryMaster;
  }

  resetForm(form: FactoryMasterFormGroup, factoryMaster: FactoryMasterFormGroupInput): void {
    const factoryMasterRawValue = { ...this.getFormDefaults(), ...factoryMaster };
    form.reset(
      {
        ...factoryMasterRawValue,
        id: { value: factoryMasterRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): FactoryMasterFormDefaults {
    return {
      id: null,
      status: false,
    };
  }
}
