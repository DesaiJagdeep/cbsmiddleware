import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IIssFileParser, NewIssFileParser } from '../iss-file-parser.model';

export type PartialUpdateIssFileParser = Partial<IIssFileParser> & Pick<IIssFileParser, 'id'>;

export type EntityResponseType = HttpResponse<IIssFileParser>;
export type EntityArrayResponseType = HttpResponse<IIssFileParser[]>;

@Injectable({ providedIn: 'root' })
export class IssFileParserService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/iss-file-parsers');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(issFileParser: NewIssFileParser): Observable<EntityResponseType> {
    return this.http.post<IIssFileParser>(this.resourceUrl, issFileParser, { observe: 'response' });
  }

  update(issFileParser: IIssFileParser): Observable<EntityResponseType> {
    return this.http.put<IIssFileParser>(`${this.resourceUrl}/${this.getIssFileParserIdentifier(issFileParser)}`, issFileParser, {
      observe: 'response',
    });
  }

  partialUpdate(issFileParser: PartialUpdateIssFileParser): Observable<EntityResponseType> {
    return this.http.patch<IIssFileParser>(`${this.resourceUrl}/${this.getIssFileParserIdentifier(issFileParser)}`, issFileParser, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IIssFileParser>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IIssFileParser[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getIssFileParserIdentifier(issFileParser: Pick<IIssFileParser, 'id'>): number {
    return issFileParser.id;
  }

  compareIssFileParser(o1: Pick<IIssFileParser, 'id'> | null, o2: Pick<IIssFileParser, 'id'> | null): boolean {
    return o1 && o2 ? this.getIssFileParserIdentifier(o1) === this.getIssFileParserIdentifier(o2) : o1 === o2;
  }

  addIssFileParserToCollectionIfMissing<Type extends Pick<IIssFileParser, 'id'>>(
    issFileParserCollection: Type[],
    ...issFileParsersToCheck: (Type | null | undefined)[]
  ): Type[] {
    const issFileParsers: Type[] = issFileParsersToCheck.filter(isPresent);
    if (issFileParsers.length > 0) {
      const issFileParserCollectionIdentifiers = issFileParserCollection.map(
        issFileParserItem => this.getIssFileParserIdentifier(issFileParserItem)!
      );
      const issFileParsersToAdd = issFileParsers.filter(issFileParserItem => {
        const issFileParserIdentifier = this.getIssFileParserIdentifier(issFileParserItem);
        if (issFileParserCollectionIdentifiers.includes(issFileParserIdentifier)) {
          return false;
        }
        issFileParserCollectionIdentifiers.push(issFileParserIdentifier);
        return true;
      });
      return [...issFileParsersToAdd, ...issFileParserCollection];
    }
    return issFileParserCollection;
  }
}
