import { IFarmerTypeMaster } from 'app/entities/farmer-type-master/farmer-type-master.model';

export interface IKmMaster {
  id: number;
  branchCode?: string | null;
  farmerName?: string | null;
  farmerNameMr?: string | null;
  farmerAddress?: string | null;
  farmerAddressMr?: string | null;
  gender?: string | null;
  cast?: string | null;
  castMr?: string | null;
  pacsNumber?: string | null;
  areaHector?: string | null;
  aadharNo?: number | null;
  aadharNoMr?: string | null;
  panNo?: number | null;
  panNoMr?: string | null;
  mobileNo?: string | null;
  mobileNoMr?: string | null;
  savingNo?: number | null;
  savingNoMr?: string | null;
  pacsMemberCode?: string | null;
  entryFlag?: string | null;
  farmerTypeMaster?: IFarmerTypeMaster | null;
}

export type NewKmMaster = Omit<IKmMaster, 'id'> & { id: null };
