import dayjs from 'dayjs/esm';

import { IIssPortalFile, NewIssPortalFile } from './iss-portal-file.model';

export const sampleWithRequiredData: IIssPortalFile = {
  id: 38270,
};

export const sampleWithPartialData: IIssPortalFile = {
  id: 2227,
  fileName: 'Computers',
  fileExtension: 'Rustic',
  toDisbursementDate: dayjs('2023-07-11'),
  status: 'North Handmade utilisation',
};

export const sampleWithFullData: IIssPortalFile = {
  id: 55235,
  fileName: 'magenta copying',
  fileExtension: 'FTP Hampshire',
  branchCode: 62782,
  financialYear: 'mindshare sticky deposit',
  fromDisbursementDate: dayjs('2023-07-10'),
  toDisbursementDate: dayjs('2023-07-11'),
  pacsCode: 45371,
  status: 'program',
  applicationCount: 'software',
  notes: 'District',
};

export const sampleWithNewData: NewIssPortalFile = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
