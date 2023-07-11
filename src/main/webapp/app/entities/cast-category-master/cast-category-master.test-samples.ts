import { ICastCategoryMaster, NewCastCategoryMaster } from './cast-category-master.model';

export const sampleWithRequiredData: ICastCategoryMaster = {
  id: 688,
};

export const sampleWithPartialData: ICastCategoryMaster = {
  id: 29494,
  castCategoryName: 'Metrics time-frame Administrator',
};

export const sampleWithFullData: ICastCategoryMaster = {
  id: 31012,
  castCategoryCode: 16300,
  castCategoryName: 'Corporate partnerships Mall',
};

export const sampleWithNewData: NewCastCategoryMaster = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
