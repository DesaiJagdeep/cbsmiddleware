export interface IKmMaster {
  id: number;
  branchCode?: string | null;
  farmerName?: string | null;
  farmerNameMr?: string | null;
  farmerAddress?: string | null;
  farmerAddressMr?: string | null;
  gender?: string | null;
  caste?: string | null;
  casteMr?: string | null;
  pacsNumber?: string | null;
  areaHect?: string | null;
  aadharNo?: number | null;
  aadharNoMr?: string | null;
  panNo?: number | null;
  panNoMr?: string | null;
  mobileNo?: string | null;
  mobileNoMr?: string | null;
  kccNo?: string | null;
  kccNoMr?: string | null;
  savingNo?: string | null;
  savingNoMr?: string | null;
  entryFlag?: string | null;
}

export type NewKmMaster = Omit<IKmMaster, 'id'> & { id: null };
