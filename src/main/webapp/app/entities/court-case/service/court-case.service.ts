import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICourtCase, NewCourtCase } from '../court-case.model';

export type PartialUpdateCourtCase = Partial<ICourtCase> & Pick<ICourtCase, 'id'>;

type RestOf<T extends ICourtCase | NewCourtCase> = Omit<T, 'loanDate' | 'dueDate' | 'firstNoticeDate' | 'secondNoticeDate'> & {
  loanDate?: string | null;
  dueDate?: string | null;
  firstNoticeDate?: string | null;
  secondNoticeDate?: string | null;
};

export type RestCourtCase = RestOf<ICourtCase>;

export type NewRestCourtCase = RestOf<NewCourtCase>;

export type PartialUpdateRestCourtCase = RestOf<PartialUpdateCourtCase>;

export type EntityResponseType = HttpResponse<ICourtCase>;
export type EntityArrayResponseType = HttpResponse<ICourtCase[]>;

@Injectable({ providedIn: 'root' })
export class CourtCaseService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/court-cases');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(courtCase: NewCourtCase): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(courtCase);
    return this.http
      .post<RestCourtCase>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(courtCase: ICourtCase): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(courtCase);
    return this.http
      .put<RestCourtCase>(`${this.resourceUrl}/${this.getCourtCaseIdentifier(courtCase)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(courtCase: PartialUpdateCourtCase): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(courtCase);
    return this.http
      .patch<RestCourtCase>(`${this.resourceUrl}/${this.getCourtCaseIdentifier(courtCase)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestCourtCase>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestCourtCase[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCourtCaseIdentifier(courtCase: Pick<ICourtCase, 'id'>): number {
    return courtCase.id;
  }

  compareCourtCase(o1: Pick<ICourtCase, 'id'> | null, o2: Pick<ICourtCase, 'id'> | null): boolean {
    return o1 && o2 ? this.getCourtCaseIdentifier(o1) === this.getCourtCaseIdentifier(o2) : o1 === o2;
  }

  addCourtCaseToCollectionIfMissing<Type extends Pick<ICourtCase, 'id'>>(
    courtCaseCollection: Type[],
    ...courtCasesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const courtCases: Type[] = courtCasesToCheck.filter(isPresent);
    if (courtCases.length > 0) {
      const courtCaseCollectionIdentifiers = courtCaseCollection.map(courtCaseItem => this.getCourtCaseIdentifier(courtCaseItem)!);
      const courtCasesToAdd = courtCases.filter(courtCaseItem => {
        const courtCaseIdentifier = this.getCourtCaseIdentifier(courtCaseItem);
        if (courtCaseCollectionIdentifiers.includes(courtCaseIdentifier)) {
          return false;
        }
        courtCaseCollectionIdentifiers.push(courtCaseIdentifier);
        return true;
      });
      return [...courtCasesToAdd, ...courtCaseCollection];
    }
    return courtCaseCollection;
  }

  protected convertDateFromClient<T extends ICourtCase | NewCourtCase | PartialUpdateCourtCase>(courtCase: T): RestOf<T> {
    return {
      ...courtCase,
      loanDate: courtCase.loanDate?.format(DATE_FORMAT) ?? null,
      dueDate: courtCase.dueDate?.format(DATE_FORMAT) ?? null,
      firstNoticeDate: courtCase.firstNoticeDate?.format(DATE_FORMAT) ?? null,
      secondNoticeDate: courtCase.secondNoticeDate?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restCourtCase: RestCourtCase): ICourtCase {
    return {
      ...restCourtCase,
      loanDate: restCourtCase.loanDate ? dayjs(restCourtCase.loanDate) : undefined,
      dueDate: restCourtCase.dueDate ? dayjs(restCourtCase.dueDate) : undefined,
      firstNoticeDate: restCourtCase.firstNoticeDate ? dayjs(restCourtCase.firstNoticeDate) : undefined,
      secondNoticeDate: restCourtCase.secondNoticeDate ? dayjs(restCourtCase.secondNoticeDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestCourtCase>): HttpResponse<ICourtCase> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestCourtCase[]>): HttpResponse<ICourtCase[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
