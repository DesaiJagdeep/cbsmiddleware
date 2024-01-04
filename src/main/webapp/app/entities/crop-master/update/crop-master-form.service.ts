import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ICropMaster, NewCropMaster } from '../crop-master.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICropMaster for edit and NewCropMasterFormGroupInput for create.
 */
type CropMasterFormGroupInput = ICropMaster | PartialWithRequiredKeyOf<NewCropMaster>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ICropMaster | NewCropMaster> = Omit<
  T,
  'vatapFromDay' | 'vatapToMonth' | 'lastToDay' | 'lastToMonth' | 'dueDay' | 'dueMonth'
> & {
  vatapFromDay?: string | null;
  vatapToMonth?: string | null;
  lastToDay?: string | null;
  lastToMonth?: string | null;
  dueDay?: string | null;
  dueMonth?: string | null;
};

type CropMasterFormRawValue = FormValueOf<ICropMaster>;

type NewCropMasterFormRawValue = FormValueOf<NewCropMaster>;

type CropMasterFormDefaults = Pick<
  NewCropMaster,
  'id' | 'vatapFromDay' | 'vatapToMonth' | 'lastToDay' | 'lastToMonth' | 'dueDay' | 'dueMonth'
>;

type CropMasterFormGroupContent = {
  id: FormControl<CropMasterFormRawValue['id'] | NewCropMaster['id']>;
  cropCode: FormControl<CropMasterFormRawValue['cropCode']>;
  cropName: FormControl<CropMasterFormRawValue['cropName']>;
  categoryCode: FormControl<CropMasterFormRawValue['categoryCode']>;
  categoryName: FormControl<CropMasterFormRawValue['categoryName']>;
  vatapFromDay: FormControl<CropMasterFormRawValue['vatapFromDay']>;
  vatapToMonth: FormControl<CropMasterFormRawValue['vatapToMonth']>;
  lastToDay: FormControl<CropMasterFormRawValue['lastToDay']>;
  lastToMonth: FormControl<CropMasterFormRawValue['lastToMonth']>;
  dueDay: FormControl<CropMasterFormRawValue['dueDay']>;
  dueMonth: FormControl<CropMasterFormRawValue['dueMonth']>;
  cropPeriod: FormControl<CropMasterFormRawValue['cropPeriod']>;
  sanctionAmt: FormControl<CropMasterFormRawValue['sanctionAmt']>;
  previousAmt: FormControl<CropMasterFormRawValue['previousAmt']>;
};

export type CropMasterFormGroup = FormGroup<CropMasterFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CropMasterFormService {
  createCropMasterFormGroup(cropMaster: CropMasterFormGroupInput = { id: null }): CropMasterFormGroup {
    const cropMasterRawValue = this.convertCropMasterToCropMasterRawValue({
      ...this.getFormDefaults(),
      ...cropMaster,
    });
    return new FormGroup<CropMasterFormGroupContent>({
      id: new FormControl(
        { value: cropMasterRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      cropCode: new FormControl(cropMasterRawValue.cropCode),
      cropName: new FormControl(cropMasterRawValue.cropName),
      categoryCode: new FormControl(cropMasterRawValue.categoryCode),
      categoryName: new FormControl(cropMasterRawValue.categoryName),
      vatapFromDay: new FormControl(cropMasterRawValue.vatapFromDay),
      vatapToMonth: new FormControl(cropMasterRawValue.vatapToMonth),
      lastToDay: new FormControl(cropMasterRawValue.lastToDay),
      lastToMonth: new FormControl(cropMasterRawValue.lastToMonth),
      dueDay: new FormControl(cropMasterRawValue.dueDay),
      dueMonth: new FormControl(cropMasterRawValue.dueMonth),
      cropPeriod: new FormControl(cropMasterRawValue.cropPeriod),
      sanctionAmt: new FormControl(cropMasterRawValue.sanctionAmt),
      previousAmt: new FormControl(cropMasterRawValue.previousAmt),
    });
  }

  getCropMaster(form: CropMasterFormGroup): ICropMaster | NewCropMaster {
    return this.convertCropMasterRawValueToCropMaster(form.getRawValue() as CropMasterFormRawValue | NewCropMasterFormRawValue);
  }

  resetForm(form: CropMasterFormGroup, cropMaster: CropMasterFormGroupInput): void {
    const cropMasterRawValue = this.convertCropMasterToCropMasterRawValue({ ...this.getFormDefaults(), ...cropMaster });
    form.reset(
      {
        ...cropMasterRawValue,
        id: { value: cropMasterRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CropMasterFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      vatapFromDay: currentTime,
      vatapToMonth: currentTime,
      lastToDay: currentTime,
      lastToMonth: currentTime,
      dueDay: currentTime,
      dueMonth: currentTime,
    };
  }

  private convertCropMasterRawValueToCropMaster(
    rawCropMaster: CropMasterFormRawValue | NewCropMasterFormRawValue,
  ): ICropMaster | NewCropMaster {
    return {
      ...rawCropMaster,
      vatapFromDay: dayjs(rawCropMaster.vatapFromDay, DATE_TIME_FORMAT),
      vatapToMonth: dayjs(rawCropMaster.vatapToMonth, DATE_TIME_FORMAT),
      lastToDay: dayjs(rawCropMaster.lastToDay, DATE_TIME_FORMAT),
      lastToMonth: dayjs(rawCropMaster.lastToMonth, DATE_TIME_FORMAT),
      dueDay: dayjs(rawCropMaster.dueDay, DATE_TIME_FORMAT),
      dueMonth: dayjs(rawCropMaster.dueMonth, DATE_TIME_FORMAT),
    };
  }

  private convertCropMasterToCropMasterRawValue(
    cropMaster: ICropMaster | (Partial<NewCropMaster> & CropMasterFormDefaults),
  ): CropMasterFormRawValue | PartialWithRequiredKeyOf<NewCropMasterFormRawValue> {
    return {
      ...cropMaster,
      vatapFromDay: cropMaster.vatapFromDay ? cropMaster.vatapFromDay.format(DATE_TIME_FORMAT) : undefined,
      vatapToMonth: cropMaster.vatapToMonth ? cropMaster.vatapToMonth.format(DATE_TIME_FORMAT) : undefined,
      lastToDay: cropMaster.lastToDay ? cropMaster.lastToDay.format(DATE_TIME_FORMAT) : undefined,
      lastToMonth: cropMaster.lastToMonth ? cropMaster.lastToMonth.format(DATE_TIME_FORMAT) : undefined,
      dueDay: cropMaster.dueDay ? cropMaster.dueDay.format(DATE_TIME_FORMAT) : undefined,
      dueMonth: cropMaster.dueMonth ? cropMaster.dueMonth.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
