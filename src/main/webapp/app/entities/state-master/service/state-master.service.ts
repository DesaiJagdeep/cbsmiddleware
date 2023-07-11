import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IStateMaster, NewStateMaster } from '../state-master.model';

export type PartialUpdateStateMaster = Partial<IStateMaster> & Pick<IStateMaster, 'id'>;

export type EntityResponseType = HttpResponse<IStateMaster>;
export type EntityArrayResponseType = HttpResponse<IStateMaster[]>;

@Injectable({ providedIn: 'root' })
export class StateMasterService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/state-masters');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(stateMaster: NewStateMaster): Observable<EntityResponseType> {
    return this.http.post<IStateMaster>(this.resourceUrl, stateMaster, { observe: 'response' });
  }

  update(stateMaster: IStateMaster): Observable<EntityResponseType> {
    return this.http.put<IStateMaster>(`${this.resourceUrl}/${this.getStateMasterIdentifier(stateMaster)}`, stateMaster, {
      observe: 'response',
    });
  }

  partialUpdate(stateMaster: PartialUpdateStateMaster): Observable<EntityResponseType> {
    return this.http.patch<IStateMaster>(`${this.resourceUrl}/${this.getStateMasterIdentifier(stateMaster)}`, stateMaster, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IStateMaster>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IStateMaster[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getStateMasterIdentifier(stateMaster: Pick<IStateMaster, 'id'>): number {
    return stateMaster.id;
  }

  compareStateMaster(o1: Pick<IStateMaster, 'id'> | null, o2: Pick<IStateMaster, 'id'> | null): boolean {
    return o1 && o2 ? this.getStateMasterIdentifier(o1) === this.getStateMasterIdentifier(o2) : o1 === o2;
  }

  addStateMasterToCollectionIfMissing<Type extends Pick<IStateMaster, 'id'>>(
    stateMasterCollection: Type[],
    ...stateMastersToCheck: (Type | null | undefined)[]
  ): Type[] {
    const stateMasters: Type[] = stateMastersToCheck.filter(isPresent);
    if (stateMasters.length > 0) {
      const stateMasterCollectionIdentifiers = stateMasterCollection.map(
        stateMasterItem => this.getStateMasterIdentifier(stateMasterItem)!
      );
      const stateMastersToAdd = stateMasters.filter(stateMasterItem => {
        const stateMasterIdentifier = this.getStateMasterIdentifier(stateMasterItem);
        if (stateMasterCollectionIdentifiers.includes(stateMasterIdentifier)) {
          return false;
        }
        stateMasterCollectionIdentifiers.push(stateMasterIdentifier);
        return true;
      });
      return [...stateMastersToAdd, ...stateMasterCollection];
    }
    return stateMasterCollection;
  }
}
