export interface IRelativeMaster {
  id: number;
  relativeCode?: number | null;
  relativeName?: string | null;
}

export type NewRelativeMaster = Omit<IRelativeMaster, 'id'> & { id: null };
