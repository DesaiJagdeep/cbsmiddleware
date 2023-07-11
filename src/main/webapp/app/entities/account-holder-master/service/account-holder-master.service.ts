import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAccountHolderMaster, NewAccountHolderMaster } from '../account-holder-master.model';

export type PartialUpdateAccountHolderMaster = Partial<IAccountHolderMaster> & Pick<IAccountHolderMaster, 'id'>;

export type EntityResponseType = HttpResponse<IAccountHolderMaster>;
export type EntityArrayResponseType = HttpResponse<IAccountHolderMaster[]>;

@Injectable({ providedIn: 'root' })
export class AccountHolderMasterService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/account-holder-masters');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(accountHolderMaster: NewAccountHolderMaster): Observable<EntityResponseType> {
    return this.http.post<IAccountHolderMaster>(this.resourceUrl, accountHolderMaster, { observe: 'response' });
  }

  update(accountHolderMaster: IAccountHolderMaster): Observable<EntityResponseType> {
    return this.http.put<IAccountHolderMaster>(
      `${this.resourceUrl}/${this.getAccountHolderMasterIdentifier(accountHolderMaster)}`,
      accountHolderMaster,
      { observe: 'response' }
    );
  }

  partialUpdate(accountHolderMaster: PartialUpdateAccountHolderMaster): Observable<EntityResponseType> {
    return this.http.patch<IAccountHolderMaster>(
      `${this.resourceUrl}/${this.getAccountHolderMasterIdentifier(accountHolderMaster)}`,
      accountHolderMaster,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAccountHolderMaster>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAccountHolderMaster[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAccountHolderMasterIdentifier(accountHolderMaster: Pick<IAccountHolderMaster, 'id'>): number {
    return accountHolderMaster.id;
  }

  compareAccountHolderMaster(o1: Pick<IAccountHolderMaster, 'id'> | null, o2: Pick<IAccountHolderMaster, 'id'> | null): boolean {
    return o1 && o2 ? this.getAccountHolderMasterIdentifier(o1) === this.getAccountHolderMasterIdentifier(o2) : o1 === o2;
  }

  addAccountHolderMasterToCollectionIfMissing<Type extends Pick<IAccountHolderMaster, 'id'>>(
    accountHolderMasterCollection: Type[],
    ...accountHolderMastersToCheck: (Type | null | undefined)[]
  ): Type[] {
    const accountHolderMasters: Type[] = accountHolderMastersToCheck.filter(isPresent);
    if (accountHolderMasters.length > 0) {
      const accountHolderMasterCollectionIdentifiers = accountHolderMasterCollection.map(
        accountHolderMasterItem => this.getAccountHolderMasterIdentifier(accountHolderMasterItem)!
      );
      const accountHolderMastersToAdd = accountHolderMasters.filter(accountHolderMasterItem => {
        const accountHolderMasterIdentifier = this.getAccountHolderMasterIdentifier(accountHolderMasterItem);
        if (accountHolderMasterCollectionIdentifiers.includes(accountHolderMasterIdentifier)) {
          return false;
        }
        accountHolderMasterCollectionIdentifiers.push(accountHolderMasterIdentifier);
        return true;
      });
      return [...accountHolderMastersToAdd, ...accountHolderMasterCollection];
    }
    return accountHolderMasterCollection;
  }
}
