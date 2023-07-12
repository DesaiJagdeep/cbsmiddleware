import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IBatchTransaction } from '../batch-transaction.model';
import { BatchTransactionService } from '../service/batch-transaction.service';

@Injectable({ providedIn: 'root' })
export class BatchTransactionRoutingResolveService implements Resolve<IBatchTransaction | null> {
  constructor(protected service: BatchTransactionService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBatchTransaction | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((batchTransaction: HttpResponse<IBatchTransaction>) => {
          if (batchTransaction.body) {
            return of(batchTransaction.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
