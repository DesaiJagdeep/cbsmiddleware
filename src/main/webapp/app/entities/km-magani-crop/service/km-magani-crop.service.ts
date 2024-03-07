import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IKmMaganiCrop, NewKmMaganiCrop } from '../km-magani-crop.model';

export type PartialUpdateKmMaganiCrop = Partial<IKmMaganiCrop> & Pick<IKmMaganiCrop, 'id'>;

export type EntityResponseType = HttpResponse<IKmMaganiCrop>;
export type EntityArrayResponseType = HttpResponse<IKmMaganiCrop[]>;

@Injectable({ providedIn: 'root' })
export class KmMaganiCropService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/km-magani-crops');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(kmMaganiCrop: NewKmMaganiCrop): Observable<EntityResponseType> {
    return this.http.post<IKmMaganiCrop>(this.resourceUrl, kmMaganiCrop, { observe: 'response' });
  }

  update(kmMaganiCrop: IKmMaganiCrop): Observable<EntityResponseType> {
    return this.http.put<IKmMaganiCrop>(`${this.resourceUrl}/${this.getKmMaganiCropIdentifier(kmMaganiCrop)}`, kmMaganiCrop, {
      observe: 'response',
    });
  }

  partialUpdate(kmMaganiCrop: PartialUpdateKmMaganiCrop): Observable<EntityResponseType> {
    return this.http.patch<IKmMaganiCrop>(`${this.resourceUrl}/${this.getKmMaganiCropIdentifier(kmMaganiCrop)}`, kmMaganiCrop, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IKmMaganiCrop>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IKmMaganiCrop[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getKmMaganiCropIdentifier(kmMaganiCrop: Pick<IKmMaganiCrop, 'id'>): number {
    return kmMaganiCrop.id;
  }

  compareKmMaganiCrop(o1: Pick<IKmMaganiCrop, 'id'> | null, o2: Pick<IKmMaganiCrop, 'id'> | null): boolean {
    return o1 && o2 ? this.getKmMaganiCropIdentifier(o1) === this.getKmMaganiCropIdentifier(o2) : o1 === o2;
  }

  addKmMaganiCropToCollectionIfMissing<Type extends Pick<IKmMaganiCrop, 'id'>>(
    kmMaganiCropCollection: Type[],
    ...kmMaganiCropsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const kmMaganiCrops: Type[] = kmMaganiCropsToCheck.filter(isPresent);
    if (kmMaganiCrops.length > 0) {
      const kmMaganiCropCollectionIdentifiers = kmMaganiCropCollection.map(
        kmMaganiCropItem => this.getKmMaganiCropIdentifier(kmMaganiCropItem)!
      );
      const kmMaganiCropsToAdd = kmMaganiCrops.filter(kmMaganiCropItem => {
        const kmMaganiCropIdentifier = this.getKmMaganiCropIdentifier(kmMaganiCropItem);
        if (kmMaganiCropCollectionIdentifiers.includes(kmMaganiCropIdentifier)) {
          return false;
        }
        kmMaganiCropCollectionIdentifiers.push(kmMaganiCropIdentifier);
        return true;
      });
      return [...kmMaganiCropsToAdd, ...kmMaganiCropCollection];
    }
    return kmMaganiCropCollection;
  }
}
