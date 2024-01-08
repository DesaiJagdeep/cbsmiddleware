import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IKmDetails } from '../km-details.model';
import { KmDetailsService } from '../service/km-details.service';

@Injectable({ providedIn: 'root' })
export class KmDetailsRoutingResolveService implements Resolve<IKmDetails | null> {
  constructor(protected service: KmDetailsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IKmDetails | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((kmDetails: HttpResponse<IKmDetails>) => {
          if (kmDetails.body) {
            return of(kmDetails.body);
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
