import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IApplicationLog, NewApplicationLog } from '../application-log.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IApplicationLog for edit and NewApplicationLogFormGroupInput for create.
 */
type ApplicationLogFormGroupInput = IApplicationLog | PartialWithRequiredKeyOf<NewApplicationLog>;

type ApplicationLogFormDefaults = Pick<NewApplicationLog, 'id'>;

type ApplicationLogFormGroupContent = {
  id: FormControl<IApplicationLog['id'] | NewApplicationLog['id']>;
  errorType: FormControl<IApplicationLog['errorType']>;
  errorCode: FormControl<IApplicationLog['errorCode']>;
  errorMessage: FormControl<IApplicationLog['errorMessage']>;
  columnNumber: FormControl<IApplicationLog['columnNumber']>;
  sevierity: FormControl<IApplicationLog['sevierity']>;
  expectedSolution: FormControl<IApplicationLog['expectedSolution']>;
  status: FormControl<IApplicationLog['status']>;
  rowNumber: FormControl<IApplicationLog['rowNumber']>;
  batchId: FormControl<IApplicationLog['batchId']>;
  issFileParser: FormControl<IApplicationLog['issFileParser']>;
};

export type ApplicationLogFormGroup = FormGroup<ApplicationLogFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ApplicationLogFormService {
  createApplicationLogFormGroup(applicationLog: ApplicationLogFormGroupInput = { id: null }): ApplicationLogFormGroup {
    const applicationLogRawValue = {
      ...this.getFormDefaults(),
      ...applicationLog,
    };
    return new FormGroup<ApplicationLogFormGroupContent>({
      id: new FormControl(
        { value: applicationLogRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      errorType: new FormControl(applicationLogRawValue.errorType),
      errorCode: new FormControl(applicationLogRawValue.errorCode),
      errorMessage: new FormControl(applicationLogRawValue.errorMessage),
      columnNumber: new FormControl(applicationLogRawValue.columnNumber),
      sevierity: new FormControl(applicationLogRawValue.sevierity),
      expectedSolution: new FormControl(applicationLogRawValue.expectedSolution),
      status: new FormControl(applicationLogRawValue.status),
      rowNumber: new FormControl(applicationLogRawValue.rowNumber),
      batchId: new FormControl(applicationLogRawValue.batchId),
      issFileParser: new FormControl(applicationLogRawValue.issFileParser),
    });
  }

  getApplicationLog(form: ApplicationLogFormGroup): IApplicationLog | NewApplicationLog {
    return form.getRawValue() as IApplicationLog | NewApplicationLog;
  }

  resetForm(form: ApplicationLogFormGroup, applicationLog: ApplicationLogFormGroupInput): void {
    const applicationLogRawValue = { ...this.getFormDefaults(), ...applicationLog };
    form.reset(
      {
        ...applicationLogRawValue,
        id: { value: applicationLogRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ApplicationLogFormDefaults {
    return {
      id: null,
    };
  }
}
