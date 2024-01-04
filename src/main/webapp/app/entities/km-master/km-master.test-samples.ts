import { IKmMaster, NewKmMaster } from './km-master.model';

export const sampleWithRequiredData: IKmMaster = {
  id: 16326,
};

export const sampleWithPartialData: IKmMaster = {
  id: 32269,
  farmerNameMr: 'nor very bulk',
  farmerAddress: 'oof archaeology',
  casteMr: 'although',
  areaHect: 'sarcastic following',
  aadharNoMr: 'fond cheerfully interestingly',
  panNo: 7503,
  kccNo: 'hm',
  kccNoMr: 'finally till',
  savingNo: 'eurocentrism',
  entryFlag: 'creepy yuck derrick',
};

export const sampleWithFullData: IKmMaster = {
  id: 23792,
  branchCode: 'per mantle',
  farmerName: 'incriminate before',
  farmerNameMr: 'cohere',
  farmerAddress: 'chubby',
  farmerAddressMr: 'embellish before',
  gender: 'enraged strengthen under',
  caste: 'infer',
  casteMr: 'except pace',
  pacsNumber: 'fat fortunately westernize',
  areaHect: 'occasion',
  aadharNo: 14773,
  aadharNoMr: 'winkle',
  panNo: 24839,
  panNoMr: 'despite so',
  mobileNo: 'mmm reproachfully glister',
  mobileNoMr: 'although including',
  kccNo: 'gain but than',
  kccNoMr: 'very',
  savingNo: 'lest by',
  savingNoMr: 'qua excursion',
  entryFlag: 'over because clear-cut',
};

export const sampleWithNewData: NewKmMaster = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
