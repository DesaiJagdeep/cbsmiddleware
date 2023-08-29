import dayjs from 'dayjs/esm';

import { ICourtCase, NewCourtCase } from './court-case.model';

export const sampleWithRequiredData: ICourtCase = {
  id: 74214,
};

export const sampleWithPartialData: ICourtCase = {
  id: 13157,
  code: 25905,
  talukaName: 'Total programming Cheese',
  karjPrakar: 'Facilitator Concrete',
  arOffice: 'Ridges',
  ekunVyaj: 69043,
  karjDinnank: dayjs('2023-08-28T23:20'),
  shakhaVevsthapak: 'Baby',
  suchak: 'Bacon Executive program',
  anumodak: 'Ball Borders',
  dava: 75205,
  sarcharge: 37655,
  jyadaVyaj: 71311,
  yeneVyaj: 40,
  etharKharch: 51761,
  vima: 37903,
  notice: 35581,
  tharavNumber: 59486,
  vishayKramank: 35699,
  noticeOne: dayjs('2023-08-28T14:23'),
  vel: 'Car',
  jamindarOne: 'pink SCSI Market',
  jamindarTwoAddress: 'redundant Consultant Wooden',
  taranType: 'Hat microchip',
};

export const sampleWithFullData: ICourtCase = {
  id: 62204,
  code: 35640,
  caseDinank: dayjs('2023-08-29T02:16'),
  bankName: 'Central',
  talukaName: 'national mobile silver',
  talukaCode: 8852,
  sabasadSavingAccNo: 'Isle',
  sabasadName: 'access Trace',
  sabasadAddress: 'heuristic microchip Applications',
  karjPrakar: 'Avon Paradigm Run',
  vasuliAdhikari: 'Pakistan',
  ekunJama: 59600,
  baki: 64663,
  arOffice: 'digital Dinar',
  ekunVyaj: 97067,
  jamaVyaj: 66631,
  dandVyaj: 34954,
  karjRakkam: 57002,
  thakDinnank: dayjs('2023-08-29T08:08'),
  karjDinnank: dayjs('2023-08-28T12:24'),
  mudatSampteDinank: dayjs('2023-08-29T07:54'),
  mudat: 'backing',
  vyaj: 'IB Consultant Salad',
  haptaRakkam: 18008,
  shakhaVevsthapak: 'channels',
  suchak: 'program ADP vertical',
  anumodak: 'support',
  dava: 87093,
  vyajDar: 13295,
  sarcharge: 72992,
  jyadaVyaj: 20922,
  yeneVyaj: 23597,
  vasuliKharch: 49644,
  etharKharch: 90770,
  vima: 59498,
  notice: 97738,
  tharavNumber: 77477,
  tharavDinank: dayjs('2023-08-28T11:07'),
  vishayKramank: 7066,
  noticeOne: dayjs('2023-08-28T17:52'),
  noticeTwo: dayjs('2023-08-28T18:48'),
  war: 'Associate web-readiness Dollar',
  vel: 'Gabon Idaho',
  jamindarOne: 'Designer Account',
  jamindarOneAddress: 'SMTP Producer payment',
  jamindarTwo: 'Louisiana Gorgeous input',
  jamindarTwoAddress: 'hack',
  taranType: 'EXE',
  taranDetails: 'turquoise HTTP white',
};

export const sampleWithNewData: NewCourtCase = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
