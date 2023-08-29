import dayjs from 'dayjs/esm';

export interface ICourtCaseDetails {
  id: number;
  kramank?: number | null;
  dinank?: dayjs.Dayjs | null;
  caseDinank?: dayjs.Dayjs | null;
  sabhasad?: string | null;
  sabhasadAccNo?: string | null;
  karjPrakarType?: string | null;
  karjPrakar?: string | null;
  certificateMilale?: string | null;
  certificateDinnank?: dayjs.Dayjs | null;
  certificateRakkam?: number | null;
  yenebaki?: number | null;
  vyaj?: number | null;
  etar?: number | null;
  dimmandMilale?: string | null;
  dimmandDinnank?: dayjs.Dayjs | null;
  japtiAadhesh?: string | null;
  japtiAadheshDinnank?: dayjs.Dayjs | null;
  sthavr?: number | null;
  jangam?: number | null;
  vikriAadhesh?: string | null;
  vikriAddheshDinnank?: dayjs.Dayjs | null;
  etarTapshil?: string | null;
}

export type NewCourtCaseDetails = Omit<ICourtCaseDetails, 'id'> & { id: null };
