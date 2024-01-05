import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IKmLoans } from '../km-loans.model';
import { KmLoansService } from '../service/km-loans.service';

export const kmLoansResolve = (route: ActivatedRouteSnapshot): Observable<null | IKmLoans> => {
  const id = route.params['id'];
  if (id) {
    return inject(KmLoansService)
      .find(id)
      .pipe(
        mergeMap((kmLoans: HttpResponse<IKmLoans>) => {
          if (kmLoans.body) {
            return of(kmLoans.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default kmLoansResolve;
