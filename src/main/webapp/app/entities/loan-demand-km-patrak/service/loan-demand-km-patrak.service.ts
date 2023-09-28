import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ILoanDemandKMPatrak, NewLoanDemandKMPatrak } from '../loan-demand-km-patrak.model';

export type PartialUpdateLoanDemandKMPatrak = Partial<ILoanDemandKMPatrak> & Pick<ILoanDemandKMPatrak, 'id'>;

type RestOf<T extends ILoanDemandKMPatrak | NewLoanDemandKMPatrak> = Omit<
  T,
  'date' | 'kmDate' | 'pendingDate' | 'depositeDate' | 'dueDateB' | 'vasulPatraRepaymentDateB'
> & {
  date?: string | null;
  kmDate?: string | null;
  pendingDate?: string | null;
  depositeDate?: string | null;
  dueDateB?: string | null;
  vasulPatraRepaymentDateB?: string | null;
};

export type RestLoanDemandKMPatrak = RestOf<ILoanDemandKMPatrak>;

export type NewRestLoanDemandKMPatrak = RestOf<NewLoanDemandKMPatrak>;

export type PartialUpdateRestLoanDemandKMPatrak = RestOf<PartialUpdateLoanDemandKMPatrak>;

export type EntityResponseType = HttpResponse<ILoanDemandKMPatrak>;
export type EntityArrayResponseType = HttpResponse<ILoanDemandKMPatrak[]>;

@Injectable({ providedIn: 'root' })
export class LoanDemandKMPatrakService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/loan-demand-km-patraks');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(loanDemandKMPatrak: NewLoanDemandKMPatrak): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(loanDemandKMPatrak);
    return this.http
      .post<RestLoanDemandKMPatrak>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(loanDemandKMPatrak: ILoanDemandKMPatrak): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(loanDemandKMPatrak);
    return this.http
      .put<RestLoanDemandKMPatrak>(`${this.resourceUrl}/${this.getLoanDemandKMPatrakIdentifier(loanDemandKMPatrak)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(loanDemandKMPatrak: PartialUpdateLoanDemandKMPatrak): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(loanDemandKMPatrak);
    return this.http
      .patch<RestLoanDemandKMPatrak>(`${this.resourceUrl}/${this.getLoanDemandKMPatrakIdentifier(loanDemandKMPatrak)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestLoanDemandKMPatrak>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestLoanDemandKMPatrak[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getLoanDemandKMPatrakIdentifier(loanDemandKMPatrak: Pick<ILoanDemandKMPatrak, 'id'>): number {
    return loanDemandKMPatrak.id;
  }

  compareLoanDemandKMPatrak(o1: Pick<ILoanDemandKMPatrak, 'id'> | null, o2: Pick<ILoanDemandKMPatrak, 'id'> | null): boolean {
    return o1 && o2 ? this.getLoanDemandKMPatrakIdentifier(o1) === this.getLoanDemandKMPatrakIdentifier(o2) : o1 === o2;
  }

  addLoanDemandKMPatrakToCollectionIfMissing<Type extends Pick<ILoanDemandKMPatrak, 'id'>>(
    loanDemandKMPatrakCollection: Type[],
    ...loanDemandKMPatraksToCheck: (Type | null | undefined)[]
  ): Type[] {
    const loanDemandKMPatraks: Type[] = loanDemandKMPatraksToCheck.filter(isPresent);
    if (loanDemandKMPatraks.length > 0) {
      const loanDemandKMPatrakCollectionIdentifiers = loanDemandKMPatrakCollection.map(
        loanDemandKMPatrakItem => this.getLoanDemandKMPatrakIdentifier(loanDemandKMPatrakItem)!
      );
      const loanDemandKMPatraksToAdd = loanDemandKMPatraks.filter(loanDemandKMPatrakItem => {
        const loanDemandKMPatrakIdentifier = this.getLoanDemandKMPatrakIdentifier(loanDemandKMPatrakItem);
        if (loanDemandKMPatrakCollectionIdentifiers.includes(loanDemandKMPatrakIdentifier)) {
          return false;
        }
        loanDemandKMPatrakCollectionIdentifiers.push(loanDemandKMPatrakIdentifier);
        return true;
      });
      return [...loanDemandKMPatraksToAdd, ...loanDemandKMPatrakCollection];
    }
    return loanDemandKMPatrakCollection;
  }

  protected convertDateFromClient<T extends ILoanDemandKMPatrak | NewLoanDemandKMPatrak | PartialUpdateLoanDemandKMPatrak>(
    loanDemandKMPatrak: T
  ): RestOf<T> {
    return {
      ...loanDemandKMPatrak,
      date: loanDemandKMPatrak.date?.format(DATE_FORMAT) ?? null,
      kmDate: loanDemandKMPatrak.kmDate?.format(DATE_FORMAT) ?? null,
      pendingDate: loanDemandKMPatrak.pendingDate?.format(DATE_FORMAT) ?? null,
      depositeDate: loanDemandKMPatrak.depositeDate?.format(DATE_FORMAT) ?? null,
      dueDateB: loanDemandKMPatrak.dueDateB?.format(DATE_FORMAT) ?? null,
      vasulPatraRepaymentDateB: loanDemandKMPatrak.vasulPatraRepaymentDateB?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restLoanDemandKMPatrak: RestLoanDemandKMPatrak): ILoanDemandKMPatrak {
    return {
      ...restLoanDemandKMPatrak,
      date: restLoanDemandKMPatrak.date ? dayjs(restLoanDemandKMPatrak.date) : undefined,
      kmDate: restLoanDemandKMPatrak.kmDate ? dayjs(restLoanDemandKMPatrak.kmDate) : undefined,
      pendingDate: restLoanDemandKMPatrak.pendingDate ? dayjs(restLoanDemandKMPatrak.pendingDate) : undefined,
      depositeDate: restLoanDemandKMPatrak.depositeDate ? dayjs(restLoanDemandKMPatrak.depositeDate) : undefined,
      dueDateB: restLoanDemandKMPatrak.dueDateB ? dayjs(restLoanDemandKMPatrak.dueDateB) : undefined,
      vasulPatraRepaymentDateB: restLoanDemandKMPatrak.vasulPatraRepaymentDateB
        ? dayjs(restLoanDemandKMPatrak.vasulPatraRepaymentDateB)
        : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestLoanDemandKMPatrak>): HttpResponse<ILoanDemandKMPatrak> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestLoanDemandKMPatrak[]>): HttpResponse<ILoanDemandKMPatrak[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
