export interface ITalukaMaster {
  id: number;
  talukaCode?: string | null;
  talukaName?: string | null;
  districtCode?: string | null;
}

export type NewTalukaMaster = Omit<ITalukaMaster, 'id'> & { id: null };
