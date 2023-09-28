import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ILoanDemandKMPatrak } from '../loan-demand-km-patrak.model';
import { LoanDemandKMPatrakService } from '../service/loan-demand-km-patrak.service';

@Injectable({ providedIn: 'root' })
export class LoanDemandKMPatrakRoutingResolveService implements Resolve<ILoanDemandKMPatrak | null> {
  constructor(protected service: LoanDemandKMPatrakService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILoanDemandKMPatrak | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((loanDemandKMPatrak: HttpResponse<ILoanDemandKMPatrak>) => {
          if (loanDemandKMPatrak.body) {
            return of(loanDemandKMPatrak.body);
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
