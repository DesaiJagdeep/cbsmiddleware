import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IKmMaster, NewKmMaster } from '../km-master.model';

export type PartialUpdateKmMaster = Partial<IKmMaster> & Pick<IKmMaster, 'id'>;

export type EntityResponseType = HttpResponse<IKmMaster>;
export type EntityArrayResponseType = HttpResponse<IKmMaster[]>;

@Injectable({ providedIn: 'root' })
export class KmMasterService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/km-masters');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(kmMaster: NewKmMaster): Observable<EntityResponseType> {
    return this.http.post<IKmMaster>(this.resourceUrl, kmMaster, { observe: 'response' });
  }

  update(kmMaster: IKmMaster): Observable<EntityResponseType> {
    return this.http.put<IKmMaster>(`${this.resourceUrl}/${this.getKmMasterIdentifier(kmMaster)}`, kmMaster, { observe: 'response' });
  }

  partialUpdate(kmMaster: PartialUpdateKmMaster): Observable<EntityResponseType> {
    return this.http.patch<IKmMaster>(`${this.resourceUrl}/${this.getKmMasterIdentifier(kmMaster)}`, kmMaster, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IKmMaster>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IKmMaster[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getKmMasterIdentifier(kmMaster: Pick<IKmMaster, 'id'>): number {
    return kmMaster.id;
  }

  compareKmMaster(o1: Pick<IKmMaster, 'id'> | null, o2: Pick<IKmMaster, 'id'> | null): boolean {
    return o1 && o2 ? this.getKmMasterIdentifier(o1) === this.getKmMasterIdentifier(o2) : o1 === o2;
  }

  addKmMasterToCollectionIfMissing<Type extends Pick<IKmMaster, 'id'>>(
    kmMasterCollection: Type[],
    ...kmMastersToCheck: (Type | null | undefined)[]
  ): Type[] {
    const kmMasters: Type[] = kmMastersToCheck.filter(isPresent);
    if (kmMasters.length > 0) {
      const kmMasterCollectionIdentifiers = kmMasterCollection.map(kmMasterItem => this.getKmMasterIdentifier(kmMasterItem)!);
      const kmMastersToAdd = kmMasters.filter(kmMasterItem => {
        const kmMasterIdentifier = this.getKmMasterIdentifier(kmMasterItem);
        if (kmMasterCollectionIdentifiers.includes(kmMasterIdentifier)) {
          return false;
        }
        kmMasterCollectionIdentifiers.push(kmMasterIdentifier);
        return true;
      });
      return [...kmMastersToAdd, ...kmMasterCollection];
    }
    return kmMasterCollection;
  }
}
