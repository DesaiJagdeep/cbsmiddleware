import { ICropMaster, NewCropMaster } from './crop-master.model';

export const sampleWithRequiredData: ICropMaster = {
  id: 98443,
};

export const sampleWithPartialData: ICropMaster = {
  id: 20178,
  cropName: 'homogeneous Rupee',
  categoryCode: 'Synchronised Savings Integrated',
};

export const sampleWithFullData: ICropMaster = {
  id: 15822,
  cropCode: 'Fantastic Jewelery',
  cropName: 'navigating',
  categoryCode: 'compressing',
};

export const sampleWithNewData: NewCropMaster = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
