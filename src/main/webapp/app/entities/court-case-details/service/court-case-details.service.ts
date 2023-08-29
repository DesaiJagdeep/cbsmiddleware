import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICourtCaseDetails, NewCourtCaseDetails } from '../court-case-details.model';

export type PartialUpdateCourtCaseDetails = Partial<ICourtCaseDetails> & Pick<ICourtCaseDetails, 'id'>;

type RestOf<T extends ICourtCaseDetails | NewCourtCaseDetails> = Omit<
  T,
  'dinank' | 'caseDinank' | 'certificateDinnank' | 'dimmandDinnank' | 'japtiAadheshDinnank' | 'vikriAddheshDinnank'
> & {
  dinank?: string | null;
  caseDinank?: string | null;
  certificateDinnank?: string | null;
  dimmandDinnank?: string | null;
  japtiAadheshDinnank?: string | null;
  vikriAddheshDinnank?: string | null;
};

export type RestCourtCaseDetails = RestOf<ICourtCaseDetails>;

export type NewRestCourtCaseDetails = RestOf<NewCourtCaseDetails>;

export type PartialUpdateRestCourtCaseDetails = RestOf<PartialUpdateCourtCaseDetails>;

export type EntityResponseType = HttpResponse<ICourtCaseDetails>;
export type EntityArrayResponseType = HttpResponse<ICourtCaseDetails[]>;

@Injectable({ providedIn: 'root' })
export class CourtCaseDetailsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/court-case-details');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(courtCaseDetails: NewCourtCaseDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(courtCaseDetails);
    return this.http
      .post<RestCourtCaseDetails>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(courtCaseDetails: ICourtCaseDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(courtCaseDetails);
    return this.http
      .put<RestCourtCaseDetails>(`${this.resourceUrl}/${this.getCourtCaseDetailsIdentifier(courtCaseDetails)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(courtCaseDetails: PartialUpdateCourtCaseDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(courtCaseDetails);
    return this.http
      .patch<RestCourtCaseDetails>(`${this.resourceUrl}/${this.getCourtCaseDetailsIdentifier(courtCaseDetails)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestCourtCaseDetails>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestCourtCaseDetails[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCourtCaseDetailsIdentifier(courtCaseDetails: Pick<ICourtCaseDetails, 'id'>): number {
    return courtCaseDetails.id;
  }

  compareCourtCaseDetails(o1: Pick<ICourtCaseDetails, 'id'> | null, o2: Pick<ICourtCaseDetails, 'id'> | null): boolean {
    return o1 && o2 ? this.getCourtCaseDetailsIdentifier(o1) === this.getCourtCaseDetailsIdentifier(o2) : o1 === o2;
  }

  addCourtCaseDetailsToCollectionIfMissing<Type extends Pick<ICourtCaseDetails, 'id'>>(
    courtCaseDetailsCollection: Type[],
    ...courtCaseDetailsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const courtCaseDetails: Type[] = courtCaseDetailsToCheck.filter(isPresent);
    if (courtCaseDetails.length > 0) {
      const courtCaseDetailsCollectionIdentifiers = courtCaseDetailsCollection.map(
        courtCaseDetailsItem => this.getCourtCaseDetailsIdentifier(courtCaseDetailsItem)!
      );
      const courtCaseDetailsToAdd = courtCaseDetails.filter(courtCaseDetailsItem => {
        const courtCaseDetailsIdentifier = this.getCourtCaseDetailsIdentifier(courtCaseDetailsItem);
        if (courtCaseDetailsCollectionIdentifiers.includes(courtCaseDetailsIdentifier)) {
          return false;
        }
        courtCaseDetailsCollectionIdentifiers.push(courtCaseDetailsIdentifier);
        return true;
      });
      return [...courtCaseDetailsToAdd, ...courtCaseDetailsCollection];
    }
    return courtCaseDetailsCollection;
  }

  protected convertDateFromClient<T extends ICourtCaseDetails | NewCourtCaseDetails | PartialUpdateCourtCaseDetails>(
    courtCaseDetails: T
  ): RestOf<T> {
    return {
      ...courtCaseDetails,
      dinank: courtCaseDetails.dinank?.toJSON() ?? null,
      caseDinank: courtCaseDetails.caseDinank?.toJSON() ?? null,
      certificateDinnank: courtCaseDetails.certificateDinnank?.toJSON() ?? null,
      dimmandDinnank: courtCaseDetails.dimmandDinnank?.toJSON() ?? null,
      japtiAadheshDinnank: courtCaseDetails.japtiAadheshDinnank?.toJSON() ?? null,
      vikriAddheshDinnank: courtCaseDetails.vikriAddheshDinnank?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restCourtCaseDetails: RestCourtCaseDetails): ICourtCaseDetails {
    return {
      ...restCourtCaseDetails,
      dinank: restCourtCaseDetails.dinank ? dayjs(restCourtCaseDetails.dinank) : undefined,
      caseDinank: restCourtCaseDetails.caseDinank ? dayjs(restCourtCaseDetails.caseDinank) : undefined,
      certificateDinnank: restCourtCaseDetails.certificateDinnank ? dayjs(restCourtCaseDetails.certificateDinnank) : undefined,
      dimmandDinnank: restCourtCaseDetails.dimmandDinnank ? dayjs(restCourtCaseDetails.dimmandDinnank) : undefined,
      japtiAadheshDinnank: restCourtCaseDetails.japtiAadheshDinnank ? dayjs(restCourtCaseDetails.japtiAadheshDinnank) : undefined,
      vikriAddheshDinnank: restCourtCaseDetails.vikriAddheshDinnank ? dayjs(restCourtCaseDetails.vikriAddheshDinnank) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestCourtCaseDetails>): HttpResponse<ICourtCaseDetails> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestCourtCaseDetails[]>): HttpResponse<ICourtCaseDetails[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
