import { IKmCrops, NewKmCrops } from './km-crops.model';

export const sampleWithRequiredData: IKmCrops = {
  id: 17743,
};

export const sampleWithPartialData: IKmCrops = {
  id: 12966,
  cropName: 'gloomy seep deliquesce',
  hector: 18032.33,
  are: 31554.52,
  areMr: 'yet',
  prviousAmt: 11171.05,
  previousAmtMr: 'atop',
  demand: 28812.39,
  society: 'roust',
  societyMr: 'turbocharge',
  bankAmt: 13142.91,
  bankAmtMr: 'machine east',
  noOfTreeMr: 'serpentine down heavily',
};

export const sampleWithFullData: IKmCrops = {
  id: 28445,
  cropName: 'scope rock cheerfully',
  cropNameMr: 'upside-down hmph',
  hector: 1598.92,
  hectorMr: 'to',
  are: 26083.72,
  areMr: 'bewitched jubilantly',
  prviousAmt: 30766.73,
  previousAmtMr: 'than reinstate wiggly',
  demand: 5157.52,
  demandMr: 'ick boo',
  society: 'absentmindedly and misread',
  societyMr: 'wide',
  bankAmt: 23137.23,
  bankAmtMr: 'why yum drat',
  noOfTree: 28746.9,
  noOfTreeMr: 'now welcome',
};

export const sampleWithNewData: NewKmCrops = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
