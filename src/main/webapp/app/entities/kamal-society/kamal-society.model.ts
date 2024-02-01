import dayjs from 'dayjs/esm';

export interface IKamalSociety {
  id: number;
  financialYear?: string | null;
  kmDate?: dayjs.Dayjs | null;
}

export type NewKamalSociety = Omit<IKamalSociety, 'id'> & { id: null };
