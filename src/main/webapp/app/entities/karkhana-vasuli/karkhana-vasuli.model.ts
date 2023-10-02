export interface IKarkhanaVasuli {
  id: number;
  khataNumber?: string | null;
  name?: string | null;
  societyName?: string | null;
  talukaName?: string | null;
  branchName?: string | null;
  defaulterName?: string | null;
}

export type NewKarkhanaVasuli = Omit<IKarkhanaVasuli, 'id'> & { id: null };
