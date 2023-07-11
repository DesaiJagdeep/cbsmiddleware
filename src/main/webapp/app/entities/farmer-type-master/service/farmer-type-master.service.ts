import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFarmerTypeMaster, NewFarmerTypeMaster } from '../farmer-type-master.model';

export type PartialUpdateFarmerTypeMaster = Partial<IFarmerTypeMaster> & Pick<IFarmerTypeMaster, 'id'>;

export type EntityResponseType = HttpResponse<IFarmerTypeMaster>;
export type EntityArrayResponseType = HttpResponse<IFarmerTypeMaster[]>;

@Injectable({ providedIn: 'root' })
export class FarmerTypeMasterService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/farmer-type-masters');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(farmerTypeMaster: NewFarmerTypeMaster): Observable<EntityResponseType> {
    return this.http.post<IFarmerTypeMaster>(this.resourceUrl, farmerTypeMaster, { observe: 'response' });
  }

  update(farmerTypeMaster: IFarmerTypeMaster): Observable<EntityResponseType> {
    return this.http.put<IFarmerTypeMaster>(
      `${this.resourceUrl}/${this.getFarmerTypeMasterIdentifier(farmerTypeMaster)}`,
      farmerTypeMaster,
      { observe: 'response' }
    );
  }

  partialUpdate(farmerTypeMaster: PartialUpdateFarmerTypeMaster): Observable<EntityResponseType> {
    return this.http.patch<IFarmerTypeMaster>(
      `${this.resourceUrl}/${this.getFarmerTypeMasterIdentifier(farmerTypeMaster)}`,
      farmerTypeMaster,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFarmerTypeMaster>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFarmerTypeMaster[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getFarmerTypeMasterIdentifier(farmerTypeMaster: Pick<IFarmerTypeMaster, 'id'>): number {
    return farmerTypeMaster.id;
  }

  compareFarmerTypeMaster(o1: Pick<IFarmerTypeMaster, 'id'> | null, o2: Pick<IFarmerTypeMaster, 'id'> | null): boolean {
    return o1 && o2 ? this.getFarmerTypeMasterIdentifier(o1) === this.getFarmerTypeMasterIdentifier(o2) : o1 === o2;
  }

  addFarmerTypeMasterToCollectionIfMissing<Type extends Pick<IFarmerTypeMaster, 'id'>>(
    farmerTypeMasterCollection: Type[],
    ...farmerTypeMastersToCheck: (Type | null | undefined)[]
  ): Type[] {
    const farmerTypeMasters: Type[] = farmerTypeMastersToCheck.filter(isPresent);
    if (farmerTypeMasters.length > 0) {
      const farmerTypeMasterCollectionIdentifiers = farmerTypeMasterCollection.map(
        farmerTypeMasterItem => this.getFarmerTypeMasterIdentifier(farmerTypeMasterItem)!
      );
      const farmerTypeMastersToAdd = farmerTypeMasters.filter(farmerTypeMasterItem => {
        const farmerTypeMasterIdentifier = this.getFarmerTypeMasterIdentifier(farmerTypeMasterItem);
        if (farmerTypeMasterCollectionIdentifiers.includes(farmerTypeMasterIdentifier)) {
          return false;
        }
        farmerTypeMasterCollectionIdentifiers.push(farmerTypeMasterIdentifier);
        return true;
      });
      return [...farmerTypeMastersToAdd, ...farmerTypeMasterCollection];
    }
    return farmerTypeMasterCollection;
  }
}
