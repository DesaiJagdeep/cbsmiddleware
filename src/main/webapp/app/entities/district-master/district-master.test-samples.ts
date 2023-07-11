import { IDistrictMaster, NewDistrictMaster } from './district-master.model';

export const sampleWithRequiredData: IDistrictMaster = {
  id: 71184,
};

export const sampleWithPartialData: IDistrictMaster = {
  id: 82634,
  stateCode: 'payment brand',
};

export const sampleWithFullData: IDistrictMaster = {
  id: 89754,
  districtCode: 'Keyboard Market Wooden',
  districtName: 'Dakota invoice',
  stateCode: 'Multi-channelled Games static',
};

export const sampleWithNewData: NewDistrictMaster = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
