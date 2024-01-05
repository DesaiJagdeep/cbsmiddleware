import { IKmDetails } from 'app/entities/km-details/km-details.model';

export interface IKmCrops {
  id: number;
  cropName?: string | null;
  cropNameMr?: string | null;
  hector?: number | null;
  hectorMr?: string | null;
  are?: number | null;
  areMr?: string | null;
  prviousAmt?: number | null;
  previousAmtMr?: string | null;
  demand?: number | null;
  demandMr?: string | null;
  society?: string | null;
  societyMr?: string | null;
  bankAmt?: number | null;
  bankAmtMr?: string | null;
  noOfTree?: number | null;
  noOfTreeMr?: string | null;
  kmDetails?: Pick<IKmDetails, 'id'> | null;
}

export type NewKmCrops = Omit<IKmCrops, 'id'> & { id: null };
