import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IBankBranchMaster } from '../bank-branch-master.model';
import { BankBranchMasterService } from '../service/bank-branch-master.service';

@Injectable({ providedIn: 'root' })
export class BankBranchMasterRoutingResolveService implements Resolve<IBankBranchMaster | null> {
  constructor(protected service: BankBranchMasterService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBankBranchMaster | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((bankBranchMaster: HttpResponse<IBankBranchMaster>) => {
          if (bankBranchMaster.body) {
            return of(bankBranchMaster.body);
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
