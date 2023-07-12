export interface IBatchTransaction {
  id: number;
  status?: string | null;
  batchDetails?: string | null;
  applicationCount?: number | null;
  notes?: string | null;
  batchId?: string | null;
  batchAckId?: string | null;
}

export type NewBatchTransaction = Omit<IBatchTransaction, 'id'> & { id: null };
