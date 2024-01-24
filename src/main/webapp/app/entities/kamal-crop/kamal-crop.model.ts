import { IFarmerTypeMaster } from 'app/entities/farmer-type-master/farmer-type-master.model';
import { ICropHangam } from 'app/entities/crop-hangam/crop-hangam.model';
import { ICropMaster } from 'app/entities/crop-master/crop-master.model';
import { IKamalSociety } from 'app/entities/kamal-society/kamal-society.model';

export interface IKamalCrop {
  id: number;
  pacsNumber?: number | null;
  memNo?: number | null;
  memHector?: number | null;
  memNoMr?: string | null;
  memHectorMr?: string | null;
  memAar?: number | null;
  memAarMr?: string | null;
  farmerTypeMaster?: Pick<IFarmerTypeMaster, 'id'> | null;
  cropHangam?: Pick<ICropHangam, 'id'> | null;
  cropMaster?: Pick<ICropMaster, 'id'> | null;
  kamalSociety?: Pick<IKamalSociety, 'id'> | null;
}

export type NewKamalCrop = Omit<IKamalCrop, 'id'> & { id: null };
