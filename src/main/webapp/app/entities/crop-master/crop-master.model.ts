import dayjs from 'dayjs/esm';

export interface ICropMaster {
  id: number;
  cropCode?: string | null;
  cropName?: string | null;
  categoryCode?: string | null;
  categoryName?: string | null;
  vatapFromDay?: dayjs.Dayjs | null;
  vatapToMonth?: dayjs.Dayjs | null;
  lastToDay?: dayjs.Dayjs | null;
  lastToMonth?: dayjs.Dayjs | null;
  dueDay?: dayjs.Dayjs | null;
  dueMonth?: dayjs.Dayjs | null;
  cropPeriod?: string | null;
  sanctionAmt?: number | null;
  previousAmt?: number | null;
}

export type NewCropMaster = Omit<ICropMaster, 'id'> & { id: null };
