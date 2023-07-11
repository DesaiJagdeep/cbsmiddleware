import { IDesignationMaster, NewDesignationMaster } from './designation-master.model';

export const sampleWithRequiredData: IDesignationMaster = {
  id: 90373,
};

export const sampleWithPartialData: IDesignationMaster = {
  id: 80717,
  designationCode: 'Oregon Virtual',
  designationName: 'panel',
};

export const sampleWithFullData: IDesignationMaster = {
  id: 63008,
  designationCode: 'Sausages',
  designationName: 'reboot relationships Chair',
};

export const sampleWithNewData: NewDesignationMaster = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
