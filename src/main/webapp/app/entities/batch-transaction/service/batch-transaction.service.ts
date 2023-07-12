import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IBatchTransaction, NewBatchTransaction } from '../batch-transaction.model';

export type PartialUpdateBatchTransaction = Partial<IBatchTransaction> & Pick<IBatchTransaction, 'id'>;

export type EntityResponseType = HttpResponse<IBatchTransaction>;
export type EntityArrayResponseType = HttpResponse<IBatchTransaction[]>;

@Injectable({ providedIn: 'root' })
export class BatchTransactionService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/batch-transactions');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(batchTransaction: NewBatchTransaction): Observable<EntityResponseType> {
    return this.http.post<IBatchTransaction>(this.resourceUrl, batchTransaction, { observe: 'response' });
  }

  update(batchTransaction: IBatchTransaction): Observable<EntityResponseType> {
    return this.http.put<IBatchTransaction>(
      `${this.resourceUrl}/${this.getBatchTransactionIdentifier(batchTransaction)}`,
      batchTransaction,
      { observe: 'response' }
    );
  }

  partialUpdate(batchTransaction: PartialUpdateBatchTransaction): Observable<EntityResponseType> {
    return this.http.patch<IBatchTransaction>(
      `${this.resourceUrl}/${this.getBatchTransactionIdentifier(batchTransaction)}`,
      batchTransaction,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBatchTransaction>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBatchTransaction[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getBatchTransactionIdentifier(batchTransaction: Pick<IBatchTransaction, 'id'>): number {
    return batchTransaction.id;
  }

  compareBatchTransaction(o1: Pick<IBatchTransaction, 'id'> | null, o2: Pick<IBatchTransaction, 'id'> | null): boolean {
    return o1 && o2 ? this.getBatchTransactionIdentifier(o1) === this.getBatchTransactionIdentifier(o2) : o1 === o2;
  }

  addBatchTransactionToCollectionIfMissing<Type extends Pick<IBatchTransaction, 'id'>>(
    batchTransactionCollection: Type[],
    ...batchTransactionsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const batchTransactions: Type[] = batchTransactionsToCheck.filter(isPresent);
    if (batchTransactions.length > 0) {
      const batchTransactionCollectionIdentifiers = batchTransactionCollection.map(
        batchTransactionItem => this.getBatchTransactionIdentifier(batchTransactionItem)!
      );
      const batchTransactionsToAdd = batchTransactions.filter(batchTransactionItem => {
        const batchTransactionIdentifier = this.getBatchTransactionIdentifier(batchTransactionItem);
        if (batchTransactionCollectionIdentifiers.includes(batchTransactionIdentifier)) {
          return false;
        }
        batchTransactionCollectionIdentifiers.push(batchTransactionIdentifier);
        return true;
      });
      return [...batchTransactionsToAdd, ...batchTransactionCollection];
    }
    return batchTransactionCollection;
  }
}
