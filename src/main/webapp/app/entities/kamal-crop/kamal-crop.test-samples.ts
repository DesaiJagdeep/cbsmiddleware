import { IKamalCrop, NewKamalCrop } from './kamal-crop.model';

export const sampleWithRequiredData: IKamalCrop = {
  id: 94816,
  pacsNumber: 52774,
  memNo: 88554,
  memHector: 53403,
};

export const sampleWithPartialData: IKamalCrop = {
  id: 48033,
  pacsNumber: 1567,
  memNo: 47935,
  memHector: 87808,
  memNoMr: 'HTTP',
  memAarMr: 'groupware Tasty',
};

export const sampleWithFullData: IKamalCrop = {
  id: 54637,
  pacsNumber: 9556,
  memNo: 36659,
  memHector: 56647,
  memNoMr: 'user tangible intuitive',
  memHectorMr: 'Licensed transmit payment',
  memAar: 23190,
  memAarMr: 'Account project',
};

export const sampleWithNewData: NewKamalCrop = {
  pacsNumber: 2390,
  memNo: 97597,
  memHector: 99108,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
