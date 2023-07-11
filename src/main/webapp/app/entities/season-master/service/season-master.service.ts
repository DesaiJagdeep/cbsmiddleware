import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISeasonMaster, NewSeasonMaster } from '../season-master.model';

export type PartialUpdateSeasonMaster = Partial<ISeasonMaster> & Pick<ISeasonMaster, 'id'>;

export type EntityResponseType = HttpResponse<ISeasonMaster>;
export type EntityArrayResponseType = HttpResponse<ISeasonMaster[]>;

@Injectable({ providedIn: 'root' })
export class SeasonMasterService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/season-masters');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(seasonMaster: NewSeasonMaster): Observable<EntityResponseType> {
    return this.http.post<ISeasonMaster>(this.resourceUrl, seasonMaster, { observe: 'response' });
  }

  update(seasonMaster: ISeasonMaster): Observable<EntityResponseType> {
    return this.http.put<ISeasonMaster>(`${this.resourceUrl}/${this.getSeasonMasterIdentifier(seasonMaster)}`, seasonMaster, {
      observe: 'response',
    });
  }

  partialUpdate(seasonMaster: PartialUpdateSeasonMaster): Observable<EntityResponseType> {
    return this.http.patch<ISeasonMaster>(`${this.resourceUrl}/${this.getSeasonMasterIdentifier(seasonMaster)}`, seasonMaster, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISeasonMaster>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISeasonMaster[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getSeasonMasterIdentifier(seasonMaster: Pick<ISeasonMaster, 'id'>): number {
    return seasonMaster.id;
  }

  compareSeasonMaster(o1: Pick<ISeasonMaster, 'id'> | null, o2: Pick<ISeasonMaster, 'id'> | null): boolean {
    return o1 && o2 ? this.getSeasonMasterIdentifier(o1) === this.getSeasonMasterIdentifier(o2) : o1 === o2;
  }

  addSeasonMasterToCollectionIfMissing<Type extends Pick<ISeasonMaster, 'id'>>(
    seasonMasterCollection: Type[],
    ...seasonMastersToCheck: (Type | null | undefined)[]
  ): Type[] {
    const seasonMasters: Type[] = seasonMastersToCheck.filter(isPresent);
    if (seasonMasters.length > 0) {
      const seasonMasterCollectionIdentifiers = seasonMasterCollection.map(
        seasonMasterItem => this.getSeasonMasterIdentifier(seasonMasterItem)!
      );
      const seasonMastersToAdd = seasonMasters.filter(seasonMasterItem => {
        const seasonMasterIdentifier = this.getSeasonMasterIdentifier(seasonMasterItem);
        if (seasonMasterCollectionIdentifiers.includes(seasonMasterIdentifier)) {
          return false;
        }
        seasonMasterCollectionIdentifiers.push(seasonMasterIdentifier);
        return true;
      });
      return [...seasonMastersToAdd, ...seasonMasterCollection];
    }
    return seasonMasterCollection;
  }
}
