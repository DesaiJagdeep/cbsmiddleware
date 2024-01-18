import { IKarkhanaVasuliRecords, NewKarkhanaVasuliRecords } from './karkhana-vasuli-records.model';

export const sampleWithRequiredData: IKarkhanaVasuliRecords = {
  id: 5233,
};

export const sampleWithPartialData: IKarkhanaVasuliRecords = {
  id: 40164,
  memberName: 'Car',
  memberNameMr: 'Berkshire',
  villageName: 'collaborative',
  amountMr: 'framework Assistant',
  status: true,
};

export const sampleWithFullData: IKarkhanaVasuliRecords = {
  id: 505,
  factoryMemberCode: 33440,
  karkhanaMemberCodeMr: 'Ergonomic overriding Madagascar',
  memberName: 'Corners',
  memberNameMr: 'Ports',
  villageName: 'optimal lime infomediaries',
  villageNameMr: 'extensible Chips',
  amount: 45627,
  amountMr: 'indexing',
  savingAccNo: 37403,
  savingAccNoMr: 'Tasty Toys',
  status: true,
};

export const sampleWithNewData: NewKarkhanaVasuliRecords = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
