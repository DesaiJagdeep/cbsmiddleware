import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

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

type KamalCropFormDefaults = Pick<NewKamalCrop, 'id'>;

type KamalCropFormGroupContent = {
  id: FormControl<IKamalCrop['id'] | NewKamalCrop['id']>;
  pacsNumber: FormControl<IKamalCrop['pacsNumber']>;
  memNo: FormControl<IKamalCrop['memNo']>;
  memHector: FormControl<IKamalCrop['memHector']>;
  memNoMr: FormControl<IKamalCrop['memNoMr']>;
  memHectorMr: FormControl<IKamalCrop['memHectorMr']>;
  memAar: FormControl<IKamalCrop['memAar']>;
  memAarMr: FormControl<IKamalCrop['memAarMr']>;
  farmerTypeMaster: FormControl<IKamalCrop['farmerTypeMaster']>;
  cropHangam: FormControl<IKamalCrop['cropHangam']>;
  cropMaster: FormControl<IKamalCrop['cropMaster']>;
  kamalSociety: FormControl<IKamalCrop['kamalSociety']>;
};

export type KamalCropFormGroup = FormGroup<KamalCropFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class KamalCropFormService {
  createKamalCropFormGroup(kamalCrop: KamalCropFormGroupInput = { id: null }): KamalCropFormGroup {
    const kamalCropRawValue = {
      ...this.getFormDefaults(),
      ...kamalCrop,
    };
    return new FormGroup<KamalCropFormGroupContent>({
      id: new FormControl(
        { value: kamalCropRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      pacsNumber: new FormControl(kamalCropRawValue.pacsNumber, {
        validators: [Validators.required],
      }),
      memNo: new FormControl(kamalCropRawValue.memNo, {
        validators: [Validators.required],
      }),
      memHector: new FormControl(kamalCropRawValue.memHector, {
        validators: [Validators.required],
      }),
      memNoMr: new FormControl(kamalCropRawValue.memNoMr),
      memHectorMr: new FormControl(kamalCropRawValue.memHectorMr),
      memAar: new FormControl(kamalCropRawValue.memAar),
      memAarMr: new FormControl(kamalCropRawValue.memAarMr),
      farmerTypeMaster: new FormControl(kamalCropRawValue.farmerTypeMaster),
      cropHangam: new FormControl(kamalCropRawValue.cropHangam),
      cropMaster: new FormControl(kamalCropRawValue.cropMaster),
      kamalSociety: new FormControl(kamalCropRawValue.kamalSociety),
    });
  }

  getKamalCrop(form: KamalCropFormGroup): IKamalCrop | NewKamalCrop {
    return form.getRawValue() as IKamalCrop | NewKamalCrop;
  }

  resetForm(form: KamalCropFormGroup, kamalCrop: KamalCropFormGroupInput): void {
    const kamalCropRawValue = { ...this.getFormDefaults(), ...kamalCrop };
    form.reset(
      {
        ...kamalCropRawValue,
        id: { value: kamalCropRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): KamalCropFormDefaults {
    return {
      id: null,
    };
  }
}
