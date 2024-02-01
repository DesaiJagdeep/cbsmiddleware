import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IKamalCrop, NewKamalCrop } from '../kamal-crop.model';

export type PartialUpdateKamalCrop = Partial<IKamalCrop> & Pick<IKamalCrop, 'id'>;

export type EntityResponseType = HttpResponse<IKamalCrop>;
export type EntityArrayResponseType = HttpResponse<IKamalCrop[]>;

@Injectable({ providedIn: 'root' })
export class KamalCropService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/kamal-crops');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(kamalCrop: NewKamalCrop): Observable<EntityResponseType> {
    return this.http.post<IKamalCrop>(this.resourceUrl, kamalCrop, { observe: 'response' });
  }

  update(kamalCrop: IKamalCrop): Observable<EntityResponseType> {
    return this.http.put<IKamalCrop>(`${this.resourceUrl}/${this.getKamalCropIdentifier(kamalCrop)}`, kamalCrop, { observe: 'response' });
  }

  partialUpdate(kamalCrop: PartialUpdateKamalCrop): Observable<EntityResponseType> {
    return this.http.patch<IKamalCrop>(`${this.resourceUrl}/${this.getKamalCropIdentifier(kamalCrop)}`, kamalCrop, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IKamalCrop>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IKamalCrop[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getKamalCropIdentifier(kamalCrop: Pick<IKamalCrop, 'id'>): number {
    return kamalCrop.id;
  }

  compareKamalCrop(o1: Pick<IKamalCrop, 'id'> | null, o2: Pick<IKamalCrop, 'id'> | null): boolean {
    return o1 && o2 ? this.getKamalCropIdentifier(o1) === this.getKamalCropIdentifier(o2) : o1 === o2;
  }

  addKamalCropToCollectionIfMissing<Type extends Pick<IKamalCrop, 'id'>>(
    kamalCropCollection: Type[],
    ...kamalCropsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const kamalCrops: Type[] = kamalCropsToCheck.filter(isPresent);
    if (kamalCrops.length > 0) {
      const kamalCropCollectionIdentifiers = kamalCropCollection.map(kamalCropItem => this.getKamalCropIdentifier(kamalCropItem)!);
      const kamalCropsToAdd = kamalCrops.filter(kamalCropItem => {
        const kamalCropIdentifier = this.getKamalCropIdentifier(kamalCropItem);
        if (kamalCropCollectionIdentifiers.includes(kamalCropIdentifier)) {
          return false;
        }
        kamalCropCollectionIdentifiers.push(kamalCropIdentifier);
        return true;
      });
      return [...kamalCropsToAdd, ...kamalCropCollection];
    }
    return kamalCropCollection;
  }
}
