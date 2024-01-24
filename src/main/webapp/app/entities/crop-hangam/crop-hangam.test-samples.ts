import { ICropHangam, NewCropHangam } from './crop-hangam.model';

export const sampleWithRequiredData: ICropHangam = {
  id: 77992,
};

export const sampleWithPartialData: ICropHangam = {
  id: 94208,
  hangam: 'Drive digital FTP',
};

export const sampleWithFullData: ICropHangam = {
  id: 70080,
  hangam: 'Total',
  hangamMr: 'Berkshire RAM',
};

export const sampleWithNewData: NewCropHangam = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
