import dayjs from 'dayjs/esm';

import { IKmLoans, NewKmLoans } from './km-loans.model';

export const sampleWithRequiredData: IKmLoans = {
  id: 39551,
};

export const sampleWithPartialData: IKmLoans = {
  id: 69637,
  hector: 13220,
  are: 48235,
  aremr: 'Synchronised orchestration Incredible',
  noOfTreeMr: 'Fiji Soft Functionality',
  sanctionAmt: 16210,
  receivableAmt: 69487,
  dueAmt: 14489,
};

export const sampleWithFullData: IKmLoans = {
  id: 25103,
  hector: 74450,
  hectorMr: 'copy Specialist Spain',
  are: 67247,
  aremr: 'parse',
  noOfTree: 94211,
  noOfTreeMr: 'PCI Micronesia',
  sanctionAmt: 12654,
  sanctionAmtMr: 'alarm Secured',
  loanAmt: 42578,
  loanAmtMr: 'calculating metrics',
  receivableAmt: 80529,
  receivableAmtMr: 'Cambodia bleeding-edge',
  dueAmt: 3107,
  dueAmtMr: 'Legacy',
  dueDate: dayjs('2024-01-07T23:16'),
  dueDateMr: 'Managed Singapore',
  spare: 'Associate',
};

export const sampleWithNewData: NewKmLoans = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
