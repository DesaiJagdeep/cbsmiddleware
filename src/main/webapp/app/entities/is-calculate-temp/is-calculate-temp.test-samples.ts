import { IIsCalculateTemp, NewIsCalculateTemp } from './is-calculate-temp.model';

export const sampleWithRequiredData: IIsCalculateTemp = {
  id: 4837,
};

export const sampleWithPartialData: IIsCalculateTemp = {
  id: 96885,
  serialNo: 36941,
};

export const sampleWithFullData: IIsCalculateTemp = {
  id: 71264,
  serialNo: 44243,
  financialYear: 'copying time-frame Forges',
};

export const sampleWithNewData: NewIsCalculateTemp = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
