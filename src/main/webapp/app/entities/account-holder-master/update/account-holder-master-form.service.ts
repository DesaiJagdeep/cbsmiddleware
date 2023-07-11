import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IAccountHolderMaster, NewAccountHolderMaster } from '../account-holder-master.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAccountHolderMaster for edit and NewAccountHolderMasterFormGroupInput for create.
 */
type AccountHolderMasterFormGroupInput = IAccountHolderMaster | PartialWithRequiredKeyOf<NewAccountHolderMaster>;

type AccountHolderMasterFormDefaults = Pick<NewAccountHolderMaster, 'id'>;

type AccountHolderMasterFormGroupContent = {
  id: FormControl<IAccountHolderMaster['id'] | NewAccountHolderMaster['id']>;
  accountHolderCode: FormControl<IAccountHolderMaster['accountHolderCode']>;
  accountHolder: FormControl<IAccountHolderMaster['accountHolder']>;
};

export type AccountHolderMasterFormGroup = FormGroup<AccountHolderMasterFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AccountHolderMasterFormService {
  createAccountHolderMasterFormGroup(accountHolderMaster: AccountHolderMasterFormGroupInput = { id: null }): AccountHolderMasterFormGroup {
    const accountHolderMasterRawValue = {
      ...this.getFormDefaults(),
      ...accountHolderMaster,
    };
    return new FormGroup<AccountHolderMasterFormGroupContent>({
      id: new FormControl(
        { value: accountHolderMasterRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      accountHolderCode: new FormControl(accountHolderMasterRawValue.accountHolderCode),
      accountHolder: new FormControl(accountHolderMasterRawValue.accountHolder),
    });
  }

  getAccountHolderMaster(form: AccountHolderMasterFormGroup): IAccountHolderMaster | NewAccountHolderMaster {
    return form.getRawValue() as IAccountHolderMaster | NewAccountHolderMaster;
  }

  resetForm(form: AccountHolderMasterFormGroup, accountHolderMaster: AccountHolderMasterFormGroupInput): void {
    const accountHolderMasterRawValue = { ...this.getFormDefaults(), ...accountHolderMaster };
    form.reset(
      {
        ...accountHolderMasterRawValue,
        id: { value: accountHolderMasterRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): AccountHolderMasterFormDefaults {
    return {
      id: null,
    };
  }
}
