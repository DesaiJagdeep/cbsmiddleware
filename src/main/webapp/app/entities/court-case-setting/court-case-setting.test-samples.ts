import dayjs from 'dayjs/esm';

import { ICourtCaseSetting, NewCourtCaseSetting } from './court-case-setting.model';

export const sampleWithRequiredData: ICourtCaseSetting = {
  id: 1278,
};

export const sampleWithPartialData: ICourtCaseSetting = {
  id: 57368,
  vasuliAdhikariName: 'quantifying Stand-alone haptic',
  suchakName: 'generating Analyst',
  otherExpense: 37583,
  meetingDate: dayjs('2023-08-29'),
  subjectNo: 2292,
};

export const sampleWithFullData: ICourtCaseSetting = {
  id: 52150,
  vasuliAdhikariName: 'Checking',
  arOfficeName: 'heuristic Hat transparent',
  chairmanName: 'Cambridgeshire knowledge',
  sachivName: 'Dram Loan',
  suchakName: 'feed',
  anumodakName: 'haptic Forest Berkshire',
  vasuliExpense: 40970,
  otherExpense: 68300,
  noticeExpense: 48436,
  meetingNo: 73252,
  meetingDate: dayjs('2023-08-29'),
  subjectNo: 13203,
  meetingDay: 'application Buckinghamshire',
  meetingTime: 'Movies',
};

export const sampleWithNewData: NewCourtCaseSetting = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
