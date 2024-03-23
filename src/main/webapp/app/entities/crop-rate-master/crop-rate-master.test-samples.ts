import { ICropRateMaster, NewCropRateMaster } from './crop-rate-master.model';

export const sampleWithRequiredData: ICropRateMaster = {
  id: 35624,
  financialYear: 'channels Drive',
  currentAmount: 26737,
};

export const sampleWithPartialData: ICropRateMaster = {
  id: 34486,
  financialYear: 'gold',
  currentAmount: 70282,
  percentage: 43059,
  activeFlag: false,
};

export const sampleWithFullData: ICropRateMaster = {
  id: 95288,
  financialYear: 'invoice Dynamic Accounts',
  currentAmount: 6599,
  currentAmountMr: 'virtual schemas deposit',
  percentage: 14430,
  activeFlag: false,
};

export const sampleWithNewData: NewCropRateMaster = {
  financialYear: 'copying Borders',
  currentAmount: 10900,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
