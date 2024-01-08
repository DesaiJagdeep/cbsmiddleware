import { IVillageMaster, NewVillageMaster } from './village-master.model';

export const sampleWithRequiredData: IVillageMaster = {
  id: 17139,
  villageName: 'Optimization',
};

export const sampleWithPartialData: IVillageMaster = {
  id: 73569,
  villageName: 'Terrace Cambridgeshire Producer',
  villageNameMr: 'transmit policy e-business',
};

export const sampleWithFullData: IVillageMaster = {
  id: 32273,
  villageName: 'Freeway system',
  villageNameMr: 'face Investor',
  villageCode: 56265,
  villageCodeMr: 'functionalities Concrete',
};

export const sampleWithNewData: NewVillageMaster = {
  villageName: 'Denar salmon',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
