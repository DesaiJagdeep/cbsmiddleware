import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IKamalSociety, NewKamalSociety } from '../kamal-society.model';

export type PartialUpdateKamalSociety = Partial<IKamalSociety> & Pick<IKamalSociety, 'id'>;

type RestOf<T extends IKamalSociety | NewKamalSociety> = Omit<T, 'zindagiDate'> & {
  zindagiDate?: string | null;
};

export type RestKamalSociety = RestOf<IKamalSociety>;

export type NewRestKamalSociety = RestOf<NewKamalSociety>;

export type PartialUpdateRestKamalSociety = RestOf<PartialUpdateKamalSociety>;

export type EntityResponseType = HttpResponse<IKamalSociety>;
export type EntityArrayResponseType = HttpResponse<IKamalSociety[]>;

@Injectable({ providedIn: 'root' })
export class KamalSocietyService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/kamal-societies');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(kamalSociety: NewKamalSociety): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(kamalSociety);
    return this.http
      .post<RestKamalSociety>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(kamalSociety: IKamalSociety): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(kamalSociety);
    return this.http
      .put<RestKamalSociety>(`${this.resourceUrl}/${this.getKamalSocietyIdentifier(kamalSociety)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(kamalSociety: PartialUpdateKamalSociety): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(kamalSociety);
    return this.http
      .patch<RestKamalSociety>(`${this.resourceUrl}/${this.getKamalSocietyIdentifier(kamalSociety)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestKamalSociety>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestKamalSociety[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getKamalSocietyIdentifier(kamalSociety: Pick<IKamalSociety, 'id'>): number {
    return kamalSociety.id;
  }

  compareKamalSociety(o1: Pick<IKamalSociety, 'id'> | null, o2: Pick<IKamalSociety, 'id'> | null): boolean {
    return o1 && o2 ? this.getKamalSocietyIdentifier(o1) === this.getKamalSocietyIdentifier(o2) : o1 === o2;
  }

  addKamalSocietyToCollectionIfMissing<Type extends Pick<IKamalSociety, 'id'>>(
    kamalSocietyCollection: Type[],
    ...kamalSocietiesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const kamalSocieties: Type[] = kamalSocietiesToCheck.filter(isPresent);
    if (kamalSocieties.length > 0) {
      const kamalSocietyCollectionIdentifiers = kamalSocietyCollection.map(
        kamalSocietyItem => this.getKamalSocietyIdentifier(kamalSocietyItem)!
      );
      const kamalSocietiesToAdd = kamalSocieties.filter(kamalSocietyItem => {
        const kamalSocietyIdentifier = this.getKamalSocietyIdentifier(kamalSocietyItem);
        if (kamalSocietyCollectionIdentifiers.includes(kamalSocietyIdentifier)) {
          return false;
        }
        kamalSocietyCollectionIdentifiers.push(kamalSocietyIdentifier);
        return true;
      });
      return [...kamalSocietiesToAdd, ...kamalSocietyCollection];
    }
    return kamalSocietyCollection;
  }

  protected convertDateFromClient<T extends IKamalSociety | NewKamalSociety | PartialUpdateKamalSociety>(kamalSociety: T): RestOf<T> {
    return {
      ...kamalSociety,
      zindagiDate: kamalSociety.zindagiDate?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restKamalSociety: RestKamalSociety): IKamalSociety {
    return {
      ...restKamalSociety,
      zindagiDate: restKamalSociety.zindagiDate ? dayjs(restKamalSociety.zindagiDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestKamalSociety>): HttpResponse<IKamalSociety> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestKamalSociety[]>): HttpResponse<IKamalSociety[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
