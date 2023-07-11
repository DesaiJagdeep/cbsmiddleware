import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IRelativeMaster } from '../relative-master.model';
import { RelativeMasterService } from '../service/relative-master.service';

@Injectable({ providedIn: 'root' })
export class RelativeMasterRoutingResolveService implements Resolve<IRelativeMaster | null> {
  constructor(protected service: RelativeMasterService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRelativeMaster | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((relativeMaster: HttpResponse<IRelativeMaster>) => {
          if (relativeMaster.body) {
            return of(relativeMaster.body);
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
