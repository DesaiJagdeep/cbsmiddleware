import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IKmLoans, NewKmLoans } from '../km-loans.model';

export type PartialUpdateKmLoans = Partial<IKmLoans> & Pick<IKmLoans, 'id'>;

type RestOf<T extends IKmLoans | NewKmLoans> = Omit<T, 'loanDate' | 'dueDate'> & {
  loanDate?: string | null;
  dueDate?: string | null;
};

export type RestKmLoans = RestOf<IKmLoans>;

export type NewRestKmLoans = RestOf<NewKmLoans>;

export type PartialUpdateRestKmLoans = RestOf<PartialUpdateKmLoans>;

export type EntityResponseType = HttpResponse<IKmLoans>;
export type EntityArrayResponseType = HttpResponse<IKmLoans[]>;

@Injectable({ providedIn: 'root' })
export class KmLoansService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/km-loans');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(kmLoans: NewKmLoans): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(kmLoans);
    return this.http
      .post<RestKmLoans>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(kmLoans: IKmLoans): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(kmLoans);
    return this.http
      .put<RestKmLoans>(`${this.resourceUrl}/${this.getKmLoansIdentifier(kmLoans)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(kmLoans: PartialUpdateKmLoans): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(kmLoans);
    return this.http
      .patch<RestKmLoans>(`${this.resourceUrl}/${this.getKmLoansIdentifier(kmLoans)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestKmLoans>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestKmLoans[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getKmLoansIdentifier(kmLoans: Pick<IKmLoans, 'id'>): number {
    return kmLoans.id;
  }

  compareKmLoans(o1: Pick<IKmLoans, 'id'> | null, o2: Pick<IKmLoans, 'id'> | null): boolean {
    return o1 && o2 ? this.getKmLoansIdentifier(o1) === this.getKmLoansIdentifier(o2) : o1 === o2;
  }

  addKmLoansToCollectionIfMissing<Type extends Pick<IKmLoans, 'id'>>(
    kmLoansCollection: Type[],
    ...kmLoansToCheck: (Type | null | undefined)[]
  ): Type[] {
    const kmLoans: Type[] = kmLoansToCheck.filter(isPresent);
    if (kmLoans.length > 0) {
      const kmLoansCollectionIdentifiers = kmLoansCollection.map(kmLoansItem => this.getKmLoansIdentifier(kmLoansItem)!);
      const kmLoansToAdd = kmLoans.filter(kmLoansItem => {
        const kmLoansIdentifier = this.getKmLoansIdentifier(kmLoansItem);
        if (kmLoansCollectionIdentifiers.includes(kmLoansIdentifier)) {
          return false;
        }
        kmLoansCollectionIdentifiers.push(kmLoansIdentifier);
        return true;
      });
      return [...kmLoansToAdd, ...kmLoansCollection];
    }
    return kmLoansCollection;
  }

  protected convertDateFromClient<T extends IKmLoans | NewKmLoans | PartialUpdateKmLoans>(kmLoans: T): RestOf<T> {
    return {
      ...kmLoans,
      loanDate: kmLoans.loanDate?.toJSON() ?? null,
      dueDate: kmLoans.dueDate?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restKmLoans: RestKmLoans): IKmLoans {
    return {
      ...restKmLoans,
      loanDate: restKmLoans.loanDate ? dayjs(restKmLoans.loanDate) : undefined,
      dueDate: restKmLoans.dueDate ? dayjs(restKmLoans.dueDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestKmLoans>): HttpResponse<IKmLoans> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestKmLoans[]>): HttpResponse<IKmLoans[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
