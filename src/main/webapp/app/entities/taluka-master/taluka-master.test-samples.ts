import { ITalukaMaster, NewTalukaMaster } from './taluka-master.model';

export const sampleWithRequiredData: ITalukaMaster = {
  id: 78712,
};

export const sampleWithPartialData: ITalukaMaster = {
  id: 49732,
  talukaName: 'Future Centers Motorway',
  districtCode: 'radical Lake Man',
};

export const sampleWithFullData: ITalukaMaster = {
  id: 57226,
  talukaCode: 'deposit',
  talukaName: 'Rubber',
  districtCode: 'Tasty Shoes Stream',
};

export const sampleWithNewData: NewTalukaMaster = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
