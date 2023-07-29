import { IBankBranchMaster, NewBankBranchMaster } from './bank-branch-master.model';

export const sampleWithRequiredData: IBankBranchMaster = {
  id: 32283,
};

export const sampleWithPartialData: IBankBranchMaster = {
  id: 61937,
  branchAddress: 'Account',
};

export const sampleWithFullData: IBankBranchMaster = {
  id: 55851,
  branchCode: 'Glens',
  branchName: 'Adaptive integrate compressing',
  branchAddress: 'Licensed multi-byte Metal',
};

export const sampleWithNewData: NewBankBranchMaster = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
