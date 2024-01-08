import dayjs from 'dayjs/esm';
import { ICropMaster } from 'app/entities/crop-master/crop-master.model';
import { IKmDetails } from 'app/entities/km-details/km-details.model';

export interface IKmLoans {
  id: number;
  hector?: number | null;
  hectorMr?: string | null;
  are?: number | null;
  aremr?: string | null;
  noOfTree?: number | null;
  noOfTreeMr?: string | null;
  sanctionAmt?: number | null;
  sanctionAmtMr?: string | null;
  loanAmt?: number | null;
  loanAmtMr?: string | null;
  receivableAmt?: number | null;
  receivableAmtMr?: string | null;
  dueAmt?: number | null;
  dueAmtMr?: string | null;
  dueDate?: dayjs.Dayjs | null;
  dueDateMr?: string | null;
  spare?: string | null;
  cropMaster?: Pick<ICropMaster, 'id'> | null;
  kmDetails?: Pick<IKmDetails, 'id'> | null;
}

export type NewKmLoans = Omit<IKmLoans, 'id'> & { id: null };
