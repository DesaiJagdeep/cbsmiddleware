import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITalukaMaster, NewTalukaMaster } from '../taluka-master.model';

export type PartialUpdateTalukaMaster = Partial<ITalukaMaster> & Pick<ITalukaMaster, 'id'>;

export type EntityResponseType = HttpResponse<ITalukaMaster>;
export type EntityArrayResponseType = HttpResponse<ITalukaMaster[]>;

@Injectable({ providedIn: 'root' })
export class TalukaMasterService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/taluka-masters');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(talukaMaster: NewTalukaMaster): Observable<EntityResponseType> {
    return this.http.post<ITalukaMaster>(this.resourceUrl, talukaMaster, { observe: 'response' });
  }

  update(talukaMaster: ITalukaMaster): Observable<EntityResponseType> {
    return this.http.put<ITalukaMaster>(`${this.resourceUrl}/${this.getTalukaMasterIdentifier(talukaMaster)}`, talukaMaster, {
      observe: 'response',
    });
  }

  partialUpdate(talukaMaster: PartialUpdateTalukaMaster): Observable<EntityResponseType> {
    return this.http.patch<ITalukaMaster>(`${this.resourceUrl}/${this.getTalukaMasterIdentifier(talukaMaster)}`, talukaMaster, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITalukaMaster>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITalukaMaster[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTalukaMasterIdentifier(talukaMaster: Pick<ITalukaMaster, 'id'>): number {
    return talukaMaster.id;
  }

  compareTalukaMaster(o1: Pick<ITalukaMaster, 'id'> | null, o2: Pick<ITalukaMaster, 'id'> | null): boolean {
    return o1 && o2 ? this.getTalukaMasterIdentifier(o1) === this.getTalukaMasterIdentifier(o2) : o1 === o2;
  }

  addTalukaMasterToCollectionIfMissing<Type extends Pick<ITalukaMaster, 'id'>>(
    talukaMasterCollection: Type[],
    ...talukaMastersToCheck: (Type | null | undefined)[]
  ): Type[] {
    const talukaMasters: Type[] = talukaMastersToCheck.filter(isPresent);
    if (talukaMasters.length > 0) {
      const talukaMasterCollectionIdentifiers = talukaMasterCollection.map(
        talukaMasterItem => this.getTalukaMasterIdentifier(talukaMasterItem)!
      );
      const talukaMastersToAdd = talukaMasters.filter(talukaMasterItem => {
        const talukaMasterIdentifier = this.getTalukaMasterIdentifier(talukaMasterItem);
        if (talukaMasterCollectionIdentifiers.includes(talukaMasterIdentifier)) {
          return false;
        }
        talukaMasterCollectionIdentifiers.push(talukaMasterIdentifier);
        return true;
      });
      return [...talukaMastersToAdd, ...talukaMasterCollection];
    }
    return talukaMasterCollection;
  }
}
