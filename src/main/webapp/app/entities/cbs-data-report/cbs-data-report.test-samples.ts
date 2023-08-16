import { ICbsDataReport, NewCbsDataReport } from './cbs-data-report.model';

export const sampleWithRequiredData: ICbsDataReport = {
  id: 72226,
};

export const sampleWithPartialData: ICbsDataReport = {
  id: 11907,
  farmerName: 'Andorra',
};

export const sampleWithFullData: ICbsDataReport = {
  id: 21811,
  farmerName: 'real-time ubiquitous',
};

export const sampleWithNewData: NewCbsDataReport = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
