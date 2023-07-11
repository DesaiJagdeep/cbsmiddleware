import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICropCategoryMaster } from '../crop-category-master.model';
import { CropCategoryMasterService } from '../service/crop-category-master.service';

@Injectable({ providedIn: 'root' })
export class CropCategoryMasterRoutingResolveService implements Resolve<ICropCategoryMaster | null> {
  constructor(protected service: CropCategoryMasterService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICropCategoryMaster | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((cropCategoryMaster: HttpResponse<ICropCategoryMaster>) => {
          if (cropCategoryMaster.body) {
            return of(cropCategoryMaster.body);
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
