import { IBankBranchMaster, NewBankBranchMaster } from './bank-branch-master.model';

export const sampleWithRequiredData: IBankBranchMaster = {
  id: 32283,
};

export const sampleWithPartialData: IBankBranchMaster = {
  id: 24375,
  branchAddress: 'synthesizing Practical',
  bankCode: 'Algerian Heard',
};

export const sampleWithFullData: IBankBranchMaster = {
  id: 22835,
  branchCode: 'software Licensed multi-byte',
  branchName: 'Riyal',
  branchAddress: 'yellow AGP Australia',
  bankCode: 'Lead Tasty',
};

export const sampleWithNewData: NewBankBranchMaster = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
