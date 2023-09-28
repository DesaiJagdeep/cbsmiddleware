import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IKamalMaryadaPatrak, NewKamalMaryadaPatrak } from '../kamal-maryada-patrak.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IKamalMaryadaPatrak for edit and NewKamalMaryadaPatrakFormGroupInput for create.
 */
type KamalMaryadaPatrakFormGroupInput = IKamalMaryadaPatrak | PartialWithRequiredKeyOf<NewKamalMaryadaPatrak>;

type KamalMaryadaPatrakFormDefaults = Pick<NewKamalMaryadaPatrak, 'id' | 'coverPage'>;

type KamalMaryadaPatrakFormGroupContent = {
  id: FormControl<IKamalMaryadaPatrak['id'] | NewKamalMaryadaPatrak['id']>;
  cropLoan: FormControl<IKamalMaryadaPatrak['cropLoan']>;
  kmChart: FormControl<IKamalMaryadaPatrak['kmChart']>;
  demand: FormControl<IKamalMaryadaPatrak['demand']>;
  kmSummary: FormControl<IKamalMaryadaPatrak['kmSummary']>;
  kmDate: FormControl<IKamalMaryadaPatrak['kmDate']>;
  toKMDate: FormControl<IKamalMaryadaPatrak['toKMDate']>;
  coverPage: FormControl<IKamalMaryadaPatrak['coverPage']>;
  cropTypeNumber: FormControl<IKamalMaryadaPatrak['cropTypeNumber']>;
  cropType: FormControl<IKamalMaryadaPatrak['cropType']>;
  fromHector: FormControl<IKamalMaryadaPatrak['fromHector']>;
  toHector: FormControl<IKamalMaryadaPatrak['toHector']>;
  defaulterName: FormControl<IKamalMaryadaPatrak['defaulterName']>;
  suchakName: FormControl<IKamalMaryadaPatrak['suchakName']>;
  anumodakName: FormControl<IKamalMaryadaPatrak['anumodakName']>;
  meetingDate: FormControl<IKamalMaryadaPatrak['meetingDate']>;
  resolutionNumber: FormControl<IKamalMaryadaPatrak['resolutionNumber']>;
};

export type KamalMaryadaPatrakFormGroup = FormGroup<KamalMaryadaPatrakFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class KamalMaryadaPatrakFormService {
  createKamalMaryadaPatrakFormGroup(kamalMaryadaPatrak: KamalMaryadaPatrakFormGroupInput = { id: null }): KamalMaryadaPatrakFormGroup {
    const kamalMaryadaPatrakRawValue = {
      ...this.getFormDefaults(),
      ...kamalMaryadaPatrak,
    };
    return new FormGroup<KamalMaryadaPatrakFormGroupContent>({
      id: new FormControl(
        { value: kamalMaryadaPatrakRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      cropLoan: new FormControl(kamalMaryadaPatrakRawValue.cropLoan),
      kmChart: new FormControl(kamalMaryadaPatrakRawValue.kmChart),
      demand: new FormControl(kamalMaryadaPatrakRawValue.demand),
      kmSummary: new FormControl(kamalMaryadaPatrakRawValue.kmSummary),
      kmDate: new FormControl(kamalMaryadaPatrakRawValue.kmDate),
      toKMDate: new FormControl(kamalMaryadaPatrakRawValue.toKMDate),
      coverPage: new FormControl(kamalMaryadaPatrakRawValue.coverPage),
      cropTypeNumber: new FormControl(kamalMaryadaPatrakRawValue.cropTypeNumber),
      cropType: new FormControl(kamalMaryadaPatrakRawValue.cropType),
      fromHector: new FormControl(kamalMaryadaPatrakRawValue.fromHector),
      toHector: new FormControl(kamalMaryadaPatrakRawValue.toHector),
      defaulterName: new FormControl(kamalMaryadaPatrakRawValue.defaulterName),
      suchakName: new FormControl(kamalMaryadaPatrakRawValue.suchakName),
      anumodakName: new FormControl(kamalMaryadaPatrakRawValue.anumodakName),
      meetingDate: new FormControl(kamalMaryadaPatrakRawValue.meetingDate),
      resolutionNumber: new FormControl(kamalMaryadaPatrakRawValue.resolutionNumber),
    });
  }

  getKamalMaryadaPatrak(form: KamalMaryadaPatrakFormGroup): IKamalMaryadaPatrak | NewKamalMaryadaPatrak {
    return form.getRawValue() as IKamalMaryadaPatrak | NewKamalMaryadaPatrak;
  }

  resetForm(form: KamalMaryadaPatrakFormGroup, kamalMaryadaPatrak: KamalMaryadaPatrakFormGroupInput): void {
    const kamalMaryadaPatrakRawValue = { ...this.getFormDefaults(), ...kamalMaryadaPatrak };
    form.reset(
      {
        ...kamalMaryadaPatrakRawValue,
        id: { value: kamalMaryadaPatrakRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): KamalMaryadaPatrakFormDefaults {
    return {
      id: null,
      coverPage: false,
    };
  }
}
