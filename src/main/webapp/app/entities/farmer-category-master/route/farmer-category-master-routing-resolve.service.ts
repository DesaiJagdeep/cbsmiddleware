import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFarmerCategoryMaster } from '../farmer-category-master.model';
import { FarmerCategoryMasterService } from '../service/farmer-category-master.service';

@Injectable({ providedIn: 'root' })
export class FarmerCategoryMasterRoutingResolveService implements Resolve<IFarmerCategoryMaster | null> {
  constructor(protected service: FarmerCategoryMasterService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFarmerCategoryMaster | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((farmerCategoryMaster: HttpResponse<IFarmerCategoryMaster>) => {
          if (farmerCategoryMaster.body) {
            return of(farmerCategoryMaster.body);
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
