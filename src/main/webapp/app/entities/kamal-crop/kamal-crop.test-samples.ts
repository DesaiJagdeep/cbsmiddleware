import { IKamalCrop, NewKamalCrop } from './kamal-crop.model';

export const sampleWithRequiredData: IKamalCrop = {
  id: 94816,
};

export const sampleWithPartialData: IKamalCrop = {
  id: 1567,
  pacsNumber: 'SQL Plastic',
  financialYear: 'Tasty Bermuda Utah',
  memberCount: 'withdrawal Plastic',
  area: 'Manat mindshare',
  headOfficeAmount: 'hacking Cambridgeshire York',
};

export const sampleWithFullData: IKamalCrop = {
  id: 67393,
  pacsNumber: 'multi-byte',
  financialYear: 'Assistant incentivize Franc',
  memberCount: 'turquoise Awesome',
  area: 'De-engineered customer synthesize',
  pacsAmount: 'magenta Synchronised Road',
  branchAmount: 'visionary',
  headOfficeAmount: 'SQL',
  cropEligibilityAmount: 'grey',
};

export const sampleWithNewData: NewKamalCrop = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
