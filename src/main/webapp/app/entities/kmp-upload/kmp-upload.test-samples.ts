import { IKMPUpload, NewKMPUpload } from './kmp-upload.model';

export const sampleWithRequiredData: IKMPUpload = {
  id: 12291,
};

export const sampleWithPartialData: IKMPUpload = {
  id: 85731,
  uniqueFileName: 'services Borders Berkshire',
};

export const sampleWithFullData: IKMPUpload = {
  id: 48781,
  fileName: 'Meadows Account wireless',
  uniqueFileName: 'Gloves',
};

export const sampleWithNewData: NewKMPUpload = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
