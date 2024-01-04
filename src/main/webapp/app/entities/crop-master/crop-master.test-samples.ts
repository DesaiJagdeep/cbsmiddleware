import dayjs from 'dayjs/esm';

import { ICropMaster, NewCropMaster } from './crop-master.model';

export const sampleWithRequiredData: ICropMaster = {
  id: 21073,
};

export const sampleWithPartialData: ICropMaster = {
  id: 10828,
  cropCode: 'institutionalize while',
  cropName: 'aha',
  categoryCode: 'rankle hm',
  categoryName: 'boo pamphlet iris',
  vatapFromDay: dayjs('2023-06-20T19:06'),
  vatapToMonth: dayjs('2023-06-21T04:11'),
  lastToDay: dayjs('2023-06-21T09:25'),
  dueMonth: dayjs('2023-06-20T17:41'),
  cropPeriod: 'like drug persist',
  sanctionAmt: 30470.11,
};

export const sampleWithFullData: ICropMaster = {
  id: 25054,
  cropCode: 'blah',
  cropName: 'glossy',
  categoryCode: 'despite mmm',
  categoryName: 'ugly ew',
  vatapFromDay: dayjs('2023-06-20T16:19'),
  vatapToMonth: dayjs('2023-06-20T20:14'),
  lastToDay: dayjs('2023-06-20T19:33'),
  lastToMonth: dayjs('2023-06-21T04:15'),
  dueDay: dayjs('2023-06-21T11:51'),
  dueMonth: dayjs('2023-06-20T14:31'),
  cropPeriod: 'sprain scrutinise',
  sanctionAmt: 23945.43,
  previousAmt: 9316.88,
};

export const sampleWithNewData: NewCropMaster = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
