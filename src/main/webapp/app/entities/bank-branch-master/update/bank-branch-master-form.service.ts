import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IBankBranchMaster, NewBankBranchMaster } from '../bank-branch-master.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IBankBranchMaster for edit and NewBankBranchMasterFormGroupInput for create.
 */
type BankBranchMasterFormGroupInput = IBankBranchMaster | PartialWithRequiredKeyOf<NewBankBranchMaster>;

type BankBranchMasterFormDefaults = Pick<NewBankBranchMaster, 'id'>;

type BankBranchMasterFormGroupContent = {
  id: FormControl<IBankBranchMaster['id'] | NewBankBranchMaster['id']>;
  branchCode: FormControl<IBankBranchMaster['branchCode']>;
  branchName: FormControl<IBankBranchMaster['branchName']>;
  branchAddress: FormControl<IBankBranchMaster['branchAddress']>;
  bankMaster: FormControl<IBankBranchMaster['bankMaster']>;
};

export type BankBranchMasterFormGroup = FormGroup<BankBranchMasterFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class BankBranchMasterFormService {
  createBankBranchMasterFormGroup(bankBranchMaster: BankBranchMasterFormGroupInput = { id: null }): BankBranchMasterFormGroup {
    const bankBranchMasterRawValue = {
      ...this.getFormDefaults(),
      ...bankBranchMaster,
    };
    return new FormGroup<BankBranchMasterFormGroupContent>({
      id: new FormControl(
        { value: bankBranchMasterRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      branchCode: new FormControl(bankBranchMasterRawValue.branchCode),
      branchName: new FormControl(bankBranchMasterRawValue.branchName),
      branchAddress: new FormControl(bankBranchMasterRawValue.branchAddress),
      bankMaster: new FormControl(bankBranchMasterRawValue.bankMaster),
    });
  }

  getBankBranchMaster(form: BankBranchMasterFormGroup): IBankBranchMaster | NewBankBranchMaster {
    return form.getRawValue() as IBankBranchMaster | NewBankBranchMaster;
  }

  resetForm(form: BankBranchMasterFormGroup, bankBranchMaster: BankBranchMasterFormGroupInput): void {
    const bankBranchMasterRawValue = { ...this.getFormDefaults(), ...bankBranchMaster };
    form.reset(
      {
        ...bankBranchMasterRawValue,
        id: { value: bankBranchMasterRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): BankBranchMasterFormDefaults {
    return {
      id: null,
    };
  }
}
