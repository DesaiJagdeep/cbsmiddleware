import { IBankMaster } from 'app/entities/bank-master/bank-master.model';

export interface IBankBranchMaster {
  id: number;
  branchCode?: string | null;
  branchName?: string | null;
  branchAddress?: string | null;
  bankMaster?: Pick<IBankMaster, 'id' | 'bankName'> | null;
}

export type NewBankBranchMaster = Omit<IBankBranchMaster, 'id'> & { id: null };
