import { IVillageMaster, NewVillageMaster } from './village-master.model';

export const sampleWithRequiredData: IVillageMaster = {
  id: 27007,
  villageName: 'crazy but',
};

export const sampleWithPartialData: IVillageMaster = {
  id: 4798,
  villageName: 'belch phew',
  villageNameMr: 'knottily as who',
};

export const sampleWithFullData: IVillageMaster = {
  id: 31363,
  villageName: 'cycle',
  villageNameMr: 'nor roll',
  villageCode: 21548,
  villageCodeMr: 'up present courteous',
};

export const sampleWithNewData: NewVillageMaster = {
  villageName: 'zowie yell',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
