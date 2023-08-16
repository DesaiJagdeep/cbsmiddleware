import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICbsDataReport, NewCbsDataReport } from '../cbs-data-report.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICbsDataReport for edit and NewCbsDataReportFormGroupInput for create.
 */
type CbsDataReportFormGroupInput = ICbsDataReport | PartialWithRequiredKeyOf<NewCbsDataReport>;

type CbsDataReportFormDefaults = Pick<NewCbsDataReport, 'id'>;

type CbsDataReportFormGroupContent = {
  id: FormControl<ICbsDataReport['id'] | NewCbsDataReport['id']>;
  farmerName: FormControl<ICbsDataReport['farmerName']>;
};

export type CbsDataReportFormGroup = FormGroup<CbsDataReportFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CbsDataReportFormService {
  createCbsDataReportFormGroup(cbsDataReport: CbsDataReportFormGroupInput = { id: null }): CbsDataReportFormGroup {
    const cbsDataReportRawValue = {
      ...this.getFormDefaults(),
      ...cbsDataReport,
    };
    return new FormGroup<CbsDataReportFormGroupContent>({
      id: new FormControl(
        { value: cbsDataReportRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      farmerName: new FormControl(cbsDataReportRawValue.farmerName),
    });
  }

  getCbsDataReport(form: CbsDataReportFormGroup): ICbsDataReport | NewCbsDataReport {
    return form.getRawValue() as ICbsDataReport | NewCbsDataReport;
  }

  resetForm(form: CbsDataReportFormGroup, cbsDataReport: CbsDataReportFormGroupInput): void {
    const cbsDataReportRawValue = { ...this.getFormDefaults(), ...cbsDataReport };
    form.reset(
      {
        ...cbsDataReportRawValue,
        id: { value: cbsDataReportRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): CbsDataReportFormDefaults {
    return {
      id: null,
    };
  }
}
