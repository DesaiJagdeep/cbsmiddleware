import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IApplicationLog, NewApplicationLog } from '../application-log.model';

export type PartialUpdateApplicationLog = Partial<IApplicationLog> & Pick<IApplicationLog, 'id'>;

export type EntityResponseType = HttpResponse<IApplicationLog>;
export type EntityArrayResponseType = HttpResponse<IApplicationLog[]>;

@Injectable({ providedIn: 'root' })
export class ApplicationLogService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/application-logs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(applicationLog: NewApplicationLog): Observable<EntityResponseType> {
    return this.http.post<IApplicationLog>(this.resourceUrl, applicationLog, { observe: 'response' });
  }

  update(applicationLog: IApplicationLog): Observable<EntityResponseType> {
    return this.http.put<IApplicationLog>(`${this.resourceUrl}/${this.getApplicationLogIdentifier(applicationLog)}`, applicationLog, {
      observe: 'response',
    });
  }

  partialUpdate(applicationLog: PartialUpdateApplicationLog): Observable<EntityResponseType> {
    return this.http.patch<IApplicationLog>(`${this.resourceUrl}/${this.getApplicationLogIdentifier(applicationLog)}`, applicationLog, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IApplicationLog>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IApplicationLog[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getApplicationLogIdentifier(applicationLog: Pick<IApplicationLog, 'id'>): number {
    return applicationLog.id;
  }

  compareApplicationLog(o1: Pick<IApplicationLog, 'id'> | null, o2: Pick<IApplicationLog, 'id'> | null): boolean {
    return o1 && o2 ? this.getApplicationLogIdentifier(o1) === this.getApplicationLogIdentifier(o2) : o1 === o2;
  }

  addApplicationLogToCollectionIfMissing<Type extends Pick<IApplicationLog, 'id'>>(
    applicationLogCollection: Type[],
    ...applicationLogsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const applicationLogs: Type[] = applicationLogsToCheck.filter(isPresent);
    if (applicationLogs.length > 0) {
      const applicationLogCollectionIdentifiers = applicationLogCollection.map(
        applicationLogItem => this.getApplicationLogIdentifier(applicationLogItem)!
      );
      const applicationLogsToAdd = applicationLogs.filter(applicationLogItem => {
        const applicationLogIdentifier = this.getApplicationLogIdentifier(applicationLogItem);
        if (applicationLogCollectionIdentifiers.includes(applicationLogIdentifier)) {
          return false;
        }
        applicationLogCollectionIdentifiers.push(applicationLogIdentifier);
        return true;
      });
      return [...applicationLogsToAdd, ...applicationLogCollection];
    }
    return applicationLogCollection;
  }
}
