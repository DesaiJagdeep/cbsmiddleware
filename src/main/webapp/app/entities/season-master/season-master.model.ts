export interface ISeasonMaster {
  id: number;
  seasonCode?: number | null;
  seasonName?: string | null;
}

export type NewSeasonMaster = Omit<ISeasonMaster, 'id'> & { id: null };
