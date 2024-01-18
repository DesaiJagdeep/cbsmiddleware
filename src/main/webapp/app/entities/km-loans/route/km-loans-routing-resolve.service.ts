import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IKmLoans } from '../km-loans.model';
import { KmLoansService } from '../service/km-loans.service';

@Injectable({ providedIn: 'root' })
export class KmLoansRoutingResolveService implements Resolve<IKmLoans | null> {
  constructor(protected service: KmLoansService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IKmLoans | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((kmLoans: HttpResponse<IKmLoans>) => {
          if (kmLoans.body) {
            return of(kmLoans.body);
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
