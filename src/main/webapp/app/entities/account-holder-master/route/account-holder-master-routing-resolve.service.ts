import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAccountHolderMaster } from '../account-holder-master.model';
import { AccountHolderMasterService } from '../service/account-holder-master.service';

@Injectable({ providedIn: 'root' })
export class AccountHolderMasterRoutingResolveService implements Resolve<IAccountHolderMaster | null> {
  constructor(protected service: AccountHolderMasterService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAccountHolderMaster | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((accountHolderMaster: HttpResponse<IAccountHolderMaster>) => {
          if (accountHolderMaster.body) {
            return of(accountHolderMaster.body);
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
