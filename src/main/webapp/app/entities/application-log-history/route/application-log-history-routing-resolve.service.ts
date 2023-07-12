import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IApplicationLogHistory } from '../application-log-history.model';
import { ApplicationLogHistoryService } from '../service/application-log-history.service';

@Injectable({ providedIn: 'root' })
export class ApplicationLogHistoryRoutingResolveService implements Resolve<IApplicationLogHistory | null> {
  constructor(protected service: ApplicationLogHistoryService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IApplicationLogHistory | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((applicationLogHistory: HttpResponse<IApplicationLogHistory>) => {
          if (applicationLogHistory.body) {
            return of(applicationLogHistory.body);
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
