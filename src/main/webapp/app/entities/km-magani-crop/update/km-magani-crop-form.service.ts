import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IKmMaganiCrop, NewKmMaganiCrop } from '../km-magani-crop.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IKmMaganiCrop for edit and NewKmMaganiCropFormGroupInput for create.
 */
type KmMaganiCropFormGroupInput = IKmMaganiCrop | PartialWithRequiredKeyOf<NewKmMaganiCrop>;

type KmMaganiCropFormDefaults = Pick<NewKmMaganiCrop, 'id'>;

type KmMaganiCropFormGroupContent = {
  id: FormControl<IKmMaganiCrop['id'] | NewKmMaganiCrop['id']>;
  cropName: FormControl<IKmMaganiCrop['cropName']>;
  kmMagani: FormControl<IKmMaganiCrop['kmMagani']>;
};

export type KmMaganiCropFormGroup = FormGroup<KmMaganiCropFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class KmMaganiCropFormService {
  createKmMaganiCropFormGroup(kmMaganiCrop: KmMaganiCropFormGroupInput = { id: null }): KmMaganiCropFormGroup {
    const kmMaganiCropRawValue = {
      ...this.getFormDefaults(),
      ...kmMaganiCrop,
    };
    return new FormGroup<KmMaganiCropFormGroupContent>({
      id: new FormControl(
        { value: kmMaganiCropRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      cropName: new FormControl(kmMaganiCropRawValue.cropName),
      kmMagani: new FormControl(kmMaganiCropRawValue.kmMagani),
    });
  }

  getKmMaganiCrop(form: KmMaganiCropFormGroup): IKmMaganiCrop | NewKmMaganiCrop {
    return form.getRawValue() as IKmMaganiCrop | NewKmMaganiCrop;
  }

  resetForm(form: KmMaganiCropFormGroup, kmMaganiCrop: KmMaganiCropFormGroupInput): void {
    const kmMaganiCropRawValue = { ...this.getFormDefaults(), ...kmMaganiCrop };
    form.reset(
      {
        ...kmMaganiCropRawValue,
        id: { value: kmMaganiCropRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): KmMaganiCropFormDefaults {
    return {
      id: null,
    };
  }
}
