import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IKmCrops, NewKmCrops } from '../km-crops.model';

export type PartialUpdateKmCrops = Partial<IKmCrops> & Pick<IKmCrops, 'id'>;

export type EntityResponseType = HttpResponse<IKmCrops>;
export type EntityArrayResponseType = HttpResponse<IKmCrops[]>;

@Injectable({ providedIn: 'root' })
export class KmCropsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/km-crops');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(kmCrops: NewKmCrops): Observable<EntityResponseType> {
    return this.http.post<IKmCrops>(this.resourceUrl, kmCrops, { observe: 'response' });
  }

  update(kmCrops: IKmCrops): Observable<EntityResponseType> {
    return this.http.put<IKmCrops>(`${this.resourceUrl}/${this.getKmCropsIdentifier(kmCrops)}`, kmCrops, { observe: 'response' });
  }

  partialUpdate(kmCrops: PartialUpdateKmCrops): Observable<EntityResponseType> {
    return this.http.patch<IKmCrops>(`${this.resourceUrl}/${this.getKmCropsIdentifier(kmCrops)}`, kmCrops, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IKmCrops>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IKmCrops[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getKmCropsIdentifier(kmCrops: Pick<IKmCrops, 'id'>): number {
    return kmCrops.id;
  }

  compareKmCrops(o1: Pick<IKmCrops, 'id'> | null, o2: Pick<IKmCrops, 'id'> | null): boolean {
    return o1 && o2 ? this.getKmCropsIdentifier(o1) === this.getKmCropsIdentifier(o2) : o1 === o2;
  }

  addKmCropsToCollectionIfMissing<Type extends Pick<IKmCrops, 'id'>>(
    kmCropsCollection: Type[],
    ...kmCropsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const kmCrops: Type[] = kmCropsToCheck.filter(isPresent);
    if (kmCrops.length > 0) {
      const kmCropsCollectionIdentifiers = kmCropsCollection.map(kmCropsItem => this.getKmCropsIdentifier(kmCropsItem)!);
      const kmCropsToAdd = kmCrops.filter(kmCropsItem => {
        const kmCropsIdentifier = this.getKmCropsIdentifier(kmCropsItem);
        if (kmCropsCollectionIdentifiers.includes(kmCropsIdentifier)) {
          return false;
        }
        kmCropsCollectionIdentifiers.push(kmCropsIdentifier);
        return true;
      });
      return [...kmCropsToAdd, ...kmCropsCollection];
    }
    return kmCropsCollection;
  }
}
