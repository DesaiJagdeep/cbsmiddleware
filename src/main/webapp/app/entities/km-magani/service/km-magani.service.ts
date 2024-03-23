import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IKmMagani, NewKmMagani } from '../km-magani.model';

export type PartialUpdateKmMagani = Partial<IKmMagani> & Pick<IKmMagani, 'id'>;

type RestOf<T extends IKmMagani | NewKmMagani> = Omit<T, 'kmDate' | 'maganiDate'> & {
  kmDate?: string | null;
  maganiDate?: string | null;
};

export type RestKmMagani = RestOf<IKmMagani>;

export type NewRestKmMagani = RestOf<NewKmMagani>;

export type PartialUpdateRestKmMagani = RestOf<PartialUpdateKmMagani>;

export type EntityResponseType = HttpResponse<IKmMagani>;
export type EntityArrayResponseType = HttpResponse<IKmMagani[]>;

@Injectable({ providedIn: 'root' })
export class KmMaganiService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/km-maganis');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(kmMagani: NewKmMagani): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(kmMagani);
    return this.http
      .post<RestKmMagani>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(kmMagani: IKmMagani): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(kmMagani);
    return this.http
      .put<RestKmMagani>(`${this.resourceUrl}/${this.getKmMaganiIdentifier(kmMagani)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(kmMagani: PartialUpdateKmMagani): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(kmMagani);
    return this.http
      .patch<RestKmMagani>(`${this.resourceUrl}/${this.getKmMaganiIdentifier(kmMagani)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestKmMagani>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestKmMagani[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getKmMaganiIdentifier(kmMagani: Pick<IKmMagani, 'id'>): number {
    return kmMagani.id;
  }

  compareKmMagani(o1: Pick<IKmMagani, 'id'> | null, o2: Pick<IKmMagani, 'id'> | null): boolean {
    return o1 && o2 ? this.getKmMaganiIdentifier(o1) === this.getKmMaganiIdentifier(o2) : o1 === o2;
  }

  addKmMaganiToCollectionIfMissing<Type extends Pick<IKmMagani, 'id'>>(
    kmMaganiCollection: Type[],
    ...kmMaganisToCheck: (Type | null | undefined)[]
  ): Type[] {
    const kmMaganis: Type[] = kmMaganisToCheck.filter(isPresent);
    if (kmMaganis.length > 0) {
      const kmMaganiCollectionIdentifiers = kmMaganiCollection.map(kmMaganiItem => this.getKmMaganiIdentifier(kmMaganiItem)!);
      const kmMaganisToAdd = kmMaganis.filter(kmMaganiItem => {
        const kmMaganiIdentifier = this.getKmMaganiIdentifier(kmMaganiItem);
        if (kmMaganiCollectionIdentifiers.includes(kmMaganiIdentifier)) {
          return false;
        }
        kmMaganiCollectionIdentifiers.push(kmMaganiIdentifier);
        return true;
      });
      return [...kmMaganisToAdd, ...kmMaganiCollection];
    }
    return kmMaganiCollection;
  }

  protected convertDateFromClient<T extends IKmMagani | NewKmMagani | PartialUpdateKmMagani>(kmMagani: T): RestOf<T> {
    return {
      ...kmMagani,
      kmDate: kmMagani.kmDate?.toJSON() ?? null,
      maganiDate: kmMagani.maganiDate?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restKmMagani: RestKmMagani): IKmMagani {
    return {
      ...restKmMagani,
      kmDate: restKmMagani.kmDate ? dayjs(restKmMagani.kmDate) : undefined,
      maganiDate: restKmMagani.maganiDate ? dayjs(restKmMagani.maganiDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestKmMagani>): HttpResponse<IKmMagani> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestKmMagani[]>): HttpResponse<IKmMagani[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
