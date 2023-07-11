export interface IStateMaster {
  id: number;
  stateCode?: string | null;
  stateName?: string | null;
}

export type NewStateMaster = Omit<IStateMaster, 'id'> & { id: null };
