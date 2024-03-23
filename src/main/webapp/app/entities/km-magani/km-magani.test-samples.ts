import dayjs from 'dayjs/esm';

import { IKmMagani, NewKmMagani } from './km-magani.model';

export const sampleWithRequiredData: IKmMagani = {
  id: 5508,
};

export const sampleWithPartialData: IKmMagani = {
  id: 9895,
  financialYear: 'utilize global',
  kmDate: dayjs('2024-03-07T03:03'),
  maganiDate: dayjs('2024-03-06T23:36'),
};

export const sampleWithFullData: IKmMagani = {
  id: 53780,
  kmNumber: 'Markets Fresh',
  memberNumber: 'Savings invoice orchid',
  memberName: 'Account Sleek',
  pacsNumber: 'payment',
  share: 59202,
  financialYear: 'Hat Dollar',
  kmDate: dayjs('2024-03-06T11:22'),
  maganiDate: dayjs('2024-03-07T10:08'),
};

export const sampleWithNewData: NewKmMagani = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
