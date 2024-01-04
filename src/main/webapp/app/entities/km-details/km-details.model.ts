import dayjs from 'dayjs/esm';
import { IKmMaster } from 'app/entities/km-master/km-master.model';

export interface IKmDetails {
  id: number;
  shares?: number | null;
  sharesMr?: string | null;
  sugarShares?: number | null;
  sugarSharesMr?: string | null;
  deposit?: number | null;
  depositMr?: string | null;
  dueLoan?: number | null;
  dueLoanMr?: string | null;
  dueAmount?: number | null;
  dueAmountMr?: string | null;
  dueDate?: dayjs.Dayjs | null;
  kmDate?: dayjs.Dayjs | null;
  kmFromDate?: dayjs.Dayjs | null;
  kmToDate?: dayjs.Dayjs | null;
  bagayatHector?: number | null;
  bagayatHectorMr?: string | null;
  bagayatAre?: number | null;
  bagayatAreMr?: string | null;
  jirayatHector?: number | null;
  jirayatHectorMr?: string | null;
  jirayatAre?: number | null;
  jirayatAreMr?: string | null;
  landValue?: number | null;
  landValueMr?: string | null;
  eAggrementAmt?: number | null;
  eAgreementAmt?: string | null;
  eAgreementDate?: dayjs.Dayjs | null;
  bojaAmount?: number | null;
  bojaAmountMr?: string | null;
  bojaDate?: dayjs.Dayjs | null;
  kmMaster?: Pick<IKmMaster, 'id'> | null;
}

export type NewKmDetails = Omit<IKmDetails, 'id'> & { id: null };
