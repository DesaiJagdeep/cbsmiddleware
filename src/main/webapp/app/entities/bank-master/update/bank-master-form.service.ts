import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IBankMaster, NewBankMaster } from '../bank-master.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IBankMaster for edit and NewBankMasterFormGroupInput for create.
 */
type BankMasterFormGroupInput = IBankMaster | PartialWithRequiredKeyOf<NewBankMaster>;

type BankMasterFormDefaults = Pick<NewBankMaster, 'id'>;

type BankMasterFormGroupContent = {
  id: FormControl<IBankMaster['id'] | NewBankMaster['id']>;
  bankCode: FormControl<IBankMaster['bankCode']>;
  bankName: FormControl<IBankMaster['bankName']>;
};

export type BankMasterFormGroup = FormGroup<BankMasterFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class BankMasterFormService {
  createBankMasterFormGroup(bankMaster: BankMasterFormGroupInput = { id: null }): BankMasterFormGroup {
    const bankMasterRawValue = {
      ...this.getFormDefaults(),
      ...bankMaster,
    };
    return new FormGroup<BankMasterFormGroupContent>({
      id: new FormControl(
        { value: bankMasterRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      bankCode: new FormControl(bankMasterRawValue.bankCode),
      bankName: new FormControl(bankMasterRawValue.bankName),
    });
  }

  getBankMaster(form: BankMasterFormGroup): IBankMaster | NewBankMaster {
    return form.getRawValue() as IBankMaster | NewBankMaster;
  }

  resetForm(form: BankMasterFormGroup, bankMaster: BankMasterFormGroupInput): void {
    const bankMasterRawValue = { ...this.getFormDefaults(), ...bankMaster };
    form.reset(
      {
        ...bankMasterRawValue,
        id: { value: bankMasterRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): BankMasterFormDefaults {
    return {
      id: null,
    };
  }
}
