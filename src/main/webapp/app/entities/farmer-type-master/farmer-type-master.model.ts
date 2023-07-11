export interface IFarmerTypeMaster {
  id: number;
  farmerTypeCode?: number | null;
  farmerType?: string | null;
}

export type NewFarmerTypeMaster = Omit<IFarmerTypeMaster, 'id'> & { id: null };
