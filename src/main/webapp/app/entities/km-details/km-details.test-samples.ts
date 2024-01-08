import dayjs from 'dayjs/esm';

import { IKmDetails, NewKmDetails } from './km-details.model';

export const sampleWithRequiredData: IKmDetails = {
  id: 79424,
};

export const sampleWithPartialData: IKmDetails = {
  id: 37960,
  sugarShares: 72391,
  sugarSharesMr: 'plug-and-play Fantastic',
  dueLoan: 94466,
  dueLoanMr: 'maroon firmware AGP',
  dueDate: dayjs('2024-01-08T02:56'),
  kmFromDate: dayjs('2024-01-08T17:12'),
  kmToDate: dayjs('2024-01-08T01:16'),
  bagayatHector: 38940,
  bagayatHectorMr: 'capacitor Austria Plastic',
  jirayatAreMr: 'Architect',
  zindagiAmt: 30773,
  surveyNo: 78048,
  landValue: 94957,
  bojaAmountMr: 'Cayman',
  bojaDate: dayjs('2024-01-07T19:59'),
};

export const sampleWithFullData: IKmDetails = {
  id: 57483,
  shares: 16309,
  sharesMr: 'SQL',
  sugarShares: 21847,
  sugarSharesMr: 'compelling Vermont',
  deposite: 8593,
  depositeMr: 'alliance',
  dueLoan: 18088,
  dueLoanMr: 'drive Tunisia',
  dueAmount: 53126,
  dueAmountMr: 'Metrics',
  dueDateMr: 'lime',
  dueDate: dayjs('2024-01-08T09:41'),
  kmDate: dayjs('2024-01-07T21:36'),
  kmDateMr: 'Soft Buckinghamshire',
  kmFromDate: dayjs('2024-01-08T05:11'),
  kmFromDateMr: 'Chair Street',
  kmToDate: dayjs('2024-01-08T05:36'),
  kmToDateMr: 'Market Home Gorgeous',
  bagayatHector: 99230,
  bagayatHectorMr: 'Clothing',
  bagayatAre: 83970,
  bagayatAreMr: 'Gorgeous plug-and-play Soft',
  jirayatHector: 32148,
  jirayatHectorMr: 'management cross-platform CSS',
  jirayatAre: 37169,
  jirayatAreMr: 'Granite Representative',
  zindagiAmt: 30474,
  zindagiNo: 62335,
  surveyNo: 39123,
  landValue: 95675,
  landValueMr: 'intranet District',
  eAgreementAmt: 75577,
  eAgreementAmtMr: 'bandwidth',
  eAgreementDate: dayjs('2024-01-08T01:12'),
  eAgreementDateMr: 'North',
  bojaAmount: 87713,
  bojaAmountMr: 'virtual Delaware web',
  bojaDate: dayjs('2024-01-08T15:10'),
  bojaDateMr: 'auxiliary Dollar',
};

export const sampleWithNewData: NewKmDetails = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
