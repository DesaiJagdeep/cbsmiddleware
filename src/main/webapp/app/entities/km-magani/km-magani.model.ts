import dayjs from 'dayjs/esm';

export interface IKmMagani {
  id: number;
  kmNumber?: string | null;
  memberNumber?: string | null;
  memberName?: string | null;
  pacsNumber?: string | null;
  share?: number | null;
  financialYear?: string | null;
  kmDate?: dayjs.Dayjs | null;
  maganiDate?: dayjs.Dayjs | null;
}

export type NewKmMagani = Omit<IKmMagani, 'id'> & { id: null };
