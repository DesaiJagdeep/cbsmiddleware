import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ICourtCaseDetails, NewCourtCaseDetails } from '../court-case-details.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICourtCaseDetails for edit and NewCourtCaseDetailsFormGroupInput for create.
 */
type CourtCaseDetailsFormGroupInput = ICourtCaseDetails | PartialWithRequiredKeyOf<NewCourtCaseDetails>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ICourtCaseDetails | NewCourtCaseDetails> = Omit<
  T,
  'dinank' | 'caseDinank' | 'certificateDinnank' | 'dimmandDinnank' | 'japtiAadheshDinnank' | 'vikriAddheshDinnank'
> & {
  dinank?: string | null;
  caseDinank?: string | null;
  certificateDinnank?: string | null;
  dimmandDinnank?: string | null;
  japtiAadheshDinnank?: string | null;
  vikriAddheshDinnank?: string | null;
};

type CourtCaseDetailsFormRawValue = FormValueOf<ICourtCaseDetails>;

type NewCourtCaseDetailsFormRawValue = FormValueOf<NewCourtCaseDetails>;

type CourtCaseDetailsFormDefaults = Pick<
  NewCourtCaseDetails,
  'id' | 'dinank' | 'caseDinank' | 'certificateDinnank' | 'dimmandDinnank' | 'japtiAadheshDinnank' | 'vikriAddheshDinnank'
>;

type CourtCaseDetailsFormGroupContent = {
  id: FormControl<CourtCaseDetailsFormRawValue['id'] | NewCourtCaseDetails['id']>;
  kramank: FormControl<CourtCaseDetailsFormRawValue['kramank']>;
  dinank: FormControl<CourtCaseDetailsFormRawValue['dinank']>;
  caseDinank: FormControl<CourtCaseDetailsFormRawValue['caseDinank']>;
  sabhasad: FormControl<CourtCaseDetailsFormRawValue['sabhasad']>;
  sabhasadAccNo: FormControl<CourtCaseDetailsFormRawValue['sabhasadAccNo']>;
  karjPrakarType: FormControl<CourtCaseDetailsFormRawValue['karjPrakarType']>;
  karjPrakar: FormControl<CourtCaseDetailsFormRawValue['karjPrakar']>;
  certificateMilale: FormControl<CourtCaseDetailsFormRawValue['certificateMilale']>;
  certificateDinnank: FormControl<CourtCaseDetailsFormRawValue['certificateDinnank']>;
  certificateRakkam: FormControl<CourtCaseDetailsFormRawValue['certificateRakkam']>;
  yenebaki: FormControl<CourtCaseDetailsFormRawValue['yenebaki']>;
  vyaj: FormControl<CourtCaseDetailsFormRawValue['vyaj']>;
  etar: FormControl<CourtCaseDetailsFormRawValue['etar']>;
  dimmandMilale: FormControl<CourtCaseDetailsFormRawValue['dimmandMilale']>;
  dimmandDinnank: FormControl<CourtCaseDetailsFormRawValue['dimmandDinnank']>;
  japtiAadhesh: FormControl<CourtCaseDetailsFormRawValue['japtiAadhesh']>;
  japtiAadheshDinnank: FormControl<CourtCaseDetailsFormRawValue['japtiAadheshDinnank']>;
  sthavr: FormControl<CourtCaseDetailsFormRawValue['sthavr']>;
  jangam: FormControl<CourtCaseDetailsFormRawValue['jangam']>;
  vikriAadhesh: FormControl<CourtCaseDetailsFormRawValue['vikriAadhesh']>;
  vikriAddheshDinnank: FormControl<CourtCaseDetailsFormRawValue['vikriAddheshDinnank']>;
  etarTapshil: FormControl<CourtCaseDetailsFormRawValue['etarTapshil']>;
};

export type CourtCaseDetailsFormGroup = FormGroup<CourtCaseDetailsFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CourtCaseDetailsFormService {
  createCourtCaseDetailsFormGroup(courtCaseDetails: CourtCaseDetailsFormGroupInput = { id: null }): CourtCaseDetailsFormGroup {
    const courtCaseDetailsRawValue = this.convertCourtCaseDetailsToCourtCaseDetailsRawValue({
      ...this.getFormDefaults(),
      ...courtCaseDetails,
    });
    return new FormGroup<CourtCaseDetailsFormGroupContent>({
      id: new FormControl(
        { value: courtCaseDetailsRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      kramank: new FormControl(courtCaseDetailsRawValue.kramank),
      dinank: new FormControl(courtCaseDetailsRawValue.dinank),
      caseDinank: new FormControl(courtCaseDetailsRawValue.caseDinank),
      sabhasad: new FormControl(courtCaseDetailsRawValue.sabhasad),
      sabhasadAccNo: new FormControl(courtCaseDetailsRawValue.sabhasadAccNo),
      karjPrakarType: new FormControl(courtCaseDetailsRawValue.karjPrakarType),
      karjPrakar: new FormControl(courtCaseDetailsRawValue.karjPrakar),
      certificateMilale: new FormControl(courtCaseDetailsRawValue.certificateMilale),
      certificateDinnank: new FormControl(courtCaseDetailsRawValue.certificateDinnank),
      certificateRakkam: new FormControl(courtCaseDetailsRawValue.certificateRakkam),
      yenebaki: new FormControl(courtCaseDetailsRawValue.yenebaki),
      vyaj: new FormControl(courtCaseDetailsRawValue.vyaj),
      etar: new FormControl(courtCaseDetailsRawValue.etar),
      dimmandMilale: new FormControl(courtCaseDetailsRawValue.dimmandMilale),
      dimmandDinnank: new FormControl(courtCaseDetailsRawValue.dimmandDinnank),
      japtiAadhesh: new FormControl(courtCaseDetailsRawValue.japtiAadhesh),
      japtiAadheshDinnank: new FormControl(courtCaseDetailsRawValue.japtiAadheshDinnank),
      sthavr: new FormControl(courtCaseDetailsRawValue.sthavr),
      jangam: new FormControl(courtCaseDetailsRawValue.jangam),
      vikriAadhesh: new FormControl(courtCaseDetailsRawValue.vikriAadhesh),
      vikriAddheshDinnank: new FormControl(courtCaseDetailsRawValue.vikriAddheshDinnank),
      etarTapshil: new FormControl(courtCaseDetailsRawValue.etarTapshil),
    });
  }

  getCourtCaseDetails(form: CourtCaseDetailsFormGroup): ICourtCaseDetails | NewCourtCaseDetails {
    return this.convertCourtCaseDetailsRawValueToCourtCaseDetails(
      form.getRawValue() as CourtCaseDetailsFormRawValue | NewCourtCaseDetailsFormRawValue
    );
  }

  resetForm(form: CourtCaseDetailsFormGroup, courtCaseDetails: CourtCaseDetailsFormGroupInput): void {
    const courtCaseDetailsRawValue = this.convertCourtCaseDetailsToCourtCaseDetailsRawValue({
      ...this.getFormDefaults(),
      ...courtCaseDetails,
    });
    form.reset(
      {
        ...courtCaseDetailsRawValue,
        id: { value: courtCaseDetailsRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): CourtCaseDetailsFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      dinank: currentTime,
      caseDinank: currentTime,
      certificateDinnank: currentTime,
      dimmandDinnank: currentTime,
      japtiAadheshDinnank: currentTime,
      vikriAddheshDinnank: currentTime,
    };
  }

  private convertCourtCaseDetailsRawValueToCourtCaseDetails(
    rawCourtCaseDetails: CourtCaseDetailsFormRawValue | NewCourtCaseDetailsFormRawValue
  ): ICourtCaseDetails | NewCourtCaseDetails {
    return {
      ...rawCourtCaseDetails,
      dinank: dayjs(rawCourtCaseDetails.dinank, DATE_TIME_FORMAT),
      caseDinank: dayjs(rawCourtCaseDetails.caseDinank, DATE_TIME_FORMAT),
      certificateDinnank: dayjs(rawCourtCaseDetails.certificateDinnank, DATE_TIME_FORMAT),
      dimmandDinnank: dayjs(rawCourtCaseDetails.dimmandDinnank, DATE_TIME_FORMAT),
      japtiAadheshDinnank: dayjs(rawCourtCaseDetails.japtiAadheshDinnank, DATE_TIME_FORMAT),
      vikriAddheshDinnank: dayjs(rawCourtCaseDetails.vikriAddheshDinnank, DATE_TIME_FORMAT),
    };
  }

  private convertCourtCaseDetailsToCourtCaseDetailsRawValue(
    courtCaseDetails: ICourtCaseDetails | (Partial<NewCourtCaseDetails> & CourtCaseDetailsFormDefaults)
  ): CourtCaseDetailsFormRawValue | PartialWithRequiredKeyOf<NewCourtCaseDetailsFormRawValue> {
    return {
      ...courtCaseDetails,
      dinank: courtCaseDetails.dinank ? courtCaseDetails.dinank.format(DATE_TIME_FORMAT) : undefined,
      caseDinank: courtCaseDetails.caseDinank ? courtCaseDetails.caseDinank.format(DATE_TIME_FORMAT) : undefined,
      certificateDinnank: courtCaseDetails.certificateDinnank ? courtCaseDetails.certificateDinnank.format(DATE_TIME_FORMAT) : undefined,
      dimmandDinnank: courtCaseDetails.dimmandDinnank ? courtCaseDetails.dimmandDinnank.format(DATE_TIME_FORMAT) : undefined,
      japtiAadheshDinnank: courtCaseDetails.japtiAadheshDinnank ? courtCaseDetails.japtiAadheshDinnank.format(DATE_TIME_FORMAT) : undefined,
      vikriAddheshDinnank: courtCaseDetails.vikriAddheshDinnank ? courtCaseDetails.vikriAddheshDinnank.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
