import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ICourtCase, NewCourtCase } from '../court-case.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICourtCase for edit and NewCourtCaseFormGroupInput for create.
 */
type CourtCaseFormGroupInput = ICourtCase | PartialWithRequiredKeyOf<NewCourtCase>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ICourtCase | NewCourtCase> = Omit<
  T,
  'caseDinank' | 'thakDinnank' | 'karjDinnank' | 'mudatSampteDinank' | 'tharavDinank' | 'noticeOne' | 'noticeTwo'
> & {
  caseDinank?: string | null;
  thakDinnank?: string | null;
  karjDinnank?: string | null;
  mudatSampteDinank?: string | null;
  tharavDinank?: string | null;
  noticeOne?: string | null;
  noticeTwo?: string | null;
};

type CourtCaseFormRawValue = FormValueOf<ICourtCase>;

type NewCourtCaseFormRawValue = FormValueOf<NewCourtCase>;

type CourtCaseFormDefaults = Pick<
  NewCourtCase,
  'id' | 'caseDinank' | 'thakDinnank' | 'karjDinnank' | 'mudatSampteDinank' | 'tharavDinank' | 'noticeOne' | 'noticeTwo'
>;

type CourtCaseFormGroupContent = {
  id: FormControl<CourtCaseFormRawValue['id'] | NewCourtCase['id']>;
  code: FormControl<CourtCaseFormRawValue['code']>;
  caseDinank: FormControl<CourtCaseFormRawValue['caseDinank']>;
  bankName: FormControl<CourtCaseFormRawValue['bankName']>;
  talukaName: FormControl<CourtCaseFormRawValue['talukaName']>;
  talukaCode: FormControl<CourtCaseFormRawValue['talukaCode']>;
  sabasadSavingAccNo: FormControl<CourtCaseFormRawValue['sabasadSavingAccNo']>;
  sabasadName: FormControl<CourtCaseFormRawValue['sabasadName']>;
  sabasadAddress: FormControl<CourtCaseFormRawValue['sabasadAddress']>;
  karjPrakar: FormControl<CourtCaseFormRawValue['karjPrakar']>;
  vasuliAdhikari: FormControl<CourtCaseFormRawValue['vasuliAdhikari']>;
  ekunJama: FormControl<CourtCaseFormRawValue['ekunJama']>;
  baki: FormControl<CourtCaseFormRawValue['baki']>;
  arOffice: FormControl<CourtCaseFormRawValue['arOffice']>;
  ekunVyaj: FormControl<CourtCaseFormRawValue['ekunVyaj']>;
  jamaVyaj: FormControl<CourtCaseFormRawValue['jamaVyaj']>;
  dandVyaj: FormControl<CourtCaseFormRawValue['dandVyaj']>;
  karjRakkam: FormControl<CourtCaseFormRawValue['karjRakkam']>;
  thakDinnank: FormControl<CourtCaseFormRawValue['thakDinnank']>;
  karjDinnank: FormControl<CourtCaseFormRawValue['karjDinnank']>;
  mudatSampteDinank: FormControl<CourtCaseFormRawValue['mudatSampteDinank']>;
  mudat: FormControl<CourtCaseFormRawValue['mudat']>;
  vyaj: FormControl<CourtCaseFormRawValue['vyaj']>;
  haptaRakkam: FormControl<CourtCaseFormRawValue['haptaRakkam']>;
  shakhaVevsthapak: FormControl<CourtCaseFormRawValue['shakhaVevsthapak']>;
  suchak: FormControl<CourtCaseFormRawValue['suchak']>;
  anumodak: FormControl<CourtCaseFormRawValue['anumodak']>;
  dava: FormControl<CourtCaseFormRawValue['dava']>;
  vyajDar: FormControl<CourtCaseFormRawValue['vyajDar']>;
  sarcharge: FormControl<CourtCaseFormRawValue['sarcharge']>;
  jyadaVyaj: FormControl<CourtCaseFormRawValue['jyadaVyaj']>;
  yeneVyaj: FormControl<CourtCaseFormRawValue['yeneVyaj']>;
  vasuliKharch: FormControl<CourtCaseFormRawValue['vasuliKharch']>;
  etharKharch: FormControl<CourtCaseFormRawValue['etharKharch']>;
  vima: FormControl<CourtCaseFormRawValue['vima']>;
  notice: FormControl<CourtCaseFormRawValue['notice']>;
  tharavNumber: FormControl<CourtCaseFormRawValue['tharavNumber']>;
  tharavDinank: FormControl<CourtCaseFormRawValue['tharavDinank']>;
  vishayKramank: FormControl<CourtCaseFormRawValue['vishayKramank']>;
  noticeOne: FormControl<CourtCaseFormRawValue['noticeOne']>;
  noticeTwo: FormControl<CourtCaseFormRawValue['noticeTwo']>;
  war: FormControl<CourtCaseFormRawValue['war']>;
  vel: FormControl<CourtCaseFormRawValue['vel']>;
  jamindarOne: FormControl<CourtCaseFormRawValue['jamindarOne']>;
  jamindarOneAddress: FormControl<CourtCaseFormRawValue['jamindarOneAddress']>;
  jamindarTwo: FormControl<CourtCaseFormRawValue['jamindarTwo']>;
  jamindarTwoAddress: FormControl<CourtCaseFormRawValue['jamindarTwoAddress']>;
  taranType: FormControl<CourtCaseFormRawValue['taranType']>;
  taranDetails: FormControl<CourtCaseFormRawValue['taranDetails']>;
};

export type CourtCaseFormGroup = FormGroup<CourtCaseFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CourtCaseFormService {
  createCourtCaseFormGroup(courtCase: CourtCaseFormGroupInput = { id: null }): CourtCaseFormGroup {
    const courtCaseRawValue = this.convertCourtCaseToCourtCaseRawValue({
      ...this.getFormDefaults(),
      ...courtCase,
    });
    return new FormGroup<CourtCaseFormGroupContent>({
      id: new FormControl(
        { value: courtCaseRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      code: new FormControl(courtCaseRawValue.code),
      caseDinank: new FormControl(courtCaseRawValue.caseDinank),
      bankName: new FormControl(courtCaseRawValue.bankName),
      talukaName: new FormControl(courtCaseRawValue.talukaName),
      talukaCode: new FormControl(courtCaseRawValue.talukaCode),
      sabasadSavingAccNo: new FormControl(courtCaseRawValue.sabasadSavingAccNo),
      sabasadName: new FormControl(courtCaseRawValue.sabasadName),
      sabasadAddress: new FormControl(courtCaseRawValue.sabasadAddress),
      karjPrakar: new FormControl(courtCaseRawValue.karjPrakar),
      vasuliAdhikari: new FormControl(courtCaseRawValue.vasuliAdhikari),
      ekunJama: new FormControl(courtCaseRawValue.ekunJama),
      baki: new FormControl(courtCaseRawValue.baki),
      arOffice: new FormControl(courtCaseRawValue.arOffice),
      ekunVyaj: new FormControl(courtCaseRawValue.ekunVyaj),
      jamaVyaj: new FormControl(courtCaseRawValue.jamaVyaj),
      dandVyaj: new FormControl(courtCaseRawValue.dandVyaj),
      karjRakkam: new FormControl(courtCaseRawValue.karjRakkam),
      thakDinnank: new FormControl(courtCaseRawValue.thakDinnank),
      karjDinnank: new FormControl(courtCaseRawValue.karjDinnank),
      mudatSampteDinank: new FormControl(courtCaseRawValue.mudatSampteDinank),
      mudat: new FormControl(courtCaseRawValue.mudat),
      vyaj: new FormControl(courtCaseRawValue.vyaj),
      haptaRakkam: new FormControl(courtCaseRawValue.haptaRakkam),
      shakhaVevsthapak: new FormControl(courtCaseRawValue.shakhaVevsthapak),
      suchak: new FormControl(courtCaseRawValue.suchak),
      anumodak: new FormControl(courtCaseRawValue.anumodak),
      dava: new FormControl(courtCaseRawValue.dava),
      vyajDar: new FormControl(courtCaseRawValue.vyajDar),
      sarcharge: new FormControl(courtCaseRawValue.sarcharge),
      jyadaVyaj: new FormControl(courtCaseRawValue.jyadaVyaj),
      yeneVyaj: new FormControl(courtCaseRawValue.yeneVyaj),
      vasuliKharch: new FormControl(courtCaseRawValue.vasuliKharch),
      etharKharch: new FormControl(courtCaseRawValue.etharKharch),
      vima: new FormControl(courtCaseRawValue.vima),
      notice: new FormControl(courtCaseRawValue.notice),
      tharavNumber: new FormControl(courtCaseRawValue.tharavNumber),
      tharavDinank: new FormControl(courtCaseRawValue.tharavDinank),
      vishayKramank: new FormControl(courtCaseRawValue.vishayKramank),
      noticeOne: new FormControl(courtCaseRawValue.noticeOne),
      noticeTwo: new FormControl(courtCaseRawValue.noticeTwo),
      war: new FormControl(courtCaseRawValue.war),
      vel: new FormControl(courtCaseRawValue.vel),
      jamindarOne: new FormControl(courtCaseRawValue.jamindarOne),
      jamindarOneAddress: new FormControl(courtCaseRawValue.jamindarOneAddress),
      jamindarTwo: new FormControl(courtCaseRawValue.jamindarTwo),
      jamindarTwoAddress: new FormControl(courtCaseRawValue.jamindarTwoAddress),
      taranType: new FormControl(courtCaseRawValue.taranType),
      taranDetails: new FormControl(courtCaseRawValue.taranDetails),
    });
  }

  getCourtCase(form: CourtCaseFormGroup): ICourtCase | NewCourtCase {
    return this.convertCourtCaseRawValueToCourtCase(form.getRawValue() as CourtCaseFormRawValue | NewCourtCaseFormRawValue);
  }

  resetForm(form: CourtCaseFormGroup, courtCase: CourtCaseFormGroupInput): void {
    const courtCaseRawValue = this.convertCourtCaseToCourtCaseRawValue({ ...this.getFormDefaults(), ...courtCase });
    form.reset(
      {
        ...courtCaseRawValue,
        id: { value: courtCaseRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): CourtCaseFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      caseDinank: currentTime,
      thakDinnank: currentTime,
      karjDinnank: currentTime,
      mudatSampteDinank: currentTime,
      tharavDinank: currentTime,
      noticeOne: currentTime,
      noticeTwo: currentTime,
    };
  }

  private convertCourtCaseRawValueToCourtCase(rawCourtCase: CourtCaseFormRawValue | NewCourtCaseFormRawValue): ICourtCase | NewCourtCase {
    return {
      ...rawCourtCase,
      caseDinank: dayjs(rawCourtCase.caseDinank, DATE_TIME_FORMAT),
      thakDinnank: dayjs(rawCourtCase.thakDinnank, DATE_TIME_FORMAT),
      karjDinnank: dayjs(rawCourtCase.karjDinnank, DATE_TIME_FORMAT),
      mudatSampteDinank: dayjs(rawCourtCase.mudatSampteDinank, DATE_TIME_FORMAT),
      tharavDinank: dayjs(rawCourtCase.tharavDinank, DATE_TIME_FORMAT),
      noticeOne: dayjs(rawCourtCase.noticeOne, DATE_TIME_FORMAT),
      noticeTwo: dayjs(rawCourtCase.noticeTwo, DATE_TIME_FORMAT),
    };
  }

  private convertCourtCaseToCourtCaseRawValue(
    courtCase: ICourtCase | (Partial<NewCourtCase> & CourtCaseFormDefaults)
  ): CourtCaseFormRawValue | PartialWithRequiredKeyOf<NewCourtCaseFormRawValue> {
    return {
      ...courtCase,
      caseDinank: courtCase.caseDinank ? courtCase.caseDinank.format(DATE_TIME_FORMAT) : undefined,
      thakDinnank: courtCase.thakDinnank ? courtCase.thakDinnank.format(DATE_TIME_FORMAT) : undefined,
      karjDinnank: courtCase.karjDinnank ? courtCase.karjDinnank.format(DATE_TIME_FORMAT) : undefined,
      mudatSampteDinank: courtCase.mudatSampteDinank ? courtCase.mudatSampteDinank.format(DATE_TIME_FORMAT) : undefined,
      tharavDinank: courtCase.tharavDinank ? courtCase.tharavDinank.format(DATE_TIME_FORMAT) : undefined,
      noticeOne: courtCase.noticeOne ? courtCase.noticeOne.format(DATE_TIME_FORMAT) : undefined,
      noticeTwo: courtCase.noticeTwo ? courtCase.noticeTwo.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
