import dayjs from 'dayjs/esm';

import { IKarkhanaVasuliFile, NewKarkhanaVasuliFile } from './karkhana-vasuli-file.model';

export const sampleWithRequiredData: IKarkhanaVasuliFile = {
  id: 12277,
};

export const sampleWithPartialData: IKarkhanaVasuliFile = {
  id: 22863,
  uniqueFileName: 'chafe',
  address: 'duh ethical',
  addressMr: 'tepid book',
  factoryName: 'like who',
  factoryNameMr: 'forenenst',
  totalAmount: 8869.27,
  totalAmountMr: 'unethically inspector',
  fromDate: dayjs('2023-12-10T12:41'),
};

export const sampleWithFullData: IKarkhanaVasuliFile = {
  id: 24075,
  fileName: 'soulful frantically',
  uniqueFileName: 'vault rudely boo',
  address: 'charset supposing',
  addressMr: 'current excepting',
  hangam: 'mantua',
  hangamMr: 'graphic',
  factoryName: 'con as spell',
  factoryNameMr: 'likewise unfortunately quarrelsomely',
  totalAmount: 4589.35,
  totalAmountMr: 'zowie daintily',
  fromDate: dayjs('2023-12-09T14:44'),
  toDate: dayjs('2023-12-10T07:54'),
  branchCode: 28222,
  pacsName: 'apud',
};

export const sampleWithNewData: NewKarkhanaVasuliFile = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
