export interface ICropCategoryMaster {
  id: number;
  categoryCode?: string | null;
  categoryName?: string | null;
}

export type NewCropCategoryMaster = Omit<ICropCategoryMaster, 'id'> & { id: null };
