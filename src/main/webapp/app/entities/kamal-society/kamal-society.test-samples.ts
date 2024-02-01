import dayjs from 'dayjs/esm';

import { IKamalSociety, NewKamalSociety } from './kamal-society.model';

export const sampleWithRequiredData: IKamalSociety = {
  id: 26087,
  financialYear: 'invoice Agent',
};

export const sampleWithPartialData: IKamalSociety = {
  id: 32890,
  financialYear: 'Gorgeous',
  kmDate: dayjs('2024-01-31T23:00'),
};

export const sampleWithFullData: IKamalSociety = {
  id: 95411,
  financialYear: 'Harbors strategic',
  kmDate: dayjs('2024-02-01T09:03'),
};

export const sampleWithNewData: NewKamalSociety = {
  financialYear: 'Account Loaf intuitive',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
