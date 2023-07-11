export interface ICategoryMaster {
  id: number;
  castName?: string | null;
  castCode?: string | null;
  castCategoryCode?: string | null;
}

export type NewCategoryMaster = Omit<ICategoryMaster, 'id'> & { id: null };
