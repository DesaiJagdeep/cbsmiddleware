import { IKarkhanaVasuliRecords, NewKarkhanaVasuliRecords } from './karkhana-vasuli-records.model';

export const sampleWithRequiredData: IKarkhanaVasuliRecords = {
  id: 29207,
};

export const sampleWithPartialData: IKarkhanaVasuliRecords = {
  id: 20391,
  karkhanaMemberCodeMr: 'loyally courteous glacier',
  memberNameMr: 'affront motherly',
  villageName: 'because nit mmm',
  villageNameMr: 'recklessly where',
  amountMr: 'digitalise but travel',
  savingAccNoMr: 'crisp since',
  status: true,
};

export const sampleWithFullData: IKarkhanaVasuliRecords = {
  id: 1211,
  factoryMemberCode: 6181,
  karkhanaMemberCodeMr: 'date solvency unfortunately',
  memberName: 'than save',
  memberNameMr: 'purify furthermore',
  villageName: 'lest harmonize though',
  villageNameMr: 'until who victorious',
  amount: 21650.91,
  amountMr: 'furiously abnormally snitch',
  savingAccNo: 24922,
  savingAccNoMr: 'bowed whoever since',
  status: false,
};

export const sampleWithNewData: NewKarkhanaVasuliRecords = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
