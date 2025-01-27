import dayjs from 'dayjs/esm';

export interface IIssPortalFile {
  id: number;
  fileName?: string | null;
  fileExtension?: string | null;
  branchCode?: number | null;
  financialYear?: string | null;
  fromDisbursementDate?: dayjs.Dayjs | null;
  toDisbursementDate?: dayjs.Dayjs | null;
  pacsCode?: number | null;
  status?: string | null;
  applicationCount?: string | null;
  notes?: string | null;
}

export type NewIssPortalFile = Omit<IIssPortalFile, 'id'> & { id: null };
