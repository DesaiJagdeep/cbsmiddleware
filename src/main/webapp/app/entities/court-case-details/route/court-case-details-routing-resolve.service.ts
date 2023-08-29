import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICourtCaseDetails } from '../court-case-details.model';
import { CourtCaseDetailsService } from '../service/court-case-details.service';

@Injectable({ providedIn: 'root' })
export class CourtCaseDetailsRoutingResolveService implements Resolve<ICourtCaseDetails | null> {
  constructor(protected service: CourtCaseDetailsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICourtCaseDetails | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((courtCaseDetails: HttpResponse<ICourtCaseDetails>) => {
          if (courtCaseDetails.body) {
            return of(courtCaseDetails.body);
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
