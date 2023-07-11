export interface IOccupationMaster {
  id: number;
  occupationCode?: number | null;
  occupationName?: string | null;
}

export type NewOccupationMaster = Omit<IOccupationMaster, 'id'> & { id: null };
