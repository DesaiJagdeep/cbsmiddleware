import dayjs from 'dayjs/esm';

import { ICourtCase, NewCourtCase } from './court-case.model';

export const sampleWithRequiredData: ICourtCase = {
  id: 74214,
};

export const sampleWithPartialData: ICourtCase = {
  id: 58795,
  srNo: 'matrix',
  address: 'COM National',
  interestRate: 61821,
  interestPaid: 5347,
  penalInterestPaid: 22100,
  dueMoreInterest: 61499,
  gaurentorTwoAddress: 'scalable deposit Quality-focused',
  firstNoticeDate: dayjs('2023-08-28'),
  secondNoticeDate: dayjs('2023-08-28'),
};

export const sampleWithFullData: ICourtCase = {
  id: 84448,
  srNo: 'Directives District SMS',
  accountNo: 'Automated',
  nameOfDefaulter: 'payment Adaptive Clothing',
  address: 'zero improvement Managed',
  loanType: 'Analyst',
  loanAmount: 30715,
  loanDate: dayjs('2023-08-28'),
  termOfLoan: 'driver facilitate',
  interestRate: 35699,
  installmentAmount: 83919,
  totalCredit: 22922,
  balance: 5875,
  interestPaid: 28813,
  penalInterestPaid: 99105,
  dueAmount: 5707,
  dueDate: dayjs('2023-08-28'),
  dueInterest: 67878,
  duePenalInterest: 20186,
  dueMoreInterest: 34372,
  interestRecivable: 7192,
  gaurentorOne: 'SSL user redundant',
  gaurentorOneAddress: 'parse Fantastic Hat',
  gaurentorTwo: 'tan virtual Central',
  gaurentorTwoAddress: 'national mobile silver',
  firstNoticeDate: dayjs('2023-08-29'),
  secondNoticeDate: dayjs('2023-08-29'),
};

export const sampleWithNewData: NewCourtCase = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
