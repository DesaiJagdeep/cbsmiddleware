export interface IDistrictMaster {
  id: number;
  districtCode?: string | null;
  districtName?: string | null;
  stateCode?: string | null;
}

export type NewDistrictMaster = Omit<IDistrictMaster, 'id'> & { id: null };
