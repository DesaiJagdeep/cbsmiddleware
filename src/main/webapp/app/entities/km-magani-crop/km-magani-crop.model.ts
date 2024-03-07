import dayjs from 'dayjs/esm';
import { IKmMagani } from 'app/entities/km-magani/km-magani.model';

export interface IKmMaganiCrop {
  id: number;
  cropName?: string | null;
  cropBalance?: number | null;
  cropDue?: number | null;
  cropDueDate?: dayjs.Dayjs | null;
  cropVasuliPatraDate?: dayjs.Dayjs | null;
  kmManjuri?: number | null;
  kmArea?: number | null;
  eKararNumber?: string | null;
  eKararAmount?: number | null;
  eKararArea?: number | null;
  maganiArea?: number | null;
  maganiAmount?: number | null;
  maganiShare?: number | null;
  pacsNumber?: string | null;
  kmMagani?: Pick<IKmMagani, 'id'> | null;
}

export type NewKmMaganiCrop = Omit<IKmMaganiCrop, 'id'> & { id: null };
