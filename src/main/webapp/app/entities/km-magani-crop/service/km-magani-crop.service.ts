import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IKmMaganiCrop, NewKmMaganiCrop } from '../km-magani-crop.model';

export type PartialUpdateKmMaganiCrop = Partial<IKmMaganiCrop> & Pick<IKmMaganiCrop, 'id'>;

type RestOf<T extends IKmMaganiCrop | NewKmMaganiCrop> = Omit<T, 'cropDueDate' | 'cropVasuliPatraDate'> & {
  cropDueDate?: string | null;
  cropVasuliPatraDate?: string | null;
};

export type RestKmMaganiCrop = RestOf<IKmMaganiCrop>;

export type NewRestKmMaganiCrop = RestOf<NewKmMaganiCrop>;

export type PartialUpdateRestKmMaganiCrop = RestOf<PartialUpdateKmMaganiCrop>;

export type EntityResponseType = HttpResponse<IKmMaganiCrop>;
export type EntityArrayResponseType = HttpResponse<IKmMaganiCrop[]>;

@Injectable({ providedIn: 'root' })
export class KmMaganiCropService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/km-magani-crops');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(kmMaganiCrop: NewKmMaganiCrop): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(kmMaganiCrop);
    return this.http
      .post<RestKmMaganiCrop>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(kmMaganiCrop: IKmMaganiCrop): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(kmMaganiCrop);
    return this.http
      .put<RestKmMaganiCrop>(`${this.resourceUrl}/${this.getKmMaganiCropIdentifier(kmMaganiCrop)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(kmMaganiCrop: PartialUpdateKmMaganiCrop): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(kmMaganiCrop);
    return this.http
      .patch<RestKmMaganiCrop>(`${this.resourceUrl}/${this.getKmMaganiCropIdentifier(kmMaganiCrop)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestKmMaganiCrop>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestKmMaganiCrop[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
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

  protected convertDateFromClient<T extends IKmMaganiCrop | NewKmMaganiCrop | PartialUpdateKmMaganiCrop>(kmMaganiCrop: T): RestOf<T> {
    return {
      ...kmMaganiCrop,
      cropDueDate: kmMaganiCrop.cropDueDate?.toJSON() ?? null,
      cropVasuliPatraDate: kmMaganiCrop.cropVasuliPatraDate?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restKmMaganiCrop: RestKmMaganiCrop): IKmMaganiCrop {
    return {
      ...restKmMaganiCrop,
      cropDueDate: restKmMaganiCrop.cropDueDate ? dayjs(restKmMaganiCrop.cropDueDate) : undefined,
      cropVasuliPatraDate: restKmMaganiCrop.cropVasuliPatraDate ? dayjs(restKmMaganiCrop.cropVasuliPatraDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestKmMaganiCrop>): HttpResponse<IKmMaganiCrop> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestKmMaganiCrop[]>): HttpResponse<IKmMaganiCrop[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
