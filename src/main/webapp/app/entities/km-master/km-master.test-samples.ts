import { IKmMaster, NewKmMaster } from './km-master.model';

export const sampleWithRequiredData: IKmMaster = {
  id: 6354,
};

export const sampleWithPartialData: IKmMaster = {
  id: 19113,
  branchCode: 'athwart an',
  farmerName: 'arm-rest',
  farmerAddressMr: 'overreach unfolded',
  gender: 'never sometimes bus',
  cast: 'daydream yet redhead',
  pacsNumber: 'because cottage',
  areaHector: 'belly flintlock ouch',
  aadharNoMr: 'needily',
  panNo: 1056,
  panNoMr: 'lively evangelize cranberry',
  savingNo: 28478,
  pacsMemberCode: 'bear conspire graceful',
};

export const sampleWithFullData: IKmMaster = {
  id: 8901,
  branchCode: 'gee',
  farmerName: 'tart',
  farmerNameMr: 'webbed whoa',
  farmerAddress: 'bogus',
  farmerAddressMr: 'wherever writer',
  gender: 'merchandise above',
  cast: 'even commonly clearly',
  castMr: 'shallow',
  pacsNumber: 'pillow',
  areaHector: 'pad',
  aadharNo: 18996,
  aadharNoMr: 'runaway once',
  panNo: 32269,
  panNoMr: 'nor very bulk',
  mobileNo: 'oof archaeology',
  mobileNoMr: 'although',
  savingNo: 11515,
  savingNoMr: 'honest wisely fond',
  pacsMemberCode: 'likewise faithfully arm',
  entryFlag: 'without incidentally',
};

export const sampleWithNewData: NewKmMaster = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
