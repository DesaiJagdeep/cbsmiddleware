import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IKamalCrop, NewKamalCrop } from '../kamal-crop.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IKamalCrop for edit and NewKamalCropFormGroupInput for create.
 */
type KamalCropFormGroupInput = IKamalCrop | PartialWithRequiredKeyOf<NewKamalCrop>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IKamalCrop | NewKamalCrop> = Omit<T, 'kmDate'> & {
  kmDate?: string | null;
};

type KamalCropFormRawValue = FormValueOf<IKamalCrop>;

type NewKamalCropFormRawValue = FormValueOf<NewKamalCrop>;

type KamalCropFormDefaults = Pick<NewKamalCrop, 'id' | 'kmDate'>;

type KamalCropFormGroupContent = {
  id: FormControl<KamalCropFormRawValue['id'] | NewKamalCrop['id']>;
  pacsNumber: FormControl<KamalCropFormRawValue['pacsNumber']>;
  financialYear: FormControl<KamalCropFormRawValue['financialYear']>;
  memberCount: FormControl<KamalCropFormRawValue['memberCount']>;
  area: FormControl<KamalCropFormRawValue['area']>;
  pacsAmount: FormControl<KamalCropFormRawValue['pacsAmount']>;
  branchAmount: FormControl<KamalCropFormRawValue['branchAmount']>;
  headOfficeAmount: FormControl<KamalCropFormRawValue['headOfficeAmount']>;
  divisionalOfficeAmount: FormControl<KamalCropFormRawValue['divisionalOfficeAmount']>;
  cropEligibilityAmount: FormControl<KamalCropFormRawValue['cropEligibilityAmount']>;
  kmDate: FormControl<KamalCropFormRawValue['kmDate']>;
  kmDateMr: FormControl<KamalCropFormRawValue['kmDateMr']>;
  kamalSociety: FormControl<KamalCropFormRawValue['kamalSociety']>;
  farmerTypeMaster: FormControl<KamalCropFormRawValue['farmerTypeMaster']>;
  seasonMaster: FormControl<KamalCropFormRawValue['seasonMaster']>;
  cropMaster: FormControl<KamalCropFormRawValue['cropMaster']>;
};

export type KamalCropFormGroup = FormGroup<KamalCropFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class KamalCropFormService {
  createKamalCropFormGroup(kamalCrop: KamalCropFormGroupInput = { id: null }): KamalCropFormGroup {
    const kamalCropRawValue = this.convertKamalCropToKamalCropRawValue({
      ...this.getFormDefaults(),
      ...kamalCrop,
    });
    return new FormGroup<KamalCropFormGroupContent>({
      id: new FormControl(
        { value: kamalCropRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      pacsNumber: new FormControl(kamalCropRawValue.pacsNumber),
      financialYear: new FormControl(kamalCropRawValue.financialYear),
      memberCount: new FormControl(kamalCropRawValue.memberCount),
      area: new FormControl(kamalCropRawValue.area),
      pacsAmount: new FormControl(kamalCropRawValue.pacsAmount),
      branchAmount: new FormControl(kamalCropRawValue.branchAmount),
      headOfficeAmount: new FormControl(kamalCropRawValue.headOfficeAmount),
      divisionalOfficeAmount: new FormControl(kamalCropRawValue.divisionalOfficeAmount),
      cropEligibilityAmount: new FormControl(kamalCropRawValue.cropEligibilityAmount),
      kmDate: new FormControl(kamalCropRawValue.kmDate),
      kmDateMr: new FormControl(kamalCropRawValue.kmDateMr),
      kamalSociety: new FormControl(kamalCropRawValue.kamalSociety),
      farmerTypeMaster: new FormControl(kamalCropRawValue.farmerTypeMaster),
      seasonMaster: new FormControl(kamalCropRawValue.seasonMaster),
      cropMaster: new FormControl(kamalCropRawValue.cropMaster),
    });
  }

  getKamalCrop(form: KamalCropFormGroup): IKamalCrop | NewKamalCrop {
    return this.convertKamalCropRawValueToKamalCrop(form.getRawValue() as KamalCropFormRawValue | NewKamalCropFormRawValue);
  }

  resetForm(form: KamalCropFormGroup, kamalCrop: KamalCropFormGroupInput): void {
    const kamalCropRawValue = this.convertKamalCropToKamalCropRawValue({ ...this.getFormDefaults(), ...kamalCrop });
    form.reset(
      {
        ...kamalCropRawValue,
        id: { value: kamalCropRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): KamalCropFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      kmDate: currentTime,
    };
  }

  private convertKamalCropRawValueToKamalCrop(rawKamalCrop: KamalCropFormRawValue | NewKamalCropFormRawValue): IKamalCrop | NewKamalCrop {
    return {
      ...rawKamalCrop,
      kmDate: dayjs(rawKamalCrop.kmDate, DATE_TIME_FORMAT),
    };
  }

  private convertKamalCropToKamalCropRawValue(
    kamalCrop: IKamalCrop | (Partial<NewKamalCrop> & KamalCropFormDefaults)
  ): KamalCropFormRawValue | PartialWithRequiredKeyOf<NewKamalCropFormRawValue> {
    return {
      ...kamalCrop,
      kmDate: kamalCrop.kmDate ? kamalCrop.kmDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
