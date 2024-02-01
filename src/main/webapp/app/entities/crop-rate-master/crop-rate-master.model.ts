import { ICropMaster } from 'app/entities/crop-master/crop-master.model';

export interface ICropRateMaster {
  id: number;
  financialYear?: string | null;
  currentAmount?: number | null;
  currentAmountMr?: string | null;
  percentage?: number | null;
  activeFlag?: boolean | null;
  cropMaster?: Pick<ICropMaster, 'id'> | null;
}

export type NewCropRateMaster = Omit<ICropRateMaster, 'id'> & { id: null };
