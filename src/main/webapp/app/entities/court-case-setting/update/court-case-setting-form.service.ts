import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
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

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ICourtCaseSetting | NewCourtCaseSetting> = Omit<
  T,
  'dinank' | 'tharavDinank' | 'karjFedNotice' | 'oneZeroOneNoticeOne' | 'oneZeroOneNoticeTwo' | 'maganiNotice'
> & {
  dinank?: string | null;
  tharavDinank?: string | null;
  karjFedNotice?: string | null;
  oneZeroOneNoticeOne?: string | null;
  oneZeroOneNoticeTwo?: string | null;
  maganiNotice?: string | null;
};

type CourtCaseSettingFormRawValue = FormValueOf<ICourtCaseSetting>;

type NewCourtCaseSettingFormRawValue = FormValueOf<NewCourtCaseSetting>;

type CourtCaseSettingFormDefaults = Pick<
  NewCourtCaseSetting,
  'id' | 'dinank' | 'tharavDinank' | 'karjFedNotice' | 'oneZeroOneNoticeOne' | 'oneZeroOneNoticeTwo' | 'maganiNotice'
>;

type CourtCaseSettingFormGroupContent = {
  id: FormControl<CourtCaseSettingFormRawValue['id'] | NewCourtCaseSetting['id']>;
  dinank: FormControl<CourtCaseSettingFormRawValue['dinank']>;
  shakhaVevsthapak: FormControl<CourtCaseSettingFormRawValue['shakhaVevsthapak']>;
  suchak: FormControl<CourtCaseSettingFormRawValue['suchak']>;
  aanumodak: FormControl<CourtCaseSettingFormRawValue['aanumodak']>;
  vasuliAdhikari: FormControl<CourtCaseSettingFormRawValue['vasuliAdhikari']>;
  arOffice: FormControl<CourtCaseSettingFormRawValue['arOffice']>;
  tharavNumber: FormControl<CourtCaseSettingFormRawValue['tharavNumber']>;
  tharavDinank: FormControl<CourtCaseSettingFormRawValue['tharavDinank']>;
  karjFedNotice: FormControl<CourtCaseSettingFormRawValue['karjFedNotice']>;
  oneZeroOneNoticeOne: FormControl<CourtCaseSettingFormRawValue['oneZeroOneNoticeOne']>;
  oneZeroOneNoticeTwo: FormControl<CourtCaseSettingFormRawValue['oneZeroOneNoticeTwo']>;
  vishayKramank: FormControl<CourtCaseSettingFormRawValue['vishayKramank']>;
  war: FormControl<CourtCaseSettingFormRawValue['war']>;
  vel: FormControl<CourtCaseSettingFormRawValue['vel']>;
  maganiNotice: FormControl<CourtCaseSettingFormRawValue['maganiNotice']>;
  etarKharch: FormControl<CourtCaseSettingFormRawValue['etarKharch']>;
  noticeKharch: FormControl<CourtCaseSettingFormRawValue['noticeKharch']>;
  vasuliKharch: FormControl<CourtCaseSettingFormRawValue['vasuliKharch']>;
};

export type CourtCaseSettingFormGroup = FormGroup<CourtCaseSettingFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CourtCaseSettingFormService {
  createCourtCaseSettingFormGroup(courtCaseSetting: CourtCaseSettingFormGroupInput = { id: null }): CourtCaseSettingFormGroup {
    const courtCaseSettingRawValue = this.convertCourtCaseSettingToCourtCaseSettingRawValue({
      ...this.getFormDefaults(),
      ...courtCaseSetting,
    });
    return new FormGroup<CourtCaseSettingFormGroupContent>({
      id: new FormControl(
        { value: courtCaseSettingRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      dinank: new FormControl(courtCaseSettingRawValue.dinank),
      shakhaVevsthapak: new FormControl(courtCaseSettingRawValue.shakhaVevsthapak),
      suchak: new FormControl(courtCaseSettingRawValue.suchak),
      aanumodak: new FormControl(courtCaseSettingRawValue.aanumodak),
      vasuliAdhikari: new FormControl(courtCaseSettingRawValue.vasuliAdhikari),
      arOffice: new FormControl(courtCaseSettingRawValue.arOffice),
      tharavNumber: new FormControl(courtCaseSettingRawValue.tharavNumber),
      tharavDinank: new FormControl(courtCaseSettingRawValue.tharavDinank),
      karjFedNotice: new FormControl(courtCaseSettingRawValue.karjFedNotice),
      oneZeroOneNoticeOne: new FormControl(courtCaseSettingRawValue.oneZeroOneNoticeOne),
      oneZeroOneNoticeTwo: new FormControl(courtCaseSettingRawValue.oneZeroOneNoticeTwo),
      vishayKramank: new FormControl(courtCaseSettingRawValue.vishayKramank),
      war: new FormControl(courtCaseSettingRawValue.war),
      vel: new FormControl(courtCaseSettingRawValue.vel),
      maganiNotice: new FormControl(courtCaseSettingRawValue.maganiNotice),
      etarKharch: new FormControl(courtCaseSettingRawValue.etarKharch),
      noticeKharch: new FormControl(courtCaseSettingRawValue.noticeKharch),
      vasuliKharch: new FormControl(courtCaseSettingRawValue.vasuliKharch),
    });
  }

  getCourtCaseSetting(form: CourtCaseSettingFormGroup): ICourtCaseSetting | NewCourtCaseSetting {
    return this.convertCourtCaseSettingRawValueToCourtCaseSetting(
      form.getRawValue() as CourtCaseSettingFormRawValue | NewCourtCaseSettingFormRawValue
    );
  }

  resetForm(form: CourtCaseSettingFormGroup, courtCaseSetting: CourtCaseSettingFormGroupInput): void {
    const courtCaseSettingRawValue = this.convertCourtCaseSettingToCourtCaseSettingRawValue({
      ...this.getFormDefaults(),
      ...courtCaseSetting,
    });
    form.reset(
      {
        ...courtCaseSettingRawValue,
        id: { value: courtCaseSettingRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): CourtCaseSettingFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      dinank: currentTime,
      tharavDinank: currentTime,
      karjFedNotice: currentTime,
      oneZeroOneNoticeOne: currentTime,
      oneZeroOneNoticeTwo: currentTime,
      maganiNotice: currentTime,
    };
  }

  private convertCourtCaseSettingRawValueToCourtCaseSetting(
    rawCourtCaseSetting: CourtCaseSettingFormRawValue | NewCourtCaseSettingFormRawValue
  ): ICourtCaseSetting | NewCourtCaseSetting {
    return {
      ...rawCourtCaseSetting,
      dinank: dayjs(rawCourtCaseSetting.dinank, DATE_TIME_FORMAT),
      tharavDinank: dayjs(rawCourtCaseSetting.tharavDinank, DATE_TIME_FORMAT),
      karjFedNotice: dayjs(rawCourtCaseSetting.karjFedNotice, DATE_TIME_FORMAT),
      oneZeroOneNoticeOne: dayjs(rawCourtCaseSetting.oneZeroOneNoticeOne, DATE_TIME_FORMAT),
      oneZeroOneNoticeTwo: dayjs(rawCourtCaseSetting.oneZeroOneNoticeTwo, DATE_TIME_FORMAT),
      maganiNotice: dayjs(rawCourtCaseSetting.maganiNotice, DATE_TIME_FORMAT),
    };
  }

  private convertCourtCaseSettingToCourtCaseSettingRawValue(
    courtCaseSetting: ICourtCaseSetting | (Partial<NewCourtCaseSetting> & CourtCaseSettingFormDefaults)
  ): CourtCaseSettingFormRawValue | PartialWithRequiredKeyOf<NewCourtCaseSettingFormRawValue> {
    return {
      ...courtCaseSetting,
      dinank: courtCaseSetting.dinank ? courtCaseSetting.dinank.format(DATE_TIME_FORMAT) : undefined,
      tharavDinank: courtCaseSetting.tharavDinank ? courtCaseSetting.tharavDinank.format(DATE_TIME_FORMAT) : undefined,
      karjFedNotice: courtCaseSetting.karjFedNotice ? courtCaseSetting.karjFedNotice.format(DATE_TIME_FORMAT) : undefined,
      oneZeroOneNoticeOne: courtCaseSetting.oneZeroOneNoticeOne ? courtCaseSetting.oneZeroOneNoticeOne.format(DATE_TIME_FORMAT) : undefined,
      oneZeroOneNoticeTwo: courtCaseSetting.oneZeroOneNoticeTwo ? courtCaseSetting.oneZeroOneNoticeTwo.format(DATE_TIME_FORMAT) : undefined,
      maganiNotice: courtCaseSetting.maganiNotice ? courtCaseSetting.maganiNotice.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
