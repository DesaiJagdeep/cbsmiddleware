import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IBankBranchMaster, NewBankBranchMaster } from '../bank-branch-master.model';

export type PartialUpdateBankBranchMaster = Partial<IBankBranchMaster> & Pick<IBankBranchMaster, 'id'>;

export type EntityResponseType = HttpResponse<IBankBranchMaster>;
export type EntityArrayResponseType = HttpResponse<IBankBranchMaster[]>;

@Injectable({ providedIn: 'root' })
export class BankBranchMasterService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/bank-branch-masters');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(bankBranchMaster: NewBankBranchMaster): Observable<EntityResponseType> {
    return this.http.post<IBankBranchMaster>(this.resourceUrl, bankBranchMaster, { observe: 'response' });
  }

  update(bankBranchMaster: IBankBranchMaster): Observable<EntityResponseType> {
    return this.http.put<IBankBranchMaster>(
      `${this.resourceUrl}/${this.getBankBranchMasterIdentifier(bankBranchMaster)}`,
      bankBranchMaster,
      { observe: 'response' }
    );
  }

  partialUpdate(bankBranchMaster: PartialUpdateBankBranchMaster): Observable<EntityResponseType> {
    return this.http.patch<IBankBranchMaster>(
      `${this.resourceUrl}/${this.getBankBranchMasterIdentifier(bankBranchMaster)}`,
      bankBranchMaster,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBankBranchMaster>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBankBranchMaster[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getBankBranchMasterIdentifier(bankBranchMaster: Pick<IBankBranchMaster, 'id'>): number {
    return bankBranchMaster.id;
  }

  compareBankBranchMaster(o1: Pick<IBankBranchMaster, 'id'> | null, o2: Pick<IBankBranchMaster, 'id'> | null): boolean {
    return o1 && o2 ? this.getBankBranchMasterIdentifier(o1) === this.getBankBranchMasterIdentifier(o2) : o1 === o2;
  }

  addBankBranchMasterToCollectionIfMissing<Type extends Pick<IBankBranchMaster, 'id'>>(
    bankBranchMasterCollection: Type[],
    ...bankBranchMastersToCheck: (Type | null | undefined)[]
  ): Type[] {
    const bankBranchMasters: Type[] = bankBranchMastersToCheck.filter(isPresent);
    if (bankBranchMasters.length > 0) {
      const bankBranchMasterCollectionIdentifiers = bankBranchMasterCollection.map(
        bankBranchMasterItem => this.getBankBranchMasterIdentifier(bankBranchMasterItem)!
      );
      const bankBranchMastersToAdd = bankBranchMasters.filter(bankBranchMasterItem => {
        const bankBranchMasterIdentifier = this.getBankBranchMasterIdentifier(bankBranchMasterItem);
        if (bankBranchMasterCollectionIdentifiers.includes(bankBranchMasterIdentifier)) {
          return false;
        }
        bankBranchMasterCollectionIdentifiers.push(bankBranchMasterIdentifier);
        return true;
      });
      return [...bankBranchMastersToAdd, ...bankBranchMasterCollection];
    }
    return bankBranchMasterCollection;
  }
}
