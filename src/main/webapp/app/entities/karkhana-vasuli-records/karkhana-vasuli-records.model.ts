import { IKarkhanaVasuliFile } from 'app/entities/karkhana-vasuli-file/karkhana-vasuli-file.model';

export interface IKarkhanaVasuliRecords {
  id: number;
  factoryMemberCode?: number | null;
  karkhanaMemberCodeMr?: string | null;
  memberName?: string | null;
  memberNameMr?: string | null;
  villageName?: string | null;
  villageNameMr?: string | null;
  amount?: number | null;
  amountMr?: string | null;
  savingAccNo?: number | null;
  savingAccNoMr?: string | null;
  status?: boolean | null;
  karkhanaVasuliFile?: Pick<IKarkhanaVasuliFile, 'id'> | null;
}

export type NewKarkhanaVasuliRecords = Omit<IKarkhanaVasuliRecords, 'id'> & { id: null };
