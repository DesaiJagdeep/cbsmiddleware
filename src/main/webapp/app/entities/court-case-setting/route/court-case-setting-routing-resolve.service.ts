import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICourtCaseSetting } from '../court-case-setting.model';
import { CourtCaseSettingService } from '../service/court-case-setting.service';

@Injectable({ providedIn: 'root' })
export class CourtCaseSettingRoutingResolveService implements Resolve<ICourtCaseSetting | null> {
  constructor(protected service: CourtCaseSettingService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICourtCaseSetting | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((courtCaseSetting: HttpResponse<ICourtCaseSetting>) => {
          if (courtCaseSetting.body) {
            return of(courtCaseSetting.body);
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
