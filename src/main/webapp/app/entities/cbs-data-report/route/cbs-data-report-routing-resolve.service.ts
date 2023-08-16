import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICbsDataReport } from '../cbs-data-report.model';
import { CbsDataReportService } from '../service/cbs-data-report.service';

@Injectable({ providedIn: 'root' })
export class CbsDataReportRoutingResolveService implements Resolve<ICbsDataReport | null> {
  constructor(protected service: CbsDataReportService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICbsDataReport | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((cbsDataReport: HttpResponse<ICbsDataReport>) => {
          if (cbsDataReport.body) {
            return of(cbsDataReport.body);
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
