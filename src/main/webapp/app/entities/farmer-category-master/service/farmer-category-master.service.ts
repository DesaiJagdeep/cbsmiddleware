import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFarmerCategoryMaster, NewFarmerCategoryMaster } from '../farmer-category-master.model';

export type PartialUpdateFarmerCategoryMaster = Partial<IFarmerCategoryMaster> & Pick<IFarmerCategoryMaster, 'id'>;

export type EntityResponseType = HttpResponse<IFarmerCategoryMaster>;
export type EntityArrayResponseType = HttpResponse<IFarmerCategoryMaster[]>;

@Injectable({ providedIn: 'root' })
export class FarmerCategoryMasterService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/farmer-category-masters');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(farmerCategoryMaster: NewFarmerCategoryMaster): Observable<EntityResponseType> {
    return this.http.post<IFarmerCategoryMaster>(this.resourceUrl, farmerCategoryMaster, { observe: 'response' });
  }

  update(farmerCategoryMaster: IFarmerCategoryMaster): Observable<EntityResponseType> {
    return this.http.put<IFarmerCategoryMaster>(
      `${this.resourceUrl}/${this.getFarmerCategoryMasterIdentifier(farmerCategoryMaster)}`,
      farmerCategoryMaster,
      { observe: 'response' }
    );
  }

  partialUpdate(farmerCategoryMaster: PartialUpdateFarmerCategoryMaster): Observable<EntityResponseType> {
    return this.http.patch<IFarmerCategoryMaster>(
      `${this.resourceUrl}/${this.getFarmerCategoryMasterIdentifier(farmerCategoryMaster)}`,
      farmerCategoryMaster,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFarmerCategoryMaster>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFarmerCategoryMaster[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getFarmerCategoryMasterIdentifier(farmerCategoryMaster: Pick<IFarmerCategoryMaster, 'id'>): number {
    return farmerCategoryMaster.id;
  }

  compareFarmerCategoryMaster(o1: Pick<IFarmerCategoryMaster, 'id'> | null, o2: Pick<IFarmerCategoryMaster, 'id'> | null): boolean {
    return o1 && o2 ? this.getFarmerCategoryMasterIdentifier(o1) === this.getFarmerCategoryMasterIdentifier(o2) : o1 === o2;
  }

  addFarmerCategoryMasterToCollectionIfMissing<Type extends Pick<IFarmerCategoryMaster, 'id'>>(
    farmerCategoryMasterCollection: Type[],
    ...farmerCategoryMastersToCheck: (Type | null | undefined)[]
  ): Type[] {
    const farmerCategoryMasters: Type[] = farmerCategoryMastersToCheck.filter(isPresent);
    if (farmerCategoryMasters.length > 0) {
      const farmerCategoryMasterCollectionIdentifiers = farmerCategoryMasterCollection.map(
        farmerCategoryMasterItem => this.getFarmerCategoryMasterIdentifier(farmerCategoryMasterItem)!
      );
      const farmerCategoryMastersToAdd = farmerCategoryMasters.filter(farmerCategoryMasterItem => {
        const farmerCategoryMasterIdentifier = this.getFarmerCategoryMasterIdentifier(farmerCategoryMasterItem);
        if (farmerCategoryMasterCollectionIdentifiers.includes(farmerCategoryMasterIdentifier)) {
          return false;
        }
        farmerCategoryMasterCollectionIdentifiers.push(farmerCategoryMasterIdentifier);
        return true;
      });
      return [...farmerCategoryMastersToAdd, ...farmerCategoryMasterCollection];
    }
    return farmerCategoryMasterCollection;
  }
}
