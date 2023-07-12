import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IApplicationLogHistory, NewApplicationLogHistory } from '../application-log-history.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IApplicationLogHistory for edit and NewApplicationLogHistoryFormGroupInput for create.
 */
type ApplicationLogHistoryFormGroupInput = IApplicationLogHistory | PartialWithRequiredKeyOf<NewApplicationLogHistory>;

type ApplicationLogHistoryFormDefaults = Pick<NewApplicationLogHistory, 'id'>;

type ApplicationLogHistoryFormGroupContent = {
  id: FormControl<IApplicationLogHistory['id'] | NewApplicationLogHistory['id']>;
  errorType: FormControl<IApplicationLogHistory['errorType']>;
  errorCode: FormControl<IApplicationLogHistory['errorCode']>;
  errorMessage: FormControl<IApplicationLogHistory['errorMessage']>;
  rowNumber: FormControl<IApplicationLogHistory['rowNumber']>;
  columnNumber: FormControl<IApplicationLogHistory['columnNumber']>;
  sevierity: FormControl<IApplicationLogHistory['sevierity']>;
  expectedSolution: FormControl<IApplicationLogHistory['expectedSolution']>;
  status: FormControl<IApplicationLogHistory['status']>;
  issFileParser: FormControl<IApplicationLogHistory['issFileParser']>;
};

export type ApplicationLogHistoryFormGroup = FormGroup<ApplicationLogHistoryFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ApplicationLogHistoryFormService {
  createApplicationLogHistoryFormGroup(
    applicationLogHistory: ApplicationLogHistoryFormGroupInput = { id: null }
  ): ApplicationLogHistoryFormGroup {
    const applicationLogHistoryRawValue = {
      ...this.getFormDefaults(),
      ...applicationLogHistory,
    };
    return new FormGroup<ApplicationLogHistoryFormGroupContent>({
      id: new FormControl(
        { value: applicationLogHistoryRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      errorType: new FormControl(applicationLogHistoryRawValue.errorType),
      errorCode: new FormControl(applicationLogHistoryRawValue.errorCode),
      errorMessage: new FormControl(applicationLogHistoryRawValue.errorMessage),
      rowNumber: new FormControl(applicationLogHistoryRawValue.rowNumber),
      columnNumber: new FormControl(applicationLogHistoryRawValue.columnNumber),
      sevierity: new FormControl(applicationLogHistoryRawValue.sevierity),
      expectedSolution: new FormControl(applicationLogHistoryRawValue.expectedSolution),
      status: new FormControl(applicationLogHistoryRawValue.status),
      issFileParser: new FormControl(applicationLogHistoryRawValue.issFileParser),
    });
  }

  getApplicationLogHistory(form: ApplicationLogHistoryFormGroup): IApplicationLogHistory | NewApplicationLogHistory {
    return form.getRawValue() as IApplicationLogHistory | NewApplicationLogHistory;
  }

  resetForm(form: ApplicationLogHistoryFormGroup, applicationLogHistory: ApplicationLogHistoryFormGroupInput): void {
    const applicationLogHistoryRawValue = { ...this.getFormDefaults(), ...applicationLogHistory };
    form.reset(
      {
        ...applicationLogHistoryRawValue,
        id: { value: applicationLogHistoryRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ApplicationLogHistoryFormDefaults {
    return {
      id: null,
    };
  }
}
