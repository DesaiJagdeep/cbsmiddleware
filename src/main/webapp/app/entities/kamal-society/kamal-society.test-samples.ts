import dayjs from 'dayjs/esm';

import { IKamalSociety, NewKamalSociety } from './kamal-society.model';

export const sampleWithRequiredData: IKamalSociety = {
  id: 26087,
  financialYear: 'invoice Agent',
};

export const sampleWithPartialData: IKamalSociety = {
  id: 48815,
  financialYear: 'Response Shirt Dakota',
  kmDate: dayjs('2024-01-31T22:15'),
  pacsNumber: 'interactive',
  zindagiPatrakDate: dayjs('2024-02-01T01:01'),
  bagayat: 'Alabama',
  jirayat: 'Generic Congo Wooden',
  memberFarmer: 'Valley Burkina defect',
  memVasuliPer: 'invoice Cotton',
  bankLoan: 'copying bypass',
  bankDue: 'Territory task-force',
  bankVasuli: 'green',
  bankVasuliPer: 'deposit flexibility lavender',
  balanceSheetDateMr: 'Shirt Marketing',
  liabilityFund: 'enhance',
  liabilityProfit: 'Islands Fish',
  assetInvestment: 'Plastic',
  assetDeadStock: 'Outdoors SCSI',
  totalLiability: 'parse',
  pacsVerifiedFlag: false,
  branchVerifiedFlag: true,
  headOfficeVerifiedFlag: false,
};

export const sampleWithFullData: IKamalSociety = {
  id: 38816,
  financialYear: 'hub Accountability De-engineered',
  kmDate: dayjs('2024-02-01T03:01'),
  kmDateMr: 'hacking bypass Rustic',
  kmFromDate: dayjs('2024-01-31T17:01'),
  kmFromDateMr: 'Optimization Liaison Self-enabling',
  kmToDate: dayjs('2024-01-31T22:08'),
  kmToDateMr: 'system matrix indigo',
  pacsNumber: 'Architect Towels',
  zindagiPatrakDate: dayjs('2024-01-31T21:31'),
  zindagiPatrakDateMr: 'Comoro Music Frozen',
  bankTapasaniDate: dayjs('2024-01-31T16:54'),
  bankTapasaniDateMr: 'compressing',
  govTapasaniDate: dayjs('2024-01-31T18:37'),
  govTapasaniDateMr: 'optical content',
  sansthaTapasaniDate: dayjs('2024-02-01T03:55'),
  sansthaTapasaniDateMr: 'Fresh navigating',
  totalLand: 'connecting indigo',
  bagayat: 'Regional user-facing online',
  jirayat: 'Ball Generic Mouse',
  totalFarmer: 'Refined Account Regional',
  memberFarmer: 'Loan Handcrafted',
  nonMemberFarmer: 'Senior',
  talebandDate: dayjs('2024-02-01T05:44'),
  memLoan: 'Jewelery',
  memDue: 'Mandatory Open-architected',
  memVasuli: 'Kansas discrete',
  memVasuliPer: 'interface interface Intelligent',
  bankLoan: 'Concrete whiteboard',
  bankDue: 'Product parsing',
  bankVasuli: 'Ohio Rustic',
  bankVasuliPer: 'Account Chief Engineer',
  balanceSheetDate: dayjs('2024-01-31T20:26'),
  balanceSheetDateMr: 'Berkshire calculate',
  liabilityAdhikrutShareCapital: 'Clothing',
  liabilityVasulShareCapital: 'Island Soft',
  liabilityFund: 'Health Berkshire',
  liabilityDeposite: 'Bedfordshire Consultant Turnpike',
  liabilityBalanceSheetBankLoan: 'programming pixel',
  liabilityOtherPayable: 'Ball Buckinghamshire Identity',
  liabilityProfit: 'e-business bypass Steel',
  assetCash: 'Ameliorated',
  assetInvestment: 'digital',
  assetImaratFund: 'Rufiyaa Integration',
  assetMemberLoan: 'eyeballs',
  assetDeadStock: 'maximized Engineer',
  assetOtherReceivable: 'Club orchid open-source',
  assetLoss: 'Pine models',
  totalLiability: 'Salad',
  totalAsset: 'Research',
  villageCode: 'Hong Unbranded Specialist',
  pacsVerifiedFlag: true,
  branchVerifiedFlag: true,
  headOfficeVerifiedFlag: false,
  isSupplimenteryFlag: true,
};

export const sampleWithNewData: NewKamalSociety = {
  financialYear: 'Auto',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
