import { IIssFileParser } from 'app/entities/iss-file-parser/iss-file-parser.model';

export interface IApplicationLog {
  id: number;
  errorType?: string | null;
  errorCode?: string | null;
  errorMessage?: string | null;
  columnNumber?: number | null;
  sevierity?: string | null;
  expectedSolution?: string | null;
  status?: string | null;
  rowNumber?: number | null;
  batchId?: string | null;
  issFileParser?: Pick<IIssFileParser, 'id' | 'aadharNumber'> | null;
}

export type NewApplicationLog = Omit<IApplicationLog, 'id'> & { id: null };
