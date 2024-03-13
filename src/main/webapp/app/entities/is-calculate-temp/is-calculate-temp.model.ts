export interface IIsCalculateTemp {
  id: number;
  serialNo?: number | null;
  financialYear?: string | null;
}

export type NewIsCalculateTemp = Omit<IIsCalculateTemp, 'id'> & { id: null };
