import dayjs from 'dayjs/esm';

import { IKamalMaryadaPatrak, NewKamalMaryadaPatrak } from './kamal-maryada-patrak.model';

export const sampleWithRequiredData: IKamalMaryadaPatrak = {
  id: 52141,
};

export const sampleWithPartialData: IKamalMaryadaPatrak = {
  id: 49992,
  cropLoan: 'Somoni drive application',
  kmSummary: 'blue Cotton virtual',
  toKMDate: dayjs('2023-09-27'),
  cropTypeNumber: 'optical Health',
  cropType: 'Credit bypassing Manor',
  fromHector: 'Licensed Extension',
  toHector: 'Port generation architectures',
};

export const sampleWithFullData: IKamalMaryadaPatrak = {
  id: 34833,
  cropLoan: 'bypass',
  kmChart: 'sensor',
  demand: 'mesh',
  kmSummary: 'Steel Integration Salad',
  kmDate: dayjs('2023-09-28'),
  toKMDate: dayjs('2023-09-27'),
  coverPage: false,
  cropTypeNumber: 'eco-centric Generic Gorgeous',
  cropType: 'application Rustic teal',
  fromHector: 'Tunisia redefine',
  toHector: 'RSS',
  defaulterName: 'Intelligent Quality-focused',
  suchakName: 'Pennsylvania deposit array',
  anumodakName: 'Bike Incredible Ball',
  meetingDate: dayjs('2023-09-27'),
  resolutionNumber: 'strategize Optimization',
};

export const sampleWithNewData: NewKamalMaryadaPatrak = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
