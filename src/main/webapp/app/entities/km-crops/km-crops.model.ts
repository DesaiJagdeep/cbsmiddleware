import { ICropMaster } from 'app/entities/crop-master/crop-master.model';
import { IKmDetails } from 'app/entities/km-details/km-details.model';

export interface IKmCrops {
  id: number;
  hector?: number | null;
  hectorMr?: string | null;
  are?: number | null;
  areMr?: string | null;
  noOfTree?: number | null;
  noOfTreeMr?: string | null;
  demand?: number | null;
  demandMr?: string | null;
  society?: string | null;
  societyMr?: string | null;
  bankAmt?: number | null;
  bankAmtMr?: string | null;
  vibhagiAdhikari?: string | null;
  vibhagiAdhikariMr?: string | null;
  branch?: string | null;
  branchMr?: string | null;
  inspAmt?: number | null;
  inspAmtMr?: string | null;
  cropMaster?: Pick<ICropMaster, 'id'> | null;
  kmDetails?: Pick<IKmDetails, 'id'> | null;
}

export type NewKmCrops = Omit<IKmCrops, 'id'> & { id: null };
