import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IKmMagani } from '../km-magani.model';
import { KmMaganiService } from '../service/km-magani.service';

@Injectable({ providedIn: 'root' })
export class KmMaganiRoutingResolveService implements Resolve<IKmMagani | null> {
  constructor(protected service: KmMaganiService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IKmMagani | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((kmMagani: HttpResponse<IKmMagani>) => {
          if (kmMagani.body) {
            return of(kmMagani.body);
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
