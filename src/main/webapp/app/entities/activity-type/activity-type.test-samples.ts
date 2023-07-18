import { IActivityType, NewActivityType } from './activity-type.model';

export const sampleWithRequiredData: IActivityType = {
  id: 67966,
};

export const sampleWithPartialData: IActivityType = {
  id: 70037,
  activityType: 'RAM online Connecticut',
};

export const sampleWithFullData: IActivityType = {
  id: 87056,
  activityType: 'bleeding-edge Wooden',
  activityTypeCode: 20977,
};

export const sampleWithNewData: NewActivityType = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
