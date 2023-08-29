import dayjs from 'dayjs/esm';

import { ICourtCaseSetting, NewCourtCaseSetting } from './court-case-setting.model';

export const sampleWithRequiredData: ICourtCaseSetting = {
  id: 1278,
};

export const sampleWithPartialData: ICourtCaseSetting = {
  id: 47530,
  dinank: dayjs('2023-08-29T03:31'),
  vasuliAdhikari: 'Cheese Managed Carolina',
  tharavDinank: dayjs('2023-08-28T21:47'),
  oneZeroOneNoticeTwo: dayjs('2023-08-28T16:26'),
  vishayKramank: 'Ameliorated Soap',
  maganiNotice: dayjs('2023-08-29T02:31'),
  etarKharch: 76810,
  noticeKharch: 29338,
  vasuliKharch: 45287,
};

export const sampleWithFullData: ICourtCaseSetting = {
  id: 46648,
  dinank: dayjs('2023-08-29T05:17'),
  shakhaVevsthapak: 'Sheqel Orchestrator',
  suchak: 'Garden Chair Loan',
  aanumodak: 'feed',
  vasuliAdhikari: 'haptic Forest Berkshire',
  arOffice: 'Litas embrace',
  tharavNumber: 71362,
  tharavDinank: dayjs('2023-08-28T15:24'),
  karjFedNotice: dayjs('2023-08-29T01:33'),
  oneZeroOneNoticeOne: dayjs('2023-08-28T21:49'),
  oneZeroOneNoticeTwo: dayjs('2023-08-28T15:27'),
  vishayKramank: 'Small',
  war: 'alarm Gorgeous ADP',
  vel: 'Cotton clear-thinking',
  maganiNotice: dayjs('2023-08-28T21:17'),
  etarKharch: 53130,
  noticeKharch: 20589,
  vasuliKharch: 72680,
};

export const sampleWithNewData: NewCourtCaseSetting = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
