export interface ICastCategoryMaster {
  id: number;
  castCategoryCode?: number | null;
  castCategoryName?: string | null;
}

export type NewCastCategoryMaster = Omit<ICastCategoryMaster, 'id'> & { id: null };
