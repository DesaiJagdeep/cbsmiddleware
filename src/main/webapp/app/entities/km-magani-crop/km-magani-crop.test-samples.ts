import dayjs from 'dayjs/esm';

import { IKmMaganiCrop, NewKmMaganiCrop } from './km-magani-crop.model';

export const sampleWithRequiredData: IKmMaganiCrop = {
  id: 43412,
};

export const sampleWithPartialData: IKmMaganiCrop = {
  id: 3299,
  cropName: 'Cliffs',
  cropDue: 72980,
  cropDueDate: dayjs('2024-03-07T03:49'),
  cropVasuliPatraDate: dayjs('2024-03-06T13:05'),
  maganiAmount: 57176,
  maganiShare: 77825,
};

export const sampleWithFullData: IKmMaganiCrop = {
  id: 4127,
  cropName: 'Borders Ergonomic',
  cropBalance: 40846,
  cropDue: 88222,
  cropDueDate: dayjs('2024-03-06T23:34'),
  cropVasuliPatraDate: dayjs('2024-03-07T07:55'),
  kmManjuri: 35216,
  kmArea: 87338,
  eKararNumber: 'web-readiness',
  eKararAmount: 29451,
  eKararArea: 42830,
  maganiArea: 67917,
  maganiAmount: 24376,
  maganiShare: 41365,
  pacsNumber: 'Steel',
};

export const sampleWithNewData: NewKmMaganiCrop = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
