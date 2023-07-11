import { ISeasonMaster, NewSeasonMaster } from './season-master.model';

export const sampleWithRequiredData: ISeasonMaster = {
  id: 81065,
};

export const sampleWithPartialData: ISeasonMaster = {
  id: 82644,
  seasonCode: 40250,
};

export const sampleWithFullData: ISeasonMaster = {
  id: 70113,
  seasonCode: 79506,
  seasonName: 'Inverse',
};

export const sampleWithNewData: NewSeasonMaster = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
