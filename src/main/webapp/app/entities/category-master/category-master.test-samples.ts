import { ICategoryMaster, NewCategoryMaster } from './category-master.model';

export const sampleWithRequiredData: ICategoryMaster = {
  id: 84285,
};

export const sampleWithPartialData: ICategoryMaster = {
  id: 9785,
  castName: 'Account Investment',
  castCode: 'quantifying Administrator',
  castCategoryCode: 'Executive',
};

export const sampleWithFullData: ICategoryMaster = {
  id: 40371,
  castName: 'Senegal Berkshire innovative',
  castCode: 'synthesize',
  castCategoryCode: 'Chair',
};

export const sampleWithNewData: NewCategoryMaster = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
