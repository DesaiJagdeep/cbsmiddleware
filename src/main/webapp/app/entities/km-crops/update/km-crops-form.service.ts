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
  hector: FormControl<IKmCrops['hector']>;
  hectorMr: FormControl<IKmCrops['hectorMr']>;
  are: FormControl<IKmCrops['are']>;
  areMr: FormControl<IKmCrops['areMr']>;
  noOfTree: FormControl<IKmCrops['noOfTree']>;
  noOfTreeMr: FormControl<IKmCrops['noOfTreeMr']>;
  demand: FormControl<IKmCrops['demand']>;
  demandMr: FormControl<IKmCrops['demandMr']>;
  society: FormControl<IKmCrops['society']>;
  societyMr: FormControl<IKmCrops['societyMr']>;
  bankAmt: FormControl<IKmCrops['bankAmt']>;
  bankAmtMr: FormControl<IKmCrops['bankAmtMr']>;
  vibhagiAdhikari: FormControl<IKmCrops['vibhagiAdhikari']>;
  vibhagiAdhikariMr: FormControl<IKmCrops['vibhagiAdhikariMr']>;
  branch: FormControl<IKmCrops['branch']>;
  branchMr: FormControl<IKmCrops['branchMr']>;
  inspAmt: FormControl<IKmCrops['inspAmt']>;
  inspAmtMr: FormControl<IKmCrops['inspAmtMr']>;
  cropMaster: FormControl<IKmCrops['cropMaster']>;
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
        }
      ),
      hector: new FormControl(kmCropsRawValue.hector),
      hectorMr: new FormControl(kmCropsRawValue.hectorMr),
      are: new FormControl(kmCropsRawValue.are),
      areMr: new FormControl(kmCropsRawValue.areMr),
      noOfTree: new FormControl(kmCropsRawValue.noOfTree),
      noOfTreeMr: new FormControl(kmCropsRawValue.noOfTreeMr),
      demand: new FormControl(kmCropsRawValue.demand),
      demandMr: new FormControl(kmCropsRawValue.demandMr),
      society: new FormControl(kmCropsRawValue.society),
      societyMr: new FormControl(kmCropsRawValue.societyMr),
      bankAmt: new FormControl(kmCropsRawValue.bankAmt),
      bankAmtMr: new FormControl(kmCropsRawValue.bankAmtMr),
      vibhagiAdhikari: new FormControl(kmCropsRawValue.vibhagiAdhikari),
      vibhagiAdhikariMr: new FormControl(kmCropsRawValue.vibhagiAdhikariMr),
      branch: new FormControl(kmCropsRawValue.branch),
      branchMr: new FormControl(kmCropsRawValue.branchMr),
      inspAmt: new FormControl(kmCropsRawValue.inspAmt),
      inspAmtMr: new FormControl(kmCropsRawValue.inspAmtMr),
      cropMaster: new FormControl(kmCropsRawValue.cropMaster),
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
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): KmCropsFormDefaults {
    return {
      id: null,
    };
  }
}
