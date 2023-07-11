import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IOccupationMaster, NewOccupationMaster } from '../occupation-master.model';

export type PartialUpdateOccupationMaster = Partial<IOccupationMaster> & Pick<IOccupationMaster, 'id'>;

export type EntityResponseType = HttpResponse<IOccupationMaster>;
export type EntityArrayResponseType = HttpResponse<IOccupationMaster[]>;

@Injectable({ providedIn: 'root' })
export class OccupationMasterService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/occupation-masters');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(occupationMaster: NewOccupationMaster): Observable<EntityResponseType> {
    return this.http.post<IOccupationMaster>(this.resourceUrl, occupationMaster, { observe: 'response' });
  }

  update(occupationMaster: IOccupationMaster): Observable<EntityResponseType> {
    return this.http.put<IOccupationMaster>(
      `${this.resourceUrl}/${this.getOccupationMasterIdentifier(occupationMaster)}`,
      occupationMaster,
      { observe: 'response' }
    );
  }

  partialUpdate(occupationMaster: PartialUpdateOccupationMaster): Observable<EntityResponseType> {
    return this.http.patch<IOccupationMaster>(
      `${this.resourceUrl}/${this.getOccupationMasterIdentifier(occupationMaster)}`,
      occupationMaster,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IOccupationMaster>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOccupationMaster[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getOccupationMasterIdentifier(occupationMaster: Pick<IOccupationMaster, 'id'>): number {
    return occupationMaster.id;
  }

  compareOccupationMaster(o1: Pick<IOccupationMaster, 'id'> | null, o2: Pick<IOccupationMaster, 'id'> | null): boolean {
    return o1 && o2 ? this.getOccupationMasterIdentifier(o1) === this.getOccupationMasterIdentifier(o2) : o1 === o2;
  }

  addOccupationMasterToCollectionIfMissing<Type extends Pick<IOccupationMaster, 'id'>>(
    occupationMasterCollection: Type[],
    ...occupationMastersToCheck: (Type | null | undefined)[]
  ): Type[] {
    const occupationMasters: Type[] = occupationMastersToCheck.filter(isPresent);
    if (occupationMasters.length > 0) {
      const occupationMasterCollectionIdentifiers = occupationMasterCollection.map(
        occupationMasterItem => this.getOccupationMasterIdentifier(occupationMasterItem)!
      );
      const occupationMastersToAdd = occupationMasters.filter(occupationMasterItem => {
        const occupationMasterIdentifier = this.getOccupationMasterIdentifier(occupationMasterItem);
        if (occupationMasterCollectionIdentifiers.includes(occupationMasterIdentifier)) {
          return false;
        }
        occupationMasterCollectionIdentifiers.push(occupationMasterIdentifier);
        return true;
      });
      return [...occupationMastersToAdd, ...occupationMasterCollection];
    }
    return occupationMasterCollection;
  }
}
