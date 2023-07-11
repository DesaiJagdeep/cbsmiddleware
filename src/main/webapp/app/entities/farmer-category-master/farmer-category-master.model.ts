export interface IFarmerCategoryMaster {
  id: number;
  farmerCategoryCode?: number | null;
  farmerCategory?: string | null;
}

export type NewFarmerCategoryMaster = Omit<IFarmerCategoryMaster, 'id'> & { id: null };
