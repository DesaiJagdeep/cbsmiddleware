import { IBankMaster, NewBankMaster } from './bank-master.model';

export const sampleWithRequiredData: IBankMaster = {
  id: 88129,
};

export const sampleWithPartialData: IBankMaster = {
  id: 99475,
  bankCode: 'Computer',
};

export const sampleWithFullData: IBankMaster = {
  id: 87840,
  bankCode: 'Expanded Forward',
  bankName: 'Architect',
};

export const sampleWithNewData: NewBankMaster = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
