import { IFactoryMaster, NewFactoryMaster } from './factory-master.model';

export const sampleWithRequiredData: IFactoryMaster = {
  id: 18251,
};

export const sampleWithPartialData: IFactoryMaster = {
  id: 337,
  factoryName: 'majestically yuck psst',
  factoryCodeMr: 'fooey worriedly rough',
  factoryAddressMr: 'alongside',
  status: false,
};

export const sampleWithFullData: IFactoryMaster = {
  id: 23052,
  factoryName: 'sympathetically',
  factoryNameMr: 'rusty oof dismantle',
  factoryCode: 32181,
  factoryCodeMr: 'safely',
  factoryAddress: 'athwart therapy lest',
  factoryAddressMr: 'boo',
  description: 'heavily',
  status: false,
};

export const sampleWithNewData: NewFactoryMaster = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
