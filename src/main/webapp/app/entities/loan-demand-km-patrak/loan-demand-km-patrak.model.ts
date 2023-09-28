import dayjs from 'dayjs/esm';

export interface ILoanDemandKMPatrak {
  id: number;
  demandCode?: string | null;
  date?: dayjs.Dayjs | null;
  kmDate?: dayjs.Dayjs | null;
  shares?: string | null;
  pid?: string | null;
  code?: string | null;
  demandArea?: string | null;
  cropType?: string | null;
  total?: string | null;
  check?: string | null;
  goods?: string | null;
  sharesn?: string | null;
  hn?: string | null;
  area?: string | null;
  hAmount?: string | null;
  name?: string | null;
  khateCode?: string | null;
  remaining?: string | null;
  arrears?: string | null;
  kmAcceptance?: string | null;
  paidDate?: string | null;
  kmCode?: string | null;
  pendingDate?: dayjs.Dayjs | null;
  depositeDate?: dayjs.Dayjs | null;
  accountNumberB?: string | null;
  loanDue?: string | null;
  arrearsB?: string | null;
  dueDateB?: dayjs.Dayjs | null;
  cropB?: string | null;
  kmAcceptanceB?: string | null;
  kmCodeB?: string | null;
  hAgreementNumberB?: string | null;
  hAgreementAreaB?: string | null;
  hAgreementBurdenB?: string | null;
  totalPaidB?: string | null;
  demandAreaB?: string | null;
  checkInTheFormOfPaymentB?: string | null;
  sharesB?: string | null;
  vasulPatraRepaymentDateB?: dayjs.Dayjs | null;
}

export type NewLoanDemandKMPatrak = Omit<ILoanDemandKMPatrak, 'id'> & { id: null };
