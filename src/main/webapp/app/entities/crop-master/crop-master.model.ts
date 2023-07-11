export interface ICropMaster {
  id: number;
  cropCode?: string | null;
  cropName?: string | null;
  categoryCode?: string | null;
}

export type NewCropMaster = Omit<ICropMaster, 'id'> & { id: null };
