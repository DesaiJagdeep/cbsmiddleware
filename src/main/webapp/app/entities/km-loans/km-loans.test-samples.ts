import dayjs from 'dayjs/esm';

import { IKmLoans, NewKmLoans } from './km-loans.model';

export const sampleWithRequiredData: IKmLoans = {
  id: 39551,
  hector: 55996,
  are: 26272,
  noOfTree: 96780,
  sanctionAmt: 68023,
  loanAmt: 23302,
  receivableAmt: 59903,
  dueAmt: 89365,
  dueDate: dayjs('2024-01-08T14:11'),
};

export const sampleWithPartialData: IKmLoans = {
  id: 69637,
  hector: 13220,
  are: 48235,
  noOfTree: 69582,
  noOfTreeMr: 'EXE',
  sanctionAmt: 71061,
  loanAmt: 88494,
  loanAmtMr: 'rich',
  receivableAmt: 55082,
  dueAmt: 28538,
  dueDate: dayjs('2024-01-08T02:43'),
};

export const sampleWithFullData: IKmLoans = {
  id: 13056,
  hector: 74577,
  hectorMr: 'Functionality Chicken',
  are: 25103,
  aremr: 'input parsing Global',
  noOfTree: 3425,
  noOfTreeMr: 'optimal parsing PCI',
  sanctionAmt: 53603,
  sanctionAmtMr: 'Liaison alarm',
  loanAmt: 25106,
  loanAmtMr: 'Practical calculating metrics',
  receivableAmt: 80529,
  receivableAmtMr: 'Cambodia bleeding-edge',
  dueAmt: 3107,
  dueAmtMr: 'Legacy',
  dueDate: dayjs('2024-01-07T23:16'),
  dueDateMr: 'Managed Singapore',
  spare: 'Associate',
};

export const sampleWithNewData: NewKmLoans = {
  hector: 88387,
  are: 30432,
  noOfTree: 70588,
  sanctionAmt: 44143,
  loanAmt: 37080,
  receivableAmt: 73111,
  dueAmt: 5688,
  dueDate: dayjs('2024-01-08T03:50'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
