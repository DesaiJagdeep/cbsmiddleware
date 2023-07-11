import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDistrictMaster, NewDistrictMaster } from '../district-master.model';

export type PartialUpdateDistrictMaster = Partial<IDistrictMaster> & Pick<IDistrictMaster, 'id'>;

export type EntityResponseType = HttpResponse<IDistrictMaster>;
export type EntityArrayResponseType = HttpResponse<IDistrictMaster[]>;

@Injectable({ providedIn: 'root' })
export class DistrictMasterService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/district-masters');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(districtMaster: NewDistrictMaster): Observable<EntityResponseType> {
    return this.http.post<IDistrictMaster>(this.resourceUrl, districtMaster, { observe: 'response' });
  }

  update(districtMaster: IDistrictMaster): Observable<EntityResponseType> {
    return this.http.put<IDistrictMaster>(`${this.resourceUrl}/${this.getDistrictMasterIdentifier(districtMaster)}`, districtMaster, {
      observe: 'response',
    });
  }

  partialUpdate(districtMaster: PartialUpdateDistrictMaster): Observable<EntityResponseType> {
    return this.http.patch<IDistrictMaster>(`${this.resourceUrl}/${this.getDistrictMasterIdentifier(districtMaster)}`, districtMaster, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDistrictMaster>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDistrictMaster[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDistrictMasterIdentifier(districtMaster: Pick<IDistrictMaster, 'id'>): number {
    return districtMaster.id;
  }

  compareDistrictMaster(o1: Pick<IDistrictMaster, 'id'> | null, o2: Pick<IDistrictMaster, 'id'> | null): boolean {
    return o1 && o2 ? this.getDistrictMasterIdentifier(o1) === this.getDistrictMasterIdentifier(o2) : o1 === o2;
  }

  addDistrictMasterToCollectionIfMissing<Type extends Pick<IDistrictMaster, 'id'>>(
    districtMasterCollection: Type[],
    ...districtMastersToCheck: (Type | null | undefined)[]
  ): Type[] {
    const districtMasters: Type[] = districtMastersToCheck.filter(isPresent);
    if (districtMasters.length > 0) {
      const districtMasterCollectionIdentifiers = districtMasterCollection.map(
        districtMasterItem => this.getDistrictMasterIdentifier(districtMasterItem)!
      );
      const districtMastersToAdd = districtMasters.filter(districtMasterItem => {
        const districtMasterIdentifier = this.getDistrictMasterIdentifier(districtMasterItem);
        if (districtMasterCollectionIdentifiers.includes(districtMasterIdentifier)) {
          return false;
        }
        districtMasterCollectionIdentifiers.push(districtMasterIdentifier);
        return true;
      });
      return [...districtMastersToAdd, ...districtMasterCollection];
    }
    return districtMasterCollection;
  }
}
