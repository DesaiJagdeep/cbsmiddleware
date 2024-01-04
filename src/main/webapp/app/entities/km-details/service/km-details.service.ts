import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IKmDetails, NewKmDetails } from '../km-details.model';

export type PartialUpdateKmDetails = Partial<IKmDetails> & Pick<IKmDetails, 'id'>;

type RestOf<T extends IKmDetails | NewKmDetails> = Omit<
  T,
  'dueDate' | 'kmDate' | 'kmFromDate' | 'kmToDate' | 'eAgreementDate' | 'bojaDate'
> & {
  dueDate?: string | null;
  kmDate?: string | null;
  kmFromDate?: string | null;
  kmToDate?: string | null;
  eAgreementDate?: string | null;
  bojaDate?: string | null;
};

export type RestKmDetails = RestOf<IKmDetails>;

export type NewRestKmDetails = RestOf<NewKmDetails>;

export type PartialUpdateRestKmDetails = RestOf<PartialUpdateKmDetails>;

export type EntityResponseType = HttpResponse<IKmDetails>;
export type EntityArrayResponseType = HttpResponse<IKmDetails[]>;

@Injectable({ providedIn: 'root' })
export class KmDetailsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/km-details');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(kmDetails: NewKmDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(kmDetails);
    return this.http
      .post<RestKmDetails>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(kmDetails: IKmDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(kmDetails);
    return this.http
      .put<RestKmDetails>(`${this.resourceUrl}/${this.getKmDetailsIdentifier(kmDetails)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(kmDetails: PartialUpdateKmDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(kmDetails);
    return this.http
      .patch<RestKmDetails>(`${this.resourceUrl}/${this.getKmDetailsIdentifier(kmDetails)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestKmDetails>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestKmDetails[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getKmDetailsIdentifier(kmDetails: Pick<IKmDetails, 'id'>): number {
    return kmDetails.id;
  }

  compareKmDetails(o1: Pick<IKmDetails, 'id'> | null, o2: Pick<IKmDetails, 'id'> | null): boolean {
    return o1 && o2 ? this.getKmDetailsIdentifier(o1) === this.getKmDetailsIdentifier(o2) : o1 === o2;
  }

  addKmDetailsToCollectionIfMissing<Type extends Pick<IKmDetails, 'id'>>(
    kmDetailsCollection: Type[],
    ...kmDetailsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const kmDetails: Type[] = kmDetailsToCheck.filter(isPresent);
    if (kmDetails.length > 0) {
      const kmDetailsCollectionIdentifiers = kmDetailsCollection.map(kmDetailsItem => this.getKmDetailsIdentifier(kmDetailsItem)!);
      const kmDetailsToAdd = kmDetails.filter(kmDetailsItem => {
        const kmDetailsIdentifier = this.getKmDetailsIdentifier(kmDetailsItem);
        if (kmDetailsCollectionIdentifiers.includes(kmDetailsIdentifier)) {
          return false;
        }
        kmDetailsCollectionIdentifiers.push(kmDetailsIdentifier);
        return true;
      });
      return [...kmDetailsToAdd, ...kmDetailsCollection];
    }
    return kmDetailsCollection;
  }

  protected convertDateFromClient<T extends IKmDetails | NewKmDetails | PartialUpdateKmDetails>(kmDetails: T): RestOf<T> {
    return {
      ...kmDetails,
      dueDate: kmDetails.dueDate?.toJSON() ?? null,
      kmDate: kmDetails.kmDate?.toJSON() ?? null,
      kmFromDate: kmDetails.kmFromDate?.toJSON() ?? null,
      kmToDate: kmDetails.kmToDate?.toJSON() ?? null,
      eAgreementDate: kmDetails.eAgreementDate?.toJSON() ?? null,
      bojaDate: kmDetails.bojaDate?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restKmDetails: RestKmDetails): IKmDetails {
    return {
      ...restKmDetails,
      dueDate: restKmDetails.dueDate ? dayjs(restKmDetails.dueDate) : undefined,
      kmDate: restKmDetails.kmDate ? dayjs(restKmDetails.kmDate) : undefined,
      kmFromDate: restKmDetails.kmFromDate ? dayjs(restKmDetails.kmFromDate) : undefined,
      kmToDate: restKmDetails.kmToDate ? dayjs(restKmDetails.kmToDate) : undefined,
      eAgreementDate: restKmDetails.eAgreementDate ? dayjs(restKmDetails.eAgreementDate) : undefined,
      bojaDate: restKmDetails.bojaDate ? dayjs(restKmDetails.bojaDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestKmDetails>): HttpResponse<IKmDetails> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestKmDetails[]>): HttpResponse<IKmDetails[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
