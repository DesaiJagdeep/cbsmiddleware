import dayjs from 'dayjs/esm';

import { ICourtCaseDetails, NewCourtCaseDetails } from './court-case-details.model';

export const sampleWithRequiredData: ICourtCaseDetails = {
  id: 35200,
};

export const sampleWithPartialData: ICourtCaseDetails = {
  id: 88101,
  kramank: 41284,
  dinank: dayjs('2023-08-28T16:22'),
  caseDinank: dayjs('2023-08-28T13:33'),
  sabhasadAccNo: 'generation Alabama',
  karjPrakarType: 'bluetooth Health',
  certificateRakkam: 23101,
  vyaj: 15367,
  dimmandDinnank: dayjs('2023-08-29T02:01'),
  japtiAadheshDinnank: dayjs('2023-08-28T20:14'),
  sthavr: 47344,
  jangam: 25136,
  vikriAddheshDinnank: dayjs('2023-08-28T17:55'),
};

export const sampleWithFullData: ICourtCaseDetails = {
  id: 54705,
  kramank: 33991,
  dinank: dayjs('2023-08-28T11:46'),
  caseDinank: dayjs('2023-08-29T02:52'),
  sabhasad: 'Djibouti panel',
  sabhasadAccNo: 'HDD',
  karjPrakarType: 'green',
  karjPrakar: 'Bermuda Cross-group',
  certificateMilale: 'indexing recontextualize',
  certificateDinnank: dayjs('2023-08-28T15:09'),
  certificateRakkam: 99207,
  yenebaki: 30618,
  vyaj: 60524,
  etar: 55450,
  dimmandMilale: 'even-keeled Director',
  dimmandDinnank: dayjs('2023-08-28T16:43'),
  japtiAadhesh: 'client-driven Crossing',
  japtiAadheshDinnank: dayjs('2023-08-28T19:08'),
  sthavr: 18734,
  jangam: 6065,
  vikriAadhesh: 'Concrete',
  vikriAddheshDinnank: dayjs('2023-08-29T05:54'),
  etarTapshil: 'Synergized',
};

export const sampleWithNewData: NewCourtCaseDetails = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
