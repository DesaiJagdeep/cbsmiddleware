import { IFarmerTypeMaster, NewFarmerTypeMaster } from './farmer-type-master.model';

export const sampleWithRequiredData: IFarmerTypeMaster = {
  id: 10043,
};

export const sampleWithPartialData: IFarmerTypeMaster = {
  id: 37781,
  farmerType: 'platforms Facilitator Industrial',
};

export const sampleWithFullData: IFarmerTypeMaster = {
  id: 68695,
  farmerTypeCode: 86945,
  farmerType: 'auxiliary tan',
};

export const sampleWithNewData: NewFarmerTypeMaster = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
