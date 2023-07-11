import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ILandTypeMaster } from '../land-type-master.model';
import { LandTypeMasterService } from '../service/land-type-master.service';

@Injectable({ providedIn: 'root' })
export class LandTypeMasterRoutingResolveService implements Resolve<ILandTypeMaster | null> {
  constructor(protected service: LandTypeMasterService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILandTypeMaster | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((landTypeMaster: HttpResponse<ILandTypeMaster>) => {
          if (landTypeMaster.body) {
            return of(landTypeMaster.body);
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
