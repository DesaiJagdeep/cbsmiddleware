import dayjs from 'dayjs/esm';

import { IKamalSociety, NewKamalSociety } from './kamal-society.model';

export const sampleWithRequiredData: IKamalSociety = {
  id: 26087,
  financialYear: 'invoice Agent',
};

export const sampleWithPartialData: IKamalSociety = {
  id: 79342,
  financialYear: 'stable empower neural',
  kmDate: dayjs('2024-02-01T11:13'),
  pacsNumber: 'Bedfordshire local',
  zindagiPatrakDate: dayjs('2024-02-01T00:01'),
  bagayat: 'Generic Congo Wooden',
  jirayat: 'Valley Burkina defect',
  memberFarmer: 'invoice Cotton',
  memVasuliPer: 'copying bypass',
  bankLoan: 'Territory task-force',
  bankDue: 'green',
  bankVasuli: 'deposit flexibility lavender',
  bankVasuliPer: 'Shirt Marketing',
  balanceSheetDateMr: 'enhance',
  liabilityFund: 'Islands Fish',
  liabilityOtherPayable: 'Plastic',
  assetCash: 'Outdoors SCSI',
  assetMemberLoan: 'parse',
  assetLoss: 'Future',
  villageCode: 'hub Accountability De-engineered',
  pacsVerifiedFlag: false,
  branchVerifiedFlag: true,
};

export const sampleWithFullData: IKamalSociety = {
  id: 82722,
  financialYear: 'invoice',
  kmDate: dayjs('2024-01-31T18:56'),
  kmDateMr: 'Computer',
  kmFromDate: dayjs('2024-02-01T06:54'),
  kmFromDateMr: 'capacitor Personal Flats',
  kmToDate: dayjs('2024-02-01T08:38'),
  kmToDateMr: '1080p wireless',
  pacsNumber: 'indigo redefine orchestrate',
  zindagiPatrakDate: dayjs('2024-01-31T15:27'),
  zindagiPatrakDateMr: 'International Assimilated',
  bankTapasaniDate: dayjs('2024-02-01T08:44'),
  bankTapasaniDateMr: 'Analyst',
  govTapasaniDate: dayjs('2024-02-01T11:49'),
  govTapasaniDateMr: 'fault-tolerant Buckinghamshire Michigan',
  sansthaTapasaniDate: dayjs('2024-02-01T05:24'),
  sansthaTapasaniDateMr: 'Account transmitter',
  totalLand: 'Loan Georgia Samoa',
  bagayat: 'Response Buckinghamshire',
  jirayat: 'Guilder SQL',
  totalFarmer: 'Savings',
  memberFarmer: 'International',
  nonMemberFarmer: 'XML Account Regional',
  talebandDate: dayjs('2024-02-01T02:42'),
  memLoan: 'Designer payment',
  memDue: 'Tasty neural Mandatory',
  memVasuli: 'Buckinghamshire',
  memVasuliPer: 'budgetary disintermediate',
  bankLoan: 'technologies monetize salmon',
  bankDue: 'deposit',
  bankVasuli: 'whiteboard Virginia',
  bankVasuliPer: 'parsing',
  balanceSheetDate: dayjs('2024-01-31T23:16'),
  balanceSheetDateMr: 'Iceland Cheese',
  liabilityAdhikrutShareCapital: 'Forward Central',
  liabilityVasulShareCapital: 'Progressive',
  liabilityFund: 'orchestrate Money',
  liabilitySpareFund: 'Azerbaijanian',
  liabilityDeposite: 'multi-byte 1080p',
  liabilityBalanceSheetBankLoan: 'Cambridgeshire',
  liabilityOtherPayable: 'calculating purple',
  liabilityProfit: 'Fresh programming pixel',
  assetCash: 'Ball Buckinghamshire Identity',
  assetInvestment: 'e-business bypass Steel',
  assetImaratFund: 'Ameliorated',
  assetMemberLoan: 'digital',
  assetDeadStock: 'Rufiyaa Integration',
  assetOtherReceivable: 'eyeballs',
  assetLoss: 'maximized Engineer',
  totalLiability: 'Club orchid open-source',
  totalAsset: 'Pine models',
  villageCode: 'Salad',
  pacsVerifiedFlag: false,
  branchVerifiedFlag: true,
  headOfficeVerifiedFlag: false,
  isSupplimenteryFlag: false,
};

export const sampleWithNewData: NewKamalSociety = {
  financialYear: 'Hong Unbranded Specialist',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
