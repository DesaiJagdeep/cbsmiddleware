import dayjs from 'dayjs/esm';

export interface IKamalMaryadaPatrak {
  id: number;
  cropLoan?: string | null;
  kmChart?: string | null;
  demand?: string | null;
  kmSummary?: string | null;
  kmDate?: dayjs.Dayjs | null;
  toKMDate?: dayjs.Dayjs | null;
  coverPage?: boolean | null;
  cropTypeNumber?: string | null;
  cropType?: string | null;
  fromHector?: string | null;
  toHector?: string | null;
  defaulterName?: string | null;
  suchakName?: string | null;
  anumodakName?: string | null;
  meetingDate?: dayjs.Dayjs | null;
  resolutionNumber?: string | null;
}

export type NewKamalMaryadaPatrak = Omit<IKamalMaryadaPatrak, 'id'> & { id: null };
