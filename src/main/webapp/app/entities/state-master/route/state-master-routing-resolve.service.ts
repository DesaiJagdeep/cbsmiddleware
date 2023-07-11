import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IStateMaster } from '../state-master.model';
import { StateMasterService } from '../service/state-master.service';

@Injectable({ providedIn: 'root' })
export class StateMasterRoutingResolveService implements Resolve<IStateMaster | null> {
  constructor(protected service: StateMasterService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IStateMaster | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((stateMaster: HttpResponse<IStateMaster>) => {
          if (stateMaster.body) {
            return of(stateMaster.body);
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
