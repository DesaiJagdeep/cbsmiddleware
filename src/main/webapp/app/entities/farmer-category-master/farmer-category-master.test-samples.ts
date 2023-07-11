import { IFarmerCategoryMaster, NewFarmerCategoryMaster } from './farmer-category-master.model';

export const sampleWithRequiredData: IFarmerCategoryMaster = {
  id: 85643,
};

export const sampleWithPartialData: IFarmerCategoryMaster = {
  id: 90465,
  farmerCategoryCode: 83519,
  farmerCategory: 'Indiana scalable deposit',
};

export const sampleWithFullData: IFarmerCategoryMaster = {
  id: 5922,
  farmerCategoryCode: 34389,
  farmerCategory: 'Towels',
};

export const sampleWithNewData: NewFarmerCategoryMaster = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
