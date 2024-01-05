import dayjs from 'dayjs/esm';
import { IKmDetails } from 'app/entities/km-details/km-details.model';

export interface IKmLoans {
  id: number;
  cropName?: string | null;
  cropNameMr?: string | null;
  loanDate?: dayjs.Dayjs | null;
  loanAmount?: number | null;
  loanAmountMr?: string | null;
  are?: number | null;
  areMr?: string | null;
  receivableAmt?: number | null;
  receivableAmtMr?: string | null;
  dueAmt?: number | null;
  dueAmtMr?: string | null;
  dueDate?: dayjs.Dayjs | null;
  kmDetails?: Pick<IKmDetails, 'id'> | null;
}

export type NewKmLoans = Omit<IKmLoans, 'id'> & { id: null };
