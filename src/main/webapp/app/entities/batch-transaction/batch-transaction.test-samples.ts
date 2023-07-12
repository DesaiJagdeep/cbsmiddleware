import { IBatchTransaction, NewBatchTransaction } from './batch-transaction.model';

export const sampleWithRequiredData: IBatchTransaction = {
  id: 76632,
};

export const sampleWithPartialData: IBatchTransaction = {
  id: 86056,
  batchDetails: 'Virtual Hat Officer',
  applicationCount: 22987,
  batchId: 'neutral',
};

export const sampleWithFullData: IBatchTransaction = {
  id: 82070,
  status: 'niches Tasty',
  batchDetails: 'Senior Executive',
  applicationCount: 50191,
  notes: 'invoice',
  batchId: 'Markets',
  batchAckId: 'red',
};

export const sampleWithNewData: NewBatchTransaction = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
