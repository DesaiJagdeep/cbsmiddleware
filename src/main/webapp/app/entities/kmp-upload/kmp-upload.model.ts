export interface IKMPUpload {
  id: number;
  fileName?: string | null;
  uniqueFileName?: string | null;
}

export type NewKMPUpload = Omit<IKMPUpload, 'id'> & { id: null };
