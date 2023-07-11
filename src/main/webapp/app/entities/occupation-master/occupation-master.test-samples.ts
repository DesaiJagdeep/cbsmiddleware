import { IOccupationMaster, NewOccupationMaster } from './occupation-master.model';

export const sampleWithRequiredData: IOccupationMaster = {
  id: 96554,
};

export const sampleWithPartialData: IOccupationMaster = {
  id: 50806,
};

export const sampleWithFullData: IOccupationMaster = {
  id: 30666,
  occupationCode: 82695,
  occupationName: 'Grocery Cheese User-friendly',
};

export const sampleWithNewData: NewOccupationMaster = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
