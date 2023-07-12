import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IApplicationLog } from '../application-log.model';
import { ApplicationLogService } from '../service/application-log.service';

@Injectable({ providedIn: 'root' })
export class ApplicationLogRoutingResolveService implements Resolve<IApplicationLog | null> {
  constructor(protected service: ApplicationLogService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IApplicationLog | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((applicationLog: HttpResponse<IApplicationLog>) => {
          if (applicationLog.body) {
            return of(applicationLog.body);
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
