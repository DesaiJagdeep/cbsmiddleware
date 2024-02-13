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
  financialYear: FormControl<IKamalCrop['financialYear']>;
  memberCount: FormControl<IKamalCrop['memberCount']>;
  area: FormControl<IKamalCrop['area']>;
  pacsAmount: FormControl<IKamalCrop['pacsAmount']>;
  branchAmount: FormControl<IKamalCrop['branchAmount']>;
  headOfficeAmount: FormControl<IKamalCrop['headOfficeAmount']>;
  divisionalOfficeAmount: FormControl<IKamalCrop['divisionalOfficeAmount']>;
  cropEligibilityAmount: FormControl<IKamalCrop['cropEligibilityAmount']>;
  kamalSociety: FormControl<IKamalCrop['kamalSociety']>;
  farmerTypeMaster: FormControl<IKamalCrop['farmerTypeMaster']>;
  seasonMaster: FormControl<IKamalCrop['seasonMaster']>;
  cropMaster: FormControl<IKamalCrop['cropMaster']>;
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
      pacsNumber: new FormControl(kamalCropRawValue.pacsNumber),
      financialYear: new FormControl(kamalCropRawValue.financialYear),
      memberCount: new FormControl(kamalCropRawValue.memberCount),
      area: new FormControl(kamalCropRawValue.area),
      pacsAmount: new FormControl(kamalCropRawValue.pacsAmount),
      branchAmount: new FormControl(kamalCropRawValue.branchAmount),
      headOfficeAmount: new FormControl(kamalCropRawValue.headOfficeAmount),
      divisionalOfficeAmount: new FormControl(kamalCropRawValue.divisionalOfficeAmount),
      cropEligibilityAmount: new FormControl(kamalCropRawValue.cropEligibilityAmount),
      kamalSociety: new FormControl(kamalCropRawValue.kamalSociety),
      farmerTypeMaster: new FormControl(kamalCropRawValue.farmerTypeMaster),
      seasonMaster: new FormControl(kamalCropRawValue.seasonMaster),
      cropMaster: new FormControl(kamalCropRawValue.cropMaster),
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
