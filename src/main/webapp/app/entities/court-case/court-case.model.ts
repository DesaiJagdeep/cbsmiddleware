import dayjs from 'dayjs/esm';

export interface ICourtCase {
  id: number;
  srNo?: string | null;
  accountNo?: string | null;
  nameOfDefaulter?: string | null;
  address?: string | null;
  loanType?: string | null;
  loanAmount?: number | null;
  loanDate?: dayjs.Dayjs | null;
  termOfLoan?: string | null;
  interestRate?: number | null;
  installmentAmount?: number | null;
  totalCredit?: number | null;
  balance?: number | null;
  interestPaid?: number | null;
  penalInterestPaid?: number | null;
  dueAmount?: number | null;
  dueDate?: dayjs.Dayjs | null;
  dueInterest?: number | null;
  duePenalInterest?: number | null;
  dueMoreInterest?: number | null;
  interestRecivable?: number | null;
  gaurentorOne?: string | null;
  gaurentorOneAddress?: string | null;
  gaurentorTwo?: string | null;
  gaurentorTwoAddress?: string | null;
  firstNoticeDate?: dayjs.Dayjs | null;
  secondNoticeDate?: dayjs.Dayjs | null;
}

export type NewCourtCase = Omit<ICourtCase, 'id'> & { id: null };
