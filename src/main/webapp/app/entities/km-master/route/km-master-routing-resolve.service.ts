import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IKmMaster } from '../km-master.model';
import { KmMasterService } from '../service/km-master.service';

@Injectable({ providedIn: 'root' })
export class KmMasterRoutingResolveService implements Resolve<IKmMaster | null> {
  constructor(protected service: KmMasterService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IKmMaster | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((kmMaster: HttpResponse<IKmMaster>) => {
          if (kmMaster.body) {
            return of(kmMaster.body);
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
