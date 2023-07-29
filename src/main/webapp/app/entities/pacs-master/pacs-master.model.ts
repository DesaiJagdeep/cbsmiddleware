import { IBankBranchMaster } from 'app/entities/bank-branch-master/bank-branch-master.model';

export interface IPacsMaster {
  id: number;
  pacsName?: string | null;
  pacsNumber?: string | null;
  bankBranchMaster?: Pick<IBankBranchMaster, 'id' | 'branchName'> | null;
}

export type NewPacsMaster = Omit<IPacsMaster, 'id'> & { id: null };
