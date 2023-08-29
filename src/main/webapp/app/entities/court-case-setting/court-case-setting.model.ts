import dayjs from 'dayjs/esm';

export interface ICourtCaseSetting {
  id: number;
  dinank?: dayjs.Dayjs | null;
  shakhaVevsthapak?: string | null;
  suchak?: string | null;
  aanumodak?: string | null;
  vasuliAdhikari?: string | null;
  arOffice?: string | null;
  tharavNumber?: number | null;
  tharavDinank?: dayjs.Dayjs | null;
  karjFedNotice?: dayjs.Dayjs | null;
  oneZeroOneNoticeOne?: dayjs.Dayjs | null;
  oneZeroOneNoticeTwo?: dayjs.Dayjs | null;
  vishayKramank?: string | null;
  war?: string | null;
  vel?: string | null;
  maganiNotice?: dayjs.Dayjs | null;
  etarKharch?: number | null;
  noticeKharch?: number | null;
  vasuliKharch?: number | null;
}

export type NewCourtCaseSetting = Omit<ICourtCaseSetting, 'id'> & { id: null };
