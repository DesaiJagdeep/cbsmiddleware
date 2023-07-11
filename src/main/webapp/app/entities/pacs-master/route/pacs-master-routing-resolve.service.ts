import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPacsMaster } from '../pacs-master.model';
import { PacsMasterService } from '../service/pacs-master.service';

@Injectable({ providedIn: 'root' })
export class PacsMasterRoutingResolveService implements Resolve<IPacsMaster | null> {
  constructor(protected service: PacsMasterService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPacsMaster | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((pacsMaster: HttpResponse<IPacsMaster>) => {
          if (pacsMaster.body) {
            return of(pacsMaster.body);
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
