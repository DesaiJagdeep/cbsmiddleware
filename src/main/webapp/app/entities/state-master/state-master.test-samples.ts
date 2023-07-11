import { IStateMaster, NewStateMaster } from './state-master.model';

export const sampleWithRequiredData: IStateMaster = {
  id: 91941,
};

export const sampleWithPartialData: IStateMaster = {
  id: 65551,
};

export const sampleWithFullData: IStateMaster = {
  id: 80206,
  stateCode: 'Sleek',
  stateName: 'Agent Supervisor',
};

export const sampleWithNewData: NewStateMaster = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
