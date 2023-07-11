export interface IDesignationMaster {
  id: number;
  designationCode?: string | null;
  designationName?: string | null;
}

export type NewDesignationMaster = Omit<IDesignationMaster, 'id'> & { id: null };
