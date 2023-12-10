import dayjs from 'dayjs/esm';

export interface IKarkhanaVasuliFile {
  id: number;
  fileName?: string | null;
  uniqueFileName?: string | null;
  address?: string | null;
  addressMr?: string | null;
  hangam?: string | null;
  hangamMr?: string | null;
  factoryName?: string | null;
  factoryNameMr?: string | null;
  totalAmount?: number | null;
  totalAmountMr?: string | null;
  fromDate?: dayjs.Dayjs | null;
  toDate?: dayjs.Dayjs | null;
}

export type NewKarkhanaVasuliFile = Omit<IKarkhanaVasuliFile, 'id'> & { id: null };
