import { IRelativeMaster, NewRelativeMaster } from './relative-master.model';

export const sampleWithRequiredData: IRelativeMaster = {
  id: 18402,
};

export const sampleWithPartialData: IRelativeMaster = {
  id: 89362,
  relativeCode: 35881,
};

export const sampleWithFullData: IRelativeMaster = {
  id: 33179,
  relativeCode: 99936,
  relativeName: 'Gloves',
};

export const sampleWithNewData: NewRelativeMaster = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
