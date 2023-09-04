import dayjs from 'dayjs/esm';

export interface ICourtCaseSetting {
  id: number;
  vasuliAdhikariName?: string | null;
  arOfficeName?: string | null;
  chairmanName?: string | null;
  sachivName?: string | null;
  suchakName?: string | null;
  anumodakName?: string | null;
  vasuliExpense?: number | null;
  otherExpense?: number | null;
  noticeExpense?: number | null;
  meetingNo?: number | null;
  meetingDate?: dayjs.Dayjs | null;
  subjectNo?: number | null;
  meetingDay?: string | null;
  meetingTime?: string | null;
}

export type NewCourtCaseSetting = Omit<ICourtCaseSetting, 'id'> & { id: null };
