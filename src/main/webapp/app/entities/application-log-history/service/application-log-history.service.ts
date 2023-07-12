import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IApplicationLogHistory, NewApplicationLogHistory } from '../application-log-history.model';

export type PartialUpdateApplicationLogHistory = Partial<IApplicationLogHistory> & Pick<IApplicationLogHistory, 'id'>;

export type EntityResponseType = HttpResponse<IApplicationLogHistory>;
export type EntityArrayResponseType = HttpResponse<IApplicationLogHistory[]>;

@Injectable({ providedIn: 'root' })
export class ApplicationLogHistoryService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/application-log-histories');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(applicationLogHistory: NewApplicationLogHistory): Observable<EntityResponseType> {
    return this.http.post<IApplicationLogHistory>(this.resourceUrl, applicationLogHistory, { observe: 'response' });
  }

  update(applicationLogHistory: IApplicationLogHistory): Observable<EntityResponseType> {
    return this.http.put<IApplicationLogHistory>(
      `${this.resourceUrl}/${this.getApplicationLogHistoryIdentifier(applicationLogHistory)}`,
      applicationLogHistory,
      { observe: 'response' }
    );
  }

  partialUpdate(applicationLogHistory: PartialUpdateApplicationLogHistory): Observable<EntityResponseType> {
    return this.http.patch<IApplicationLogHistory>(
      `${this.resourceUrl}/${this.getApplicationLogHistoryIdentifier(applicationLogHistory)}`,
      applicationLogHistory,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IApplicationLogHistory>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IApplicationLogHistory[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getApplicationLogHistoryIdentifier(applicationLogHistory: Pick<IApplicationLogHistory, 'id'>): number {
    return applicationLogHistory.id;
  }

  compareApplicationLogHistory(o1: Pick<IApplicationLogHistory, 'id'> | null, o2: Pick<IApplicationLogHistory, 'id'> | null): boolean {
    return o1 && o2 ? this.getApplicationLogHistoryIdentifier(o1) === this.getApplicationLogHistoryIdentifier(o2) : o1 === o2;
  }

  addApplicationLogHistoryToCollectionIfMissing<Type extends Pick<IApplicationLogHistory, 'id'>>(
    applicationLogHistoryCollection: Type[],
    ...applicationLogHistoriesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const applicationLogHistories: Type[] = applicationLogHistoriesToCheck.filter(isPresent);
    if (applicationLogHistories.length > 0) {
      const applicationLogHistoryCollectionIdentifiers = applicationLogHistoryCollection.map(
        applicationLogHistoryItem => this.getApplicationLogHistoryIdentifier(applicationLogHistoryItem)!
      );
      const applicationLogHistoriesToAdd = applicationLogHistories.filter(applicationLogHistoryItem => {
        const applicationLogHistoryIdentifier = this.getApplicationLogHistoryIdentifier(applicationLogHistoryItem);
        if (applicationLogHistoryCollectionIdentifiers.includes(applicationLogHistoryIdentifier)) {
          return false;
        }
        applicationLogHistoryCollectionIdentifiers.push(applicationLogHistoryIdentifier);
        return true;
      });
      return [...applicationLogHistoriesToAdd, ...applicationLogHistoryCollection];
    }
    return applicationLogHistoryCollection;
  }
}
