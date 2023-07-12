import { IApplication, NewApplication } from './application.model';

export const sampleWithRequiredData: IApplication = {
  id: 99506,
};

export const sampleWithPartialData: IApplication = {
  id: 63817,
  recipientUniqueId: 'Handmade invoice',
};

export const sampleWithFullData: IApplication = {
  id: 41265,
  batchId: 'Cambridgeshire',
  uniqueId: 'Metal value-added',
  recordStatus: 73237,
  applicationStatus: 22450,
  kccStatus: 92763,
  recipientUniqueId: 'Handmade Cotton copying',
  farmerId: 'programming payment Nepal',
};

export const sampleWithNewData: NewApplication = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
