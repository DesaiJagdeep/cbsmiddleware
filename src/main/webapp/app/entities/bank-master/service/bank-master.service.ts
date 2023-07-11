import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IBankMaster, NewBankMaster } from '../bank-master.model';

export type PartialUpdateBankMaster = Partial<IBankMaster> & Pick<IBankMaster, 'id'>;

export type EntityResponseType = HttpResponse<IBankMaster>;
export type EntityArrayResponseType = HttpResponse<IBankMaster[]>;

@Injectable({ providedIn: 'root' })
export class BankMasterService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/bank-masters');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(bankMaster: NewBankMaster): Observable<EntityResponseType> {
    return this.http.post<IBankMaster>(this.resourceUrl, bankMaster, { observe: 'response' });
  }

  update(bankMaster: IBankMaster): Observable<EntityResponseType> {
    return this.http.put<IBankMaster>(`${this.resourceUrl}/${this.getBankMasterIdentifier(bankMaster)}`, bankMaster, {
      observe: 'response',
    });
  }

  partialUpdate(bankMaster: PartialUpdateBankMaster): Observable<EntityResponseType> {
    return this.http.patch<IBankMaster>(`${this.resourceUrl}/${this.getBankMasterIdentifier(bankMaster)}`, bankMaster, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBankMaster>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBankMaster[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getBankMasterIdentifier(bankMaster: Pick<IBankMaster, 'id'>): number {
    return bankMaster.id;
  }

  compareBankMaster(o1: Pick<IBankMaster, 'id'> | null, o2: Pick<IBankMaster, 'id'> | null): boolean {
    return o1 && o2 ? this.getBankMasterIdentifier(o1) === this.getBankMasterIdentifier(o2) : o1 === o2;
  }

  addBankMasterToCollectionIfMissing<Type extends Pick<IBankMaster, 'id'>>(
    bankMasterCollection: Type[],
    ...bankMastersToCheck: (Type | null | undefined)[]
  ): Type[] {
    const bankMasters: Type[] = bankMastersToCheck.filter(isPresent);
    if (bankMasters.length > 0) {
      const bankMasterCollectionIdentifiers = bankMasterCollection.map(bankMasterItem => this.getBankMasterIdentifier(bankMasterItem)!);
      const bankMastersToAdd = bankMasters.filter(bankMasterItem => {
        const bankMasterIdentifier = this.getBankMasterIdentifier(bankMasterItem);
        if (bankMasterCollectionIdentifiers.includes(bankMasterIdentifier)) {
          return false;
        }
        bankMasterCollectionIdentifiers.push(bankMasterIdentifier);
        return true;
      });
      return [...bankMastersToAdd, ...bankMasterCollection];
    }
    return bankMasterCollection;
  }
}
