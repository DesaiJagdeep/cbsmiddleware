import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICastCategoryMaster } from '../cast-category-master.model';
import { CastCategoryMasterService } from '../service/cast-category-master.service';

@Injectable({ providedIn: 'root' })
export class CastCategoryMasterRoutingResolveService implements Resolve<ICastCategoryMaster | null> {
  constructor(protected service: CastCategoryMasterService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICastCategoryMaster | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((castCategoryMaster: HttpResponse<ICastCategoryMaster>) => {
          if (castCategoryMaster.body) {
            return of(castCategoryMaster.body);
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
