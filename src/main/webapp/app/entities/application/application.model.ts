import { IIssFileParser } from 'app/entities/iss-file-parser/iss-file-parser.model';

export interface IApplication {
  id: number;
  batchId?: string | null;
  uniqueId?: string | null;
  recordStatus?: number | null;
  applicationStatus?: number | null;
  kccStatus?: number | null;
  recipientUniqueId?: string | null;
  farmerId?: string | null;
  issFileParser?: Pick<IIssFileParser, 'id' | 'aadharNumber'> | null;
}

export type NewApplication = Omit<IApplication, 'id'> & { id: null };
