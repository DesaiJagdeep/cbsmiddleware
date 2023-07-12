import { IApplicationLogHistory, NewApplicationLogHistory } from './application-log-history.model';

export const sampleWithRequiredData: IApplicationLogHistory = {
  id: 39703,
};

export const sampleWithPartialData: IApplicationLogHistory = {
  id: 64217,
  errorType: 'Bike compressing red',
  errorMessage: 'protocol Montana navigating',
  columnNumber: 89378,
  expectedSolution: 'Re-contextualized',
};

export const sampleWithFullData: IApplicationLogHistory = {
  id: 55866,
  errorType: 'Compatible',
  errorCode: 'Peso Soap',
  errorMessage: 'Object-based',
  rowNumber: 4274,
  columnNumber: 13955,
  sevierity: 'distributed cyan Pennsylvania',
  expectedSolution: 'Buckinghamshire PNG magnetic',
  status: 'Forest Steel',
};

export const sampleWithNewData: NewApplicationLogHistory = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
