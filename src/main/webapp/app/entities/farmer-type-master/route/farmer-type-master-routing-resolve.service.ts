import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFarmerTypeMaster } from '../farmer-type-master.model';
import { FarmerTypeMasterService } from '../service/farmer-type-master.service';

@Injectable({ providedIn: 'root' })
export class FarmerTypeMasterRoutingResolveService implements Resolve<IFarmerTypeMaster | null> {
  constructor(protected service: FarmerTypeMasterService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFarmerTypeMaster | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((farmerTypeMaster: HttpResponse<IFarmerTypeMaster>) => {
          if (farmerTypeMaster.body) {
            return of(farmerTypeMaster.body);
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
