import { IFarmerTypeMaster } from 'app/entities/farmer-type-master/farmer-type-master.model';

export interface IKmMaster {
  id: number;
  branchCode?: string | null;
  branchCodeMr?: string | null;
  farmerName?: string | null;
  farmerNameMr?: string | null;
  farmerAddress?: string | null;
  farmerAddressMr?: string | null;
  gender?: string | null;
  genderMr?: string | null;
  caste?: string | null;
  casteMr?: string | null;
  pacsNumber?: string | null;
  areaHector?: number | null;
  areaHectorMr?: string | null;
  aadharNo?: string | null;
  aadharNoMr?: string | null;
  panNo?: string | null;
  panNoMr?: string | null;
  mobileNo?: string | null;
  mobileNoMr?: string | null;
  kccNo?: string | null;
  kccNoMr?: string | null;
  savingAcNo?: number | null;
  savingAcNoMr?: string | null;
  pacsMemberCode?: string | null;
  pacsMemberCodeMr?: string | null;
  entryFlag?: string | null;
  farmerTypeMaster?: Pick<IFarmerTypeMaster, 'id'> | null;
}

export type NewKmMaster = Omit<IKmMaster, 'id'> & { id: null };
