import { IKmMaganiCrop, NewKmMaganiCrop } from './km-magani-crop.model';

export const sampleWithRequiredData: IKmMaganiCrop = {
  id: 43412,
};

export const sampleWithPartialData: IKmMaganiCrop = {
  id: 21963,
  cropName: 'Accounts channels',
};

export const sampleWithFullData: IKmMaganiCrop = {
  id: 19826,
  cropName: 'payment',
};

export const sampleWithNewData: NewKmMaganiCrop = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
