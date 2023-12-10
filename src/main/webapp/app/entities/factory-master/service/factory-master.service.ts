import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFactoryMaster, NewFactoryMaster } from '../factory-master.model';

export type PartialUpdateFactoryMaster = Partial<IFactoryMaster> & Pick<IFactoryMaster, 'id'>;

export type EntityResponseType = HttpResponse<IFactoryMaster>;
export type EntityArrayResponseType = HttpResponse<IFactoryMaster[]>;

@Injectable({ providedIn: 'root' })
export class FactoryMasterService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/factory-masters');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(factoryMaster: NewFactoryMaster): Observable<EntityResponseType> {
    return this.http.post<IFactoryMaster>(this.resourceUrl, factoryMaster, { observe: 'response' });
  }

  update(factoryMaster: IFactoryMaster): Observable<EntityResponseType> {
    return this.http.put<IFactoryMaster>(`${this.resourceUrl}/${this.getFactoryMasterIdentifier(factoryMaster)}`, factoryMaster, {
      observe: 'response',
    });
  }

  partialUpdate(factoryMaster: PartialUpdateFactoryMaster): Observable<EntityResponseType> {
    return this.http.patch<IFactoryMaster>(`${this.resourceUrl}/${this.getFactoryMasterIdentifier(factoryMaster)}`, factoryMaster, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFactoryMaster>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFactoryMaster[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getFactoryMasterIdentifier(factoryMaster: Pick<IFactoryMaster, 'id'>): number {
    return factoryMaster.id;
  }

  compareFactoryMaster(o1: Pick<IFactoryMaster, 'id'> | null, o2: Pick<IFactoryMaster, 'id'> | null): boolean {
    return o1 && o2 ? this.getFactoryMasterIdentifier(o1) === this.getFactoryMasterIdentifier(o2) : o1 === o2;
  }

  addFactoryMasterToCollectionIfMissing<Type extends Pick<IFactoryMaster, 'id'>>(
    factoryMasterCollection: Type[],
    ...factoryMastersToCheck: (Type | null | undefined)[]
  ): Type[] {
    const factoryMasters: Type[] = factoryMastersToCheck.filter(isPresent);
    if (factoryMasters.length > 0) {
      const factoryMasterCollectionIdentifiers = factoryMasterCollection.map(
        factoryMasterItem => this.getFactoryMasterIdentifier(factoryMasterItem)!,
      );
      const factoryMastersToAdd = factoryMasters.filter(factoryMasterItem => {
        const factoryMasterIdentifier = this.getFactoryMasterIdentifier(factoryMasterItem);
        if (factoryMasterCollectionIdentifiers.includes(factoryMasterIdentifier)) {
          return false;
        }
        factoryMasterCollectionIdentifiers.push(factoryMasterIdentifier);
        return true;
      });
      return [...factoryMastersToAdd, ...factoryMasterCollection];
    }
    return factoryMasterCollection;
  }
}
