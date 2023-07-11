export interface IBankMaster {
  id: number;
  bankCode?: string | null;
  bankName?: string | null;
}

export type NewBankMaster = Omit<IBankMaster, 'id'> & { id: null };
