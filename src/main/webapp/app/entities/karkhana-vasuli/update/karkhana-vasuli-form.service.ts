import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IKarkhanaVasuli, NewKarkhanaVasuli } from '../karkhana-vasuli.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IKarkhanaVasuli for edit and NewKarkhanaVasuliFormGroupInput for create.
 */
type KarkhanaVasuliFormGroupInput = IKarkhanaVasuli | PartialWithRequiredKeyOf<NewKarkhanaVasuli>;

type KarkhanaVasuliFormDefaults = Pick<NewKarkhanaVasuli, 'id'>;

type KarkhanaVasuliFormGroupContent = {
  id: FormControl<IKarkhanaVasuli['id'] | NewKarkhanaVasuli['id']>;
  khataNumber: FormControl<IKarkhanaVasuli['khataNumber']>;
  name: FormControl<IKarkhanaVasuli['name']>;
  societyName: FormControl<IKarkhanaVasuli['societyName']>;
  talukaName: FormControl<IKarkhanaVasuli['talukaName']>;
  branchName: FormControl<IKarkhanaVasuli['branchName']>;
  defaulterName: FormControl<IKarkhanaVasuli['defaulterName']>;
};

export type KarkhanaVasuliFormGroup = FormGroup<KarkhanaVasuliFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class KarkhanaVasuliFormService {
  createKarkhanaVasuliFormGroup(karkhanaVasuli: KarkhanaVasuliFormGroupInput = { id: null }): KarkhanaVasuliFormGroup {
    const karkhanaVasuliRawValue = {
      ...this.getFormDefaults(),
      ...karkhanaVasuli,
    };
    return new FormGroup<KarkhanaVasuliFormGroupContent>({
      id: new FormControl(
        { value: karkhanaVasuliRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      khataNumber: new FormControl(karkhanaVasuliRawValue.khataNumber),
      name: new FormControl(karkhanaVasuliRawValue.name),
      societyName: new FormControl(karkhanaVasuliRawValue.societyName),
      talukaName: new FormControl(karkhanaVasuliRawValue.talukaName),
      branchName: new FormControl(karkhanaVasuliRawValue.branchName),
      defaulterName: new FormControl(karkhanaVasuliRawValue.defaulterName),
    });
  }

  getKarkhanaVasuli(form: KarkhanaVasuliFormGroup): IKarkhanaVasuli | NewKarkhanaVasuli {
    return form.getRawValue() as IKarkhanaVasuli | NewKarkhanaVasuli;
  }

  resetForm(form: KarkhanaVasuliFormGroup, karkhanaVasuli: KarkhanaVasuliFormGroupInput): void {
    const karkhanaVasuliRawValue = { ...this.getFormDefaults(), ...karkhanaVasuli };
    form.reset(
      {
        ...karkhanaVasuliRawValue,
        id: { value: karkhanaVasuliRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): KarkhanaVasuliFormDefaults {
    return {
      id: null,
    };
  }
}
