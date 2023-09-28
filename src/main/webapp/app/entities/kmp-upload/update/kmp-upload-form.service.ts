import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IKMPUpload, NewKMPUpload } from '../kmp-upload.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IKMPUpload for edit and NewKMPUploadFormGroupInput for create.
 */
type KMPUploadFormGroupInput = IKMPUpload | PartialWithRequiredKeyOf<NewKMPUpload>;

type KMPUploadFormDefaults = Pick<NewKMPUpload, 'id'>;

type KMPUploadFormGroupContent = {
  id: FormControl<IKMPUpload['id'] | NewKMPUpload['id']>;
  fileName: FormControl<IKMPUpload['fileName']>;
  uniqueFileName: FormControl<IKMPUpload['uniqueFileName']>;
};

export type KMPUploadFormGroup = FormGroup<KMPUploadFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class KMPUploadFormService {
  createKMPUploadFormGroup(kMPUpload: KMPUploadFormGroupInput = { id: null }): KMPUploadFormGroup {
    const kMPUploadRawValue = {
      ...this.getFormDefaults(),
      ...kMPUpload,
    };
    return new FormGroup<KMPUploadFormGroupContent>({
      id: new FormControl(
        { value: kMPUploadRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      fileName: new FormControl(kMPUploadRawValue.fileName),
      uniqueFileName: new FormControl(kMPUploadRawValue.uniqueFileName),
    });
  }

  getKMPUpload(form: KMPUploadFormGroup): IKMPUpload | NewKMPUpload {
    return form.getRawValue() as IKMPUpload | NewKMPUpload;
  }

  resetForm(form: KMPUploadFormGroup, kMPUpload: KMPUploadFormGroupInput): void {
    const kMPUploadRawValue = { ...this.getFormDefaults(), ...kMPUpload };
    form.reset(
      {
        ...kMPUploadRawValue,
        id: { value: kMPUploadRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): KMPUploadFormDefaults {
    return {
      id: null,
    };
  }
}
