import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICourtCase } from '../court-case.model';
import { CourtCaseService } from '../service/court-case.service';

@Injectable({ providedIn: 'root' })
export class CourtCaseRoutingResolveService implements Resolve<ICourtCase | null> {
  constructor(protected service: CourtCaseService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICourtCase | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((courtCase: HttpResponse<ICourtCase>) => {
          if (courtCase.body) {
            return of(courtCase.body);
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
