import dayjs from 'dayjs/esm';
import { IKmMaster } from 'app/entities/km-master/km-master.model';

export interface IKmDetails {
  id: number;
  shares?: number | null;
  sharesMr?: string | null;
  sugarShares?: number | null;
  sugarSharesMr?: string | null;
  deposite?: number | null;
  depositeMr?: string | null;
  dueLoan?: number | null;
  dueLoanMr?: string | null;
  dueAmount?: number | null;
  dueAmountMr?: string | null;
  dueDateMr?: string | null;
  dueDate?: dayjs.Dayjs | null;
  kmDate?: dayjs.Dayjs | null;
  kmDateMr?: string | null;
  kmFromDate?: dayjs.Dayjs | null;
  kmFromDateMr?: string | null;
  kmToDate?: dayjs.Dayjs | null;
  kmToDateMr?: string | null;
  bagayatHector?: number | null;
  bagayatHectorMr?: string | null;
  bagayatAre?: number | null;
  bagayatAreMr?: string | null;
  jirayatHector?: number | null;
  jirayatHectorMr?: string | null;
  jirayatAre?: number | null;
  jirayatAreMr?: string | null;
  zindagiAmt?: number | null;
  zindagiNo?: number | null;
  surveyNo?: string | null;
  landValue?: number | null;
  landValueMr?: string | null;
  eAgreementAmt?: number | null;
  eAgreementAmtMr?: string | null;
  eAgreementDate?: dayjs.Dayjs | null;
  eAgreementDateMr?: string | null;
  bojaAmount?: number | null;
  bojaAmountMr?: string | null;
  bojaDate?: dayjs.Dayjs | null;
  bojaDateMr?: string | null;
  kmMaster?: Pick<IKmMaster, 'id'> | null;
}

export type NewKmDetails = Omit<IKmDetails, 'id'> & { id: null };
