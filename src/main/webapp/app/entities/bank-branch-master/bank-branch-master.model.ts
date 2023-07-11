export interface IBankBranchMaster {
  id: number;
  branchCode?: string | null;
  branchName?: string | null;
  branchAddress?: string | null;
  bankCode?: string | null;
}

export type NewBankBranchMaster = Omit<IBankBranchMaster, 'id'> & { id: null };
