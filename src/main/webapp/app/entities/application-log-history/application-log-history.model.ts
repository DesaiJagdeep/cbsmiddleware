import { IIssFileParser } from 'app/entities/iss-file-parser/iss-file-parser.model';

export interface IApplicationLogHistory {
  id: number;
  errorType?: string | null;
  errorCode?: string | null;
  errorMessage?: string | null;
  rowNumber?: number | null;
  columnNumber?: number | null;
  sevierity?: string | null;
  expectedSolution?: string | null;
  status?: string | null;
  issFileParser?: Pick<IIssFileParser, 'id' | 'aadharNumber'> | null;
}

export type NewApplicationLogHistory = Omit<IApplicationLogHistory, 'id'> & { id: null };
