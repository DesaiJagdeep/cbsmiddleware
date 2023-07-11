export interface IPacsMaster {
  id: number;
  pacsName?: string | null;
  pacsNumber?: string | null;
}

export type NewPacsMaster = Omit<IPacsMaster, 'id'> & { id: null };
