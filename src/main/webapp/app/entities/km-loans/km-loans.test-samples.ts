import dayjs from 'dayjs/esm';

import { IKmLoans, NewKmLoans } from './km-loans.model';

export const sampleWithRequiredData: IKmLoans = {
  id: 3177,
};

export const sampleWithPartialData: IKmLoans = {
  id: 28790,
  cropName: 'ack er honesty',
  cropNameMr: 'eurocentrism circa',
  loanAmount: 28433.06,
  areMr: 'long-term tankful educated',
  receivableAmtMr: 'able gracefully',
  dueAmt: 14862.57,
};

export const sampleWithFullData: IKmLoans = {
  id: 30340,
  cropName: 'absent',
  cropNameMr: 'belly grown woozy',
  loanDate: dayjs('2024-01-04T17:02'),
  loanAmount: 27835.55,
  loanAmountMr: 'keep miniature',
  are: 6133.12,
  areMr: 'till quixotic',
  receivableAmt: 4420.85,
  receivableAmtMr: 'infamous',
  dueAmt: 17184.47,
  dueAmtMr: 'aha yet',
  dueDate: dayjs('2024-01-05T02:24'),
};

export const sampleWithNewData: NewKmLoans = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
