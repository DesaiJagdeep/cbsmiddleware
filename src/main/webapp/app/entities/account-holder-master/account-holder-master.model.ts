export interface IAccountHolderMaster {
  id: number;
  accountHolderCode?: number | null;
  accountHolder?: string | null;
}

export type NewAccountHolderMaster = Omit<IAccountHolderMaster, 'id'> & { id: null };
