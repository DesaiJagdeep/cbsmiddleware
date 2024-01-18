import dayjs from 'dayjs/esm';

import { IKarkhanaVasuliFile, NewKarkhanaVasuliFile } from './karkhana-vasuli-file.model';

export const sampleWithRequiredData: IKarkhanaVasuliFile = {
  id: 83489,
};

export const sampleWithPartialData: IKarkhanaVasuliFile = {
  id: 76034,
  uniqueFileName: 'AGP payment Buckinghamshire',
  hangamMr: 'Arkansas directional compressing',
  factoryNameMr: 'Coordinator',
  totalAmount: 50447,
  totalAmountMr: 'Outdoors Illinois Rustic',
  fromDate: dayjs('2023-12-09T21:14'),
  toDate: dayjs('2023-12-09T22:30'),
  branchCode: 93326,
  pacsName: 'Bedfordshire',
};

export const sampleWithFullData: IKarkhanaVasuliFile = {
  id: 86026,
  fileName: 'programming',
  uniqueFileName: 'New',
  address: 'invoice',
  addressMr: 'Arab',
  hangam: 'bypass York Chips',
  hangamMr: 'intermediate',
  factoryName: 'Handcrafted target',
  factoryNameMr: 'compelling hour deposit',
  totalAmount: 33655,
  totalAmountMr: 'Sausages navigating',
  fromDate: dayjs('2023-12-09T22:06'),
  toDate: dayjs('2023-12-09T15:27'),
  branchCode: 42028,
  pacsName: 'Convertible Wooden',
};

export const sampleWithNewData: NewKarkhanaVasuliFile = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
