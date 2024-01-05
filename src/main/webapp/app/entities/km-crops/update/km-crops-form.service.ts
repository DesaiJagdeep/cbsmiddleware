import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IKmCrops, NewKmCrops } from '../km-crops.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IKmCrops for edit and NewKmCropsFormGroupInput for create.
 */
type KmCropsFormGroupInput = IKmCrops | PartialWithRequiredKeyOf<NewKmCrops>;

type KmCropsFormDefaults = Pick<NewKmCrops, 'id'>;

type KmCropsFormGroupContent = {
  id: FormControl<IKmCrops['id'] | NewKmCrops['id']>;
  cropName: FormControl<IKmCrops['cropName']>;
  cropNameMr: FormControl<IKmCrops['cropNameMr']>;
  hector: FormControl<IKmCrops['hector']>;
  hectorMr: FormControl<IKmCrops['hectorMr']>;
  are: FormControl<IKmCrops['are']>;
  areMr: FormControl<IKmCrops['areMr']>;
  prviousAmt: FormControl<IKmCrops['prviousAmt']>;
  previousAmtMr: FormControl<IKmCrops['previousAmtMr']>;
  demand: FormControl<IKmCrops['demand']>;
  demandMr: FormControl<IKmCrops['demandMr']>;
  society: FormControl<IKmCrops['society']>;
  societyMr: FormControl<IKmCrops['societyMr']>;
  bankAmt: FormControl<IKmCrops['bankAmt']>;
  bankAmtMr: FormControl<IKmCrops['bankAmtMr']>;
  noOfTree: FormControl<IKmCrops['noOfTree']>;
  noOfTreeMr: FormControl<IKmCrops['noOfTreeMr']>;
  kmDetails: FormControl<IKmCrops['kmDetails']>;
};

export type KmCropsFormGroup = FormGroup<KmCropsFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class KmCropsFormService {
  createKmCropsFormGroup(kmCrops: KmCropsFormGroupInput = { id: null }): KmCropsFormGroup {
    const kmCropsRawValue = {
      ...this.getFormDefaults(),
      ...kmCrops,
    };
    return new FormGroup<KmCropsFormGroupContent>({
      id: new FormControl(
        { value: kmCropsRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      cropName: new FormControl(kmCropsRawValue.cropName),
      cropNameMr: new FormControl(kmCropsRawValue.cropNameMr),
      hector: new FormControl(kmCropsRawValue.hector),
      hectorMr: new FormControl(kmCropsRawValue.hectorMr),
      are: new FormControl(kmCropsRawValue.are),
      areMr: new FormControl(kmCropsRawValue.areMr),
      prviousAmt: new FormControl(kmCropsRawValue.prviousAmt),
      previousAmtMr: new FormControl(kmCropsRawValue.previousAmtMr),
      demand: new FormControl(kmCropsRawValue.demand),
      demandMr: new FormControl(kmCropsRawValue.demandMr),
      society: new FormControl(kmCropsRawValue.society),
      societyMr: new FormControl(kmCropsRawValue.societyMr),
      bankAmt: new FormControl(kmCropsRawValue.bankAmt),
      bankAmtMr: new FormControl(kmCropsRawValue.bankAmtMr),
      noOfTree: new FormControl(kmCropsRawValue.noOfTree),
      noOfTreeMr: new FormControl(kmCropsRawValue.noOfTreeMr),
      kmDetails: new FormControl(kmCropsRawValue.kmDetails),
    });
  }

  getKmCrops(form: KmCropsFormGroup): IKmCrops | NewKmCrops {
    return form.getRawValue() as IKmCrops | NewKmCrops;
  }

  resetForm(form: KmCropsFormGroup, kmCrops: KmCropsFormGroupInput): void {
    const kmCropsRawValue = { ...this.getFormDefaults(), ...kmCrops };
    form.reset(
      {
        ...kmCropsRawValue,
        id: { value: kmCropsRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): KmCropsFormDefaults {
    return {
      id: null,
    };
  }
}
