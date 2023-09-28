import dayjs from 'dayjs/esm';

import { ILoanDemandKMPatrak, NewLoanDemandKMPatrak } from './loan-demand-km-patrak.model';

export const sampleWithRequiredData: ILoanDemandKMPatrak = {
  id: 41308,
};

export const sampleWithPartialData: ILoanDemandKMPatrak = {
  id: 49884,
  shares: 'Heights Metal Auto',
  pid: 'Card',
  check: 'Iran',
  sharesn: 'Legacy Georgia',
  area: 'Ergonomic deposit',
  remaining: 'payment visualize',
  kmAcceptance: 'connect SMTP copy',
  paidDate: 'Hat Buckinghamshire',
  kmCode: 'transmitter Account monitoring',
  accountNumberB: 'Tasty Chicken XSS',
  loanDue: 'Personal Cloned',
  arrearsB: 'Designer Music',
  dueDateB: dayjs('2023-09-27'),
  kmAcceptanceB: 'FTP Music salmon',
  kmCodeB: 'experiences bandwidth distributed',
  hAgreementNumberB: 'bi-directional',
  totalPaidB: 'Grove',
  checkInTheFormOfPaymentB: 'Plastic Springs Reverse-engineered',
  sharesB: 'index',
  vasulPatraRepaymentDateB: dayjs('2023-09-27'),
};

export const sampleWithFullData: ILoanDemandKMPatrak = {
  id: 64950,
  demandCode: 'Maine Borders Shirt',
  date: dayjs('2023-09-27'),
  kmDate: dayjs('2023-09-27'),
  shares: 'Jewelery multimedia',
  pid: 'bypass Wisconsin Rustic',
  code: 'Ergonomic mission-critical',
  demandArea: 'transform',
  cropType: 'Metrics virtual',
  total: 'Facilitator',
  check: 'Interactions Steel back-end',
  goods: 'Human Wooden bleeding-edge',
  sharesn: 'Unbranded Officer',
  hn: 'B2C',
  area: 'Garden IB',
  hAmount: 'withdrawal Argentine whiteboard',
  name: 'silver parallelism',
  khateCode: 'applications Granite Kids',
  remaining: 'Sausages Towels',
  arrears: 'Data Loaf',
  kmAcceptance: 'navigating',
  paidDate: 'Avon',
  kmCode: 'Account Texas Upgradable',
  pendingDate: dayjs('2023-09-27'),
  depositeDate: dayjs('2023-09-28'),
  accountNumberB: 'Sausages',
  loanDue: 'Cambridgeshire',
  arrearsB: 'National firmware Yuan',
  dueDateB: dayjs('2023-09-28'),
  cropB: 'infrastructures',
  kmAcceptanceB: 'Quality-focused Interactions',
  kmCodeB: 'interface',
  hAgreementNumberB: 'Steel Sports',
  hAgreementAreaB: 'Station multi-byte',
  hAgreementBurdenB: 'Infrastructure Programmable Pre-emptive',
  totalPaidB: 'Gorgeous Industrial',
  demandAreaB: 'Mountain complexity',
  checkInTheFormOfPaymentB: 'open-source',
  sharesB: 'Bedfordshire Compatible',
  vasulPatraRepaymentDateB: dayjs('2023-09-27'),
};

export const sampleWithNewData: NewLoanDemandKMPatrak = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
