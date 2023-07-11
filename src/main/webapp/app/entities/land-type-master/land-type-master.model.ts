export interface ILandTypeMaster {
  id: number;
  landTypeCode?: number | null;
  landType?: string | null;
}

export type NewLandTypeMaster = Omit<ILandTypeMaster, 'id'> & { id: null };
