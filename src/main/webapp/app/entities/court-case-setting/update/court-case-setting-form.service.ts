import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICourtCaseSetting, NewCourtCaseSetting } from '../court-case-setting.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICourtCaseSetting for edit and NewCourtCaseSettingFormGroupInput for create.
 */
type CourtCaseSettingFormGroupInput = ICourtCaseSetting | PartialWithRequiredKeyOf<NewCourtCaseSetting>;

type CourtCaseSettingFormDefaults = Pick<NewCourtCaseSetting, 'id'>;

type CourtCaseSettingFormGroupContent = {
  id: FormControl<ICourtCaseSetting['id'] | NewCourtCaseSetting['id']>;
  vasuliAdhikariName: FormControl<ICourtCaseSetting['vasuliAdhikariName']>;
  arOfficeName: FormControl<ICourtCaseSetting['arOfficeName']>;
  chairmanName: FormControl<ICourtCaseSetting['chairmanName']>;
  sachivName: FormControl<ICourtCaseSetting['sachivName']>;
  suchakName: FormControl<ICourtCaseSetting['suchakName']>;
  anumodakName: FormControl<ICourtCaseSetting['anumodakName']>;
  vasuliExpense: FormControl<ICourtCaseSetting['vasuliExpense']>;
  otherExpense: FormControl<ICourtCaseSetting['otherExpense']>;
  noticeExpense: FormControl<ICourtCaseSetting['noticeExpense']>;
  meetingNo: FormControl<ICourtCaseSetting['meetingNo']>;
  meetingDate: FormControl<ICourtCaseSetting['meetingDate']>;
  subjectNo: FormControl<ICourtCaseSetting['subjectNo']>;
  meetingDay: FormControl<ICourtCaseSetting['meetingDay']>;
  meetingTime: FormControl<ICourtCaseSetting['meetingTime']>;
};

export type CourtCaseSettingFormGroup = FormGroup<CourtCaseSettingFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CourtCaseSettingFormService {
  createCourtCaseSettingFormGroup(courtCaseSetting: CourtCaseSettingFormGroupInput = { id: null }): CourtCaseSettingFormGroup {
    const courtCaseSettingRawValue = {
      ...this.getFormDefaults(),
      ...courtCaseSetting,
    };
    return new FormGroup<CourtCaseSettingFormGroupContent>({
      id: new FormControl(
        { value: courtCaseSettingRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      vasuliAdhikariName: new FormControl(courtCaseSettingRawValue.vasuliAdhikariName),
      arOfficeName: new FormControl(courtCaseSettingRawValue.arOfficeName),
      chairmanName: new FormControl(courtCaseSettingRawValue.chairmanName),
      sachivName: new FormControl(courtCaseSettingRawValue.sachivName),
      suchakName: new FormControl(courtCaseSettingRawValue.suchakName),
      anumodakName: new FormControl(courtCaseSettingRawValue.anumodakName),
      vasuliExpense: new FormControl(courtCaseSettingRawValue.vasuliExpense),
      otherExpense: new FormControl(courtCaseSettingRawValue.otherExpense),
      noticeExpense: new FormControl(courtCaseSettingRawValue.noticeExpense),
      meetingNo: new FormControl(courtCaseSettingRawValue.meetingNo),
      meetingDate: new FormControl(courtCaseSettingRawValue.meetingDate),
      subjectNo: new FormControl(courtCaseSettingRawValue.subjectNo),
      meetingDay: new FormControl(courtCaseSettingRawValue.meetingDay),
      meetingTime: new FormControl(courtCaseSettingRawValue.meetingTime),
    });
  }

  getCourtCaseSetting(form: CourtCaseSettingFormGroup): ICourtCaseSetting | NewCourtCaseSetting {
    return form.getRawValue() as ICourtCaseSetting | NewCourtCaseSetting;
  }

  resetForm(form: CourtCaseSettingFormGroup, courtCaseSetting: CourtCaseSettingFormGroupInput): void {
    const courtCaseSettingRawValue = { ...this.getFormDefaults(), ...courtCaseSetting };
    form.reset(
      {
        ...courtCaseSettingRawValue,
        id: { value: courtCaseSettingRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): CourtCaseSettingFormDefaults {
    return {
      id: null,
    };
  }
}
