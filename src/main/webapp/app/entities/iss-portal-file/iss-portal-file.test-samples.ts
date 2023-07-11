import dayjs from 'dayjs/esm';

import { IIssPortalFile, NewIssPortalFile } from './iss-portal-file.model';

export const sampleWithRequiredData: IIssPortalFile = {
  id: 38270,
};

export const sampleWithPartialData: IIssPortalFile = {
  id: 26337,
  batchId: 4774,
  fileName: 'Rustic',
  fromDisbursementDate: dayjs('2023-07-11'),
  pacsCode: 88901,
};

export const sampleWithFullData: IIssPortalFile = {
  id: 57232,
  batchId: 67768,
  fileName: 'Handmade',
  fileExtension: 'Analyst',
  branchCode: 46857,
  financialYear: 'pixel',
  fromDisbursementDate: dayjs('2023-07-10'),
  toDisbursementDate: dayjs('2023-07-10'),
  pacsCode: 59576,
  status: 'line Wisconsin navigating',
  batchAckId: 'sticky deposit hack',
  batchDetails: 'program',
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
