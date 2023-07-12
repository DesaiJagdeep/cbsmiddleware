import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IIssPortalFile, NewIssPortalFile } from '../iss-portal-file.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IIssPortalFile for edit and NewIssPortalFileFormGroupInput for create.
 */
type IssPortalFileFormGroupInput = IIssPortalFile | PartialWithRequiredKeyOf<NewIssPortalFile>;

type IssPortalFileFormDefaults = Pick<NewIssPortalFile, 'id'>;

type IssPortalFileFormGroupContent = {
  id: FormControl<IIssPortalFile['id'] | NewIssPortalFile['id']>;
  fileName: FormControl<IIssPortalFile['fileName']>;
  fileExtension: FormControl<IIssPortalFile['fileExtension']>;
  branchCode: FormControl<IIssPortalFile['branchCode']>;
  financialYear: FormControl<IIssPortalFile['financialYear']>;
  fromDisbursementDate: FormControl<IIssPortalFile['fromDisbursementDate']>;
  toDisbursementDate: FormControl<IIssPortalFile['toDisbursementDate']>;
  pacsCode: FormControl<IIssPortalFile['pacsCode']>;
  status: FormControl<IIssPortalFile['status']>;
  applicationCount: FormControl<IIssPortalFile['applicationCount']>;
  notes: FormControl<IIssPortalFile['notes']>;
};

export type IssPortalFileFormGroup = FormGroup<IssPortalFileFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class IssPortalFileFormService {
  createIssPortalFileFormGroup(issPortalFile: IssPortalFileFormGroupInput = { id: null }): IssPortalFileFormGroup {
    const issPortalFileRawValue = {
      ...this.getFormDefaults(),
      ...issPortalFile,
    };
    return new FormGroup<IssPortalFileFormGroupContent>({
      id: new FormControl(
        { value: issPortalFileRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      fileName: new FormControl(issPortalFileRawValue.fileName),
      fileExtension: new FormControl(issPortalFileRawValue.fileExtension),
      branchCode: new FormControl(issPortalFileRawValue.branchCode),
      financialYear: new FormControl(issPortalFileRawValue.financialYear),
      fromDisbursementDate: new FormControl(issPortalFileRawValue.fromDisbursementDate),
      toDisbursementDate: new FormControl(issPortalFileRawValue.toDisbursementDate),
      pacsCode: new FormControl(issPortalFileRawValue.pacsCode),
      status: new FormControl(issPortalFileRawValue.status),
      applicationCount: new FormControl(issPortalFileRawValue.applicationCount),
      notes: new FormControl(issPortalFileRawValue.notes),
    });
  }

  getIssPortalFile(form: IssPortalFileFormGroup): IIssPortalFile | NewIssPortalFile {
    return form.getRawValue() as IIssPortalFile | NewIssPortalFile;
  }

  resetForm(form: IssPortalFileFormGroup, issPortalFile: IssPortalFileFormGroupInput): void {
    const issPortalFileRawValue = { ...this.getFormDefaults(), ...issPortalFile };
    form.reset(
      {
        ...issPortalFileRawValue,
        id: { value: issPortalFileRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): IssPortalFileFormDefaults {
    return {
      id: null,
    };
  }
}
