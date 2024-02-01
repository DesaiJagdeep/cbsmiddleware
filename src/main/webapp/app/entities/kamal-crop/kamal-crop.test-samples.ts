import { IKamalCrop, NewKamalCrop } from './kamal-crop.model';

export const sampleWithRequiredData: IKamalCrop = {
  id: 94816,
};

export const sampleWithPartialData: IKamalCrop = {
  id: 53403,
  pacsNumber: 'Car Bridge',
  financialYear: 'standardization Future-proofed experiences',
};

export const sampleWithFullData: IKamalCrop = {
  id: 96758,
  pacsNumber: 'generation',
  financialYear: 'efficient',
};

export const sampleWithNewData: NewKamalCrop = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
