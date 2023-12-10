import { ITalukaMaster } from 'app/entities/taluka-master/taluka-master.model';

export interface IVillageMaster {
  id: number;
  villageName?: string | null;
  villageNameMr?: string | null;
  villageCode?: number | null;
  villageCodeMr?: string | null;
  talukaMaster?: Pick<ITalukaMaster, 'id'> | null;
}

export type NewVillageMaster = Omit<IVillageMaster, 'id'> & { id: null };
