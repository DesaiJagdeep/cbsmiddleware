import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IKamalCrop, NewKamalCrop } from '../kamal-crop.model';

export type PartialUpdateKamalCrop = Partial<IKamalCrop> & Pick<IKamalCrop, 'id'>;

type RestOf<T extends IKamalCrop | NewKamalCrop> = Omit<T, 'kmDate'> & {
  kmDate?: string | null;
};

export type RestKamalCrop = RestOf<IKamalCrop>;

export type NewRestKamalCrop = RestOf<NewKamalCrop>;

export type PartialUpdateRestKamalCrop = RestOf<PartialUpdateKamalCrop>;

export type EntityResponseType = HttpResponse<IKamalCrop>;
export type EntityArrayResponseType = HttpResponse<IKamalCrop[]>;

@Injectable({ providedIn: 'root' })
export class KamalCropService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/kamal-crops');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(kamalCrop: NewKamalCrop): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(kamalCrop);
    return this.http
      .post<RestKamalCrop>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(kamalCrop: IKamalCrop): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(kamalCrop);
    return this.http
      .put<RestKamalCrop>(`${this.resourceUrl}/${this.getKamalCropIdentifier(kamalCrop)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(kamalCrop: PartialUpdateKamalCrop): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(kamalCrop);
    return this.http
      .patch<RestKamalCrop>(`${this.resourceUrl}/${this.getKamalCropIdentifier(kamalCrop)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestKamalCrop>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestKamalCrop[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
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

  protected convertDateFromClient<T extends IKamalCrop | NewKamalCrop | PartialUpdateKamalCrop>(kamalCrop: T): RestOf<T> {
    return {
      ...kamalCrop,
      kmDate: kamalCrop.kmDate?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restKamalCrop: RestKamalCrop): IKamalCrop {
    return {
      ...restKamalCrop,
      kmDate: restKamalCrop.kmDate ? dayjs(restKamalCrop.kmDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestKamalCrop>): HttpResponse<IKamalCrop> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestKamalCrop[]>): HttpResponse<IKamalCrop[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
