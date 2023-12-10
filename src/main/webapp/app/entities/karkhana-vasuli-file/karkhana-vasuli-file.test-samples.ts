import dayjs from 'dayjs/esm';

import { IKarkhanaVasuliFile, NewKarkhanaVasuliFile } from './karkhana-vasuli-file.model';

export const sampleWithRequiredData: IKarkhanaVasuliFile = {
  id: 23458,
};

export const sampleWithPartialData: IKarkhanaVasuliFile = {
  id: 4145,
  address: 'tolerate',
  addressMr: 'ha dart inside',
  hangamMr: 'toss so',
  factoryName: 'interlace which wording',
  totalAmountMr: 'impoverish',
  toDate: dayjs('2023-12-10T12:02'),
};

export const sampleWithFullData: IKarkhanaVasuliFile = {
  id: 18975,
  fileName: 'manservant',
  uniqueFileName: 'clothe',
  address: 'unless',
  addressMr: 'fast',
  hangam: 'pfft yuck healthy',
  hangamMr: 'woeful neon because',
  factoryName: 'ha instead',
  factoryNameMr: 'incommode',
  totalAmount: 9548.69,
  totalAmountMr: 'obediently um frightfully',
  fromDate: dayjs('2023-12-09T23:12'),
  toDate: dayjs('2023-12-09T20:18'),
};

export const sampleWithNewData: NewKarkhanaVasuliFile = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
