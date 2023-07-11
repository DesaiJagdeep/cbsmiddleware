import { ILandTypeMaster, NewLandTypeMaster } from './land-type-master.model';

export const sampleWithRequiredData: ILandTypeMaster = {
  id: 56114,
};

export const sampleWithPartialData: ILandTypeMaster = {
  id: 8557,
  landTypeCode: 14218,
};

export const sampleWithFullData: ILandTypeMaster = {
  id: 15008,
  landTypeCode: 56004,
  landType: 'Tactics',
};

export const sampleWithNewData: NewLandTypeMaster = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
