import { ICropCategoryMaster, NewCropCategoryMaster } from './crop-category-master.model';

export const sampleWithRequiredData: ICropCategoryMaster = {
  id: 54614,
};

export const sampleWithPartialData: ICropCategoryMaster = {
  id: 14258,
};

export const sampleWithFullData: ICropCategoryMaster = {
  id: 40996,
  categoryCode: 'Money calculate Ball',
  categoryName: 'Factors France',
};

export const sampleWithNewData: NewCropCategoryMaster = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
