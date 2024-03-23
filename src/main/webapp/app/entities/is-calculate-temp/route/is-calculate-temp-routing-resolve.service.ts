import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IIsCalculateTemp } from '../is-calculate-temp.model';
import { IsCalculateTempService } from '../service/is-calculate-temp.service';

@Injectable({ providedIn: 'root' })
export class IsCalculateTempRoutingResolveService implements Resolve<IIsCalculateTemp | null> {
  constructor(protected service: IsCalculateTempService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IIsCalculateTemp | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((isCalculateTemp: HttpResponse<IIsCalculateTemp>) => {
          if (isCalculateTemp.body) {
            return of(isCalculateTemp.body);
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
