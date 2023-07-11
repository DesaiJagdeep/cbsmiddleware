import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDistrictMaster } from '../district-master.model';
import { DistrictMasterService } from '../service/district-master.service';

@Injectable({ providedIn: 'root' })
export class DistrictMasterRoutingResolveService implements Resolve<IDistrictMaster | null> {
  constructor(protected service: DistrictMasterService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDistrictMaster | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((districtMaster: HttpResponse<IDistrictMaster>) => {
          if (districtMaster.body) {
            return of(districtMaster.body);
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
