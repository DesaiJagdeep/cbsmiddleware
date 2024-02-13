import { IKamalSociety } from 'app/entities/kamal-society/kamal-society.model';
import { IFarmerTypeMaster } from 'app/entities/farmer-type-master/farmer-type-master.model';
import { ISeasonMaster } from 'app/entities/season-master/season-master.model';
import { ICropMaster } from 'app/entities/crop-master/crop-master.model';

export interface IKamalCrop {
  id: number;
  pacsNumber?: string | null;
  financialYear?: string | null;
  memberCount?: string | null;
  area?: string | null;
  pacsAmount?: string | null;
  branchAmount?: string | null;
  headOfficeAmount?: string | null;
  divisionalOfficeAmount?: string | null;
  cropEligibilityAmount?: string | null;
  kamalSociety?: Pick<IKamalSociety, 'id'> | null;
  farmerTypeMaster?: Pick<IFarmerTypeMaster, 'id'> | null;
  seasonMaster?: Pick<ISeasonMaster, 'id'> | null;
  cropMaster?: Pick<ICropMaster, 'id'> | null;
}

export type NewKamalCrop = Omit<IKamalCrop, 'id'> & { id: null };
