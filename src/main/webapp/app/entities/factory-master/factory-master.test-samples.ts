import { IFactoryMaster, NewFactoryMaster } from './factory-master.model';

export const sampleWithRequiredData: IFactoryMaster = {
  id: 51172,
};

export const sampleWithPartialData: IFactoryMaster = {
  id: 31373,
  factoryName: 'deposit Kina New',
  status: false,
};

export const sampleWithFullData: IFactoryMaster = {
  id: 22527,
  factoryName: 'Buckinghamshire Beauty',
  factoryNameMr: 'partnerships ADP channels',
  factoryCode: 63260,
  factoryCodeMr: 'Internal Account payment',
  factoryAddress: 'alarm Steel PNG',
  factoryAddressMr: 'pixel systems panel',
  description: 'Gardens teal drive',
  status: true,
};

export const sampleWithNewData: NewFactoryMaster = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
