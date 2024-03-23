import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICropRateMaster } from '../crop-rate-master.model';
import { CropRateMasterService } from '../service/crop-rate-master.service';

@Injectable({ providedIn: 'root' })
export class CropRateMasterRoutingResolveService implements Resolve<ICropRateMaster | null> {
  constructor(protected service: CropRateMasterService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICropRateMaster | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((cropRateMaster: HttpResponse<ICropRateMaster>) => {
          if (cropRateMaster.body) {
            return of(cropRateMaster.body);
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
