import { IPacsMaster, NewPacsMaster } from './pacs-master.model';

export const sampleWithRequiredData: IPacsMaster = {
  id: 2551,
};

export const sampleWithPartialData: IPacsMaster = {
  id: 9036,
  pacsNumber: 'bluetooth',
};

export const sampleWithFullData: IPacsMaster = {
  id: 84687,
  pacsName: 'Tala web-readiness',
  pacsNumber: 'application Cambridgeshire Parks',
};

export const sampleWithNewData: NewPacsMaster = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
