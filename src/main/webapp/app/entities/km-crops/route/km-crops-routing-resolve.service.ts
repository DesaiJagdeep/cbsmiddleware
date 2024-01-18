import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IKmCrops } from '../km-crops.model';
import { KmCropsService } from '../service/km-crops.service';

@Injectable({ providedIn: 'root' })
export class KmCropsRoutingResolveService implements Resolve<IKmCrops | null> {
  constructor(protected service: KmCropsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IKmCrops | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((kmCrops: HttpResponse<IKmCrops>) => {
          if (kmCrops.body) {
            return of(kmCrops.body);
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
