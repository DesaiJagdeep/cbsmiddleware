import { IAccountHolderMaster, NewAccountHolderMaster } from './account-holder-master.model';

export const sampleWithRequiredData: IAccountHolderMaster = {
  id: 64944,
};

export const sampleWithPartialData: IAccountHolderMaster = {
  id: 26859,
  accountHolderCode: 15533,
};

export const sampleWithFullData: IAccountHolderMaster = {
  id: 99872,
  accountHolderCode: 20087,
  accountHolder: 'Danish',
};

export const sampleWithNewData: NewAccountHolderMaster = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
