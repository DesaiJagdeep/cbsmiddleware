export interface ICropHangam {
  id: number;
  hangam?: string | null;
  hangamMr?: string | null;
}

export type NewCropHangam = Omit<ICropHangam, 'id'> & { id: null };
