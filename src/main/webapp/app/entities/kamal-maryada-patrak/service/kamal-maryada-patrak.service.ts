import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IKamalMaryadaPatrak, NewKamalMaryadaPatrak } from '../kamal-maryada-patrak.model';

export type PartialUpdateKamalMaryadaPatrak = Partial<IKamalMaryadaPatrak> & Pick<IKamalMaryadaPatrak, 'id'>;

type RestOf<T extends IKamalMaryadaPatrak | NewKamalMaryadaPatrak> = Omit<T, 'kmDate' | 'toKMDate' | 'meetingDate'> & {
  kmDate?: string | null;
  toKMDate?: string | null;
  meetingDate?: string | null;
};

export type RestKamalMaryadaPatrak = RestOf<IKamalMaryadaPatrak>;

export type NewRestKamalMaryadaPatrak = RestOf<NewKamalMaryadaPatrak>;

export type PartialUpdateRestKamalMaryadaPatrak = RestOf<PartialUpdateKamalMaryadaPatrak>;

export type EntityResponseType = HttpResponse<IKamalMaryadaPatrak>;
export type EntityArrayResponseType = HttpResponse<IKamalMaryadaPatrak[]>;

@Injectable({ providedIn: 'root' })
export class KamalMaryadaPatrakService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/kamal-maryada-patraks');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(kamalMaryadaPatrak: NewKamalMaryadaPatrak): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(kamalMaryadaPatrak);
    return this.http
      .post<RestKamalMaryadaPatrak>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(kamalMaryadaPatrak: IKamalMaryadaPatrak): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(kamalMaryadaPatrak);
    return this.http
      .put<RestKamalMaryadaPatrak>(`${this.resourceUrl}/${this.getKamalMaryadaPatrakIdentifier(kamalMaryadaPatrak)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(kamalMaryadaPatrak: PartialUpdateKamalMaryadaPatrak): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(kamalMaryadaPatrak);
    return this.http
      .patch<RestKamalMaryadaPatrak>(`${this.resourceUrl}/${this.getKamalMaryadaPatrakIdentifier(kamalMaryadaPatrak)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestKamalMaryadaPatrak>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestKamalMaryadaPatrak[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getKamalMaryadaPatrakIdentifier(kamalMaryadaPatrak: Pick<IKamalMaryadaPatrak, 'id'>): number {
    return kamalMaryadaPatrak.id;
  }

  compareKamalMaryadaPatrak(o1: Pick<IKamalMaryadaPatrak, 'id'> | null, o2: Pick<IKamalMaryadaPatrak, 'id'> | null): boolean {
    return o1 && o2 ? this.getKamalMaryadaPatrakIdentifier(o1) === this.getKamalMaryadaPatrakIdentifier(o2) : o1 === o2;
  }

  addKamalMaryadaPatrakToCollectionIfMissing<Type extends Pick<IKamalMaryadaPatrak, 'id'>>(
    kamalMaryadaPatrakCollection: Type[],
    ...kamalMaryadaPatraksToCheck: (Type | null | undefined)[]
  ): Type[] {
    const kamalMaryadaPatraks: Type[] = kamalMaryadaPatraksToCheck.filter(isPresent);
    if (kamalMaryadaPatraks.length > 0) {
      const kamalMaryadaPatrakCollectionIdentifiers = kamalMaryadaPatrakCollection.map(
        kamalMaryadaPatrakItem => this.getKamalMaryadaPatrakIdentifier(kamalMaryadaPatrakItem)!
      );
      const kamalMaryadaPatraksToAdd = kamalMaryadaPatraks.filter(kamalMaryadaPatrakItem => {
        const kamalMaryadaPatrakIdentifier = this.getKamalMaryadaPatrakIdentifier(kamalMaryadaPatrakItem);
        if (kamalMaryadaPatrakCollectionIdentifiers.includes(kamalMaryadaPatrakIdentifier)) {
          return false;
        }
        kamalMaryadaPatrakCollectionIdentifiers.push(kamalMaryadaPatrakIdentifier);
        return true;
      });
      return [...kamalMaryadaPatraksToAdd, ...kamalMaryadaPatrakCollection];
    }
    return kamalMaryadaPatrakCollection;
  }

  protected convertDateFromClient<T extends IKamalMaryadaPatrak | NewKamalMaryadaPatrak | PartialUpdateKamalMaryadaPatrak>(
    kamalMaryadaPatrak: T
  ): RestOf<T> {
    return {
      ...kamalMaryadaPatrak,
      kmDate: kamalMaryadaPatrak.kmDate?.format(DATE_FORMAT) ?? null,
      toKMDate: kamalMaryadaPatrak.toKMDate?.format(DATE_FORMAT) ?? null,
      meetingDate: kamalMaryadaPatrak.meetingDate?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restKamalMaryadaPatrak: RestKamalMaryadaPatrak): IKamalMaryadaPatrak {
    return {
      ...restKamalMaryadaPatrak,
      kmDate: restKamalMaryadaPatrak.kmDate ? dayjs(restKamalMaryadaPatrak.kmDate) : undefined,
      toKMDate: restKamalMaryadaPatrak.toKMDate ? dayjs(restKamalMaryadaPatrak.toKMDate) : undefined,
      meetingDate: restKamalMaryadaPatrak.meetingDate ? dayjs(restKamalMaryadaPatrak.meetingDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestKamalMaryadaPatrak>): HttpResponse<IKamalMaryadaPatrak> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestKamalMaryadaPatrak[]>): HttpResponse<IKamalMaryadaPatrak[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
