import { IKmMagani } from 'app/entities/km-magani/km-magani.model';

export interface IKmMaganiCrop {
  id: number;
  cropName?: string | null;
  kmMagani?: Pick<IKmMagani, 'id'> | null;
}

export type NewKmMaganiCrop = Omit<IKmMaganiCrop, 'id'> & { id: null };
