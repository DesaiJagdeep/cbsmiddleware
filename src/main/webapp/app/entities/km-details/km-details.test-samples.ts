import dayjs from 'dayjs/esm';

import { IKmDetails, NewKmDetails } from './km-details.model';

export const sampleWithRequiredData: IKmDetails = {
  id: 3427,
};

export const sampleWithPartialData: IKmDetails = {
  id: 20938,
  sugarShares: 13506.23,
  sugarSharesMr: 'fillet',
  dueLoan: 15353.68,
  dueLoanMr: 'cuddly hm',
  dueAmountMr: 'blackbird',
  kmDate: dayjs('2024-01-08T07:23'),
  kmDateMr: 'driving',
  kmFromDateMr: 'gratefully monger ah',
  kmToDateMr: 'unfold',
  bagayatAre: 23419.6,
  bagayatAreMr: 'tournament how',
  jirayatHector: 30504.01,
  jirayatHectorMr: 'ew aw what',
  jirayatAre: 12865.11,
  jirayatAreMr: 'deceivingly',
  surveyNo: 6276,
  landValue: 23629.73,
  landValueMr: 'a well-informed tight',
  eAgreementAmt: 5172.12,
  eAgreementAmtMr: 'near except',
  bojaAmount: 28977.02,
  bojaDateMr: 'calmly a jaw',
};

export const sampleWithFullData: IKmDetails = {
  id: 32487,
  shares: 15639.56,
  sharesMr: 'straw patiently label',
  sugarShares: 10653.85,
  sugarSharesMr: 'zowie ew uh-huh',
  deposite: 921.15,
  depositeMr: 'tremendously dispatch',
  dueLoan: 31482.2,
  dueLoanMr: 'badly',
  dueAmount: 31496.78,
  dueAmountMr: 'overfeed into',
  dueDateMr: 'by ouch zap',
  dueDate: dayjs('2024-01-08T01:03'),
  kmDate: dayjs('2024-01-07T19:26'),
  kmDateMr: 'uh-huh',
  kmFromDate: dayjs('2024-01-08T01:03'),
  kmFromDateMr: 'familiarise',
  kmToDate: dayjs('2024-01-08T01:08'),
  kmToDateMr: 'kip kookily nearly',
  bagayatHector: 6591.03,
  bagayatHectorMr: 'yippee',
  bagayatAre: 17176.1,
  bagayatAreMr: 'woot',
  jirayatHector: 28609.02,
  jirayatHectorMr: 'incidentally',
  jirayatAre: 5682.5,
  jirayatAreMr: 'yieldingly reunify oof',
  zindagiAmt: 24593.05,
  zindagiNo: 18807,
  surveyNo: 10032,
  landValue: 32403.24,
  landValueMr: 'apropos',
  eAgreementAmt: 746.84,
  eAgreementAmtMr: 'with but',
  eAgreementDate: dayjs('2024-01-08T00:46'),
  eAgreementDateMr: 'during',
  bojaAmount: 15339.2,
  bojaAmountMr: 'er',
  bojaDate: dayjs('2024-01-08T02:15'),
  bojaDateMr: 'finally aw piano',
};

export const sampleWithNewData: NewKmDetails = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
