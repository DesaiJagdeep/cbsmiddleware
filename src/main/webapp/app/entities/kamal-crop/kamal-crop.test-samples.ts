import dayjs from 'dayjs/esm';

import { IKamalCrop, NewKamalCrop } from './kamal-crop.model';

export const sampleWithRequiredData: IKamalCrop = {
  id: 94816,
};

export const sampleWithPartialData: IKamalCrop = {
  id: 29664,
  pacsNumber: 'Plastic Lead directional',
  financialYear: 'Fantastic SQL',
  memberCount: 'tangible intuitive',
  area: 'Licensed transmit payment',
  headOfficeAmount: 'York',
  kmDateMr: 'project Industrial redundant',
};

export const sampleWithFullData: IKamalCrop = {
  id: 25119,
  pacsNumber: 'Franc Savings Associate',
  financialYear: 'Centralized De-engineered',
  memberCount: 'Integrated',
  area: 'Reduced Kip SMTP',
  pacsAmount: 'Plastic AI SQL',
  branchAmount: 'grey',
  headOfficeAmount: 'transmit CSS',
  divisionalOfficeAmount: 'bypass Senior Wooden',
  cropEligibilityAmount: 'Operations Equatorial',
  kmDate: dayjs('2024-02-01T11:01'),
  kmDateMr: 'system',
};

export const sampleWithNewData: NewKamalCrop = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
