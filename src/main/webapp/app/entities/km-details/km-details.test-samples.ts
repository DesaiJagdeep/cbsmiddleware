import dayjs from 'dayjs/esm';

import { IKmDetails, NewKmDetails } from './km-details.model';

export const sampleWithRequiredData: IKmDetails = {
  id: 9138,
};

export const sampleWithPartialData: IKmDetails = {
  id: 27250,
  sharesMr: 'ew boo',
  sugarShares: 19011.03,
  deposit: 12753.4,
  dueLoan: 9576.64,
  dueDate: dayjs('2024-01-03T23:39'),
  kmDate: dayjs('2024-01-04T02:01'),
  kmFromDate: dayjs('2024-01-04T05:53'),
  bagayatHectorMr: 'evenly',
  bagayatAre: 20854.21,
  jirayatAre: 17439.73,
  landValueMr: 'behind happily',
  eAggrementAmt: 9475.71,
  eAgreementAmt: 'pep why martyr',
  eAgreementDate: dayjs('2024-01-04T05:29'),
  bojaAmount: 22022.75,
  bojaAmountMr: 'versus selfishly',
  bojaDate: dayjs('2024-01-03T15:12'),
};

export const sampleWithFullData: IKmDetails = {
  id: 29076,
  shares: 17975.72,
  sharesMr: 'whenever mid',
  sugarShares: 1534.79,
  sugarSharesMr: 'painfully monthly',
  deposit: 2676.47,
  depositMr: 'whether',
  dueLoan: 30653.66,
  dueLoanMr: 'optimistically',
  dueAmount: 5097.18,
  dueAmountMr: 'yack buttonhole gosh',
  dueDate: dayjs('2024-01-04T00:32'),
  kmDate: dayjs('2024-01-04T09:13'),
  kmFromDate: dayjs('2024-01-04T08:30'),
  kmToDate: dayjs('2024-01-03T23:46'),
  bagayatHector: 15812.54,
  bagayatHectorMr: 'under though rapidly',
  bagayatAre: 26775.47,
  bagayatAreMr: 'unless sturdy round',
  jirayatHector: 1654.59,
  jirayatHectorMr: 'pastel whether',
  jirayatAre: 24762.7,
  jirayatAreMr: 'contrast option',
  landValue: 11209.41,
  landValueMr: 'bah woot alongside',
  eAggrementAmt: 16103.76,
  eAgreementAmt: 'separately',
  eAgreementDate: dayjs('2024-01-03T23:41'),
  bojaAmount: 16936.6,
  bojaAmountMr: 'enthusiastically likable astride',
  bojaDate: dayjs('2024-01-04T11:15'),
};

export const sampleWithNewData: NewKmDetails = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
