export interface ICbsDataReport {
  id: number;
  farmerName?: string | null;
}

export type NewCbsDataReport = Omit<ICbsDataReport, 'id'> & { id: null };
