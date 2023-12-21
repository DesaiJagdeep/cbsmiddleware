import dayjs from 'dayjs/esm';
import { IFactoryMaster } from 'app/entities/factory-master/factory-master.model';

export interface IKarkhanaVasuliFile {
  id: number;
  fileName?: string | null;
  uniqueFileName?: string | null;
  address?: string | null;
  addressMr?: string | null;
  hangam?: string | null;
  hangamMr?: string | null;
  factoryName?: string | null;
  factoryNameMr?: string | null;
  totalAmount?: number | null;
  totalAmountMr?: string | null;
  fromDate?: dayjs.Dayjs | null;
  toDate?: dayjs.Dayjs | null;
  branchCode?: number | null;
  pacsName?: string | null;
  factoryMaster?: Pick<IFactoryMaster, 'id'> | null;
}

export type NewKarkhanaVasuliFile = Omit<IKarkhanaVasuliFile, 'id'> & { id: null };
