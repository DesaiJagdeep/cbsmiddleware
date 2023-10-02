import { IKarkhanaVasuli, NewKarkhanaVasuli } from './karkhana-vasuli.model';

export const sampleWithRequiredData: IKarkhanaVasuli = {
  id: 93320,
};

export const sampleWithPartialData: IKarkhanaVasuli = {
  id: 66449,
  name: 'Dynamic Ball Ireland',
  talukaName: 'Unit vortals transmit',
  branchName: 'Texas Franc',
  defaulterName: 'green Washington',
};

export const sampleWithFullData: IKarkhanaVasuli = {
  id: 86233,
  khataNumber: 'cross-platform synergies',
  name: 'Madagascar Unbranded',
  societyName: 'Marketing Central Markets',
  talukaName: 'Islands e-commerce',
  branchName: 'Avon',
  defaulterName: 'granular Massachusetts',
};

export const sampleWithNewData: NewKarkhanaVasuli = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
