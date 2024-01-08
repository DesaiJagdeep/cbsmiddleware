import { IKmCrops, NewKmCrops } from './km-crops.model';

export const sampleWithRequiredData: IKmCrops = {
  id: 64097,
};

export const sampleWithPartialData: IKmCrops = {
  id: 64411,
  hectorMr: 'Berkshire deposit',
  areMr: 'bricks-and-clicks',
  noOfTree: 36061,
  noOfTreeMr: 'Plastic',
  demand: 38918,
  society: 'back-end input',
  societyMr: 'whiteboard Sausages Incredible',
  vibhagiAdhikari: 'hardware Togo',
  vibhagiAdhikariMr: 'SDD Shoes',
  branch: 'Metal Granite',
  inspAmt: 12060,
  inspAmtMr: 'Steel',
};

export const sampleWithFullData: IKmCrops = {
  id: 51261,
  hector: 64919,
  hectorMr: 'Gorgeous Developer',
  are: 59537,
  areMr: 'Dong ubiquitous',
  noOfTree: 74062,
  noOfTreeMr: 'Pine Metal Integration',
  demand: 43892,
  demandMr: 'Steel Robust Human',
  society: 'conglomeration navigating driver',
  societyMr: 'Internal lavender Representative',
  bankAmt: 28894,
  bankAmtMr: 'Soft Product',
  vibhagiAdhikari: 'Ball generating',
  vibhagiAdhikariMr: 'cyan SCSI',
  branch: 'brand Books',
  branchMr: 'generating',
  inspAmt: 49316,
  inspAmtMr: 'Ergonomic fault-tolerant Investor',
};

export const sampleWithNewData: NewKmCrops = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
