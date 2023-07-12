import { IApplicationLog, NewApplicationLog } from './application-log.model';

export const sampleWithRequiredData: IApplicationLog = {
  id: 92230,
};

export const sampleWithPartialData: IApplicationLog = {
  id: 54615,
  errorType: 'User-centric Quality-focused Card',
};

export const sampleWithFullData: IApplicationLog = {
  id: 27688,
  errorType: 'Handmade Jewelery Ergonomic',
  errorCode: 'architectures integrate hierarchy',
  errorMessage: 'Handcrafted wireless neural',
  columnNumber: 66394,
  sevierity: 'coherent up deposit',
  expectedSolution: 'Ball',
  status: 'Customer 24/7 Bedfordshire',
  rowNumber: 18473,
  batchId: 'Reactive',
};

export const sampleWithNewData: NewApplicationLog = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
