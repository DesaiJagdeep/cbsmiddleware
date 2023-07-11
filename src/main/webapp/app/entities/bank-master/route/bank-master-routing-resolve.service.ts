import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IBankMaster } from '../bank-master.model';
import { BankMasterService } from '../service/bank-master.service';

@Injectable({ providedIn: 'root' })
export class BankMasterRoutingResolveService implements Resolve<IBankMaster | null> {
  constructor(protected service: BankMasterService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBankMaster | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((bankMaster: HttpResponse<IBankMaster>) => {
          if (bankMaster.body) {
            return of(bankMaster.body);
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
