import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICategoryMaster } from '../category-master.model';
import { CategoryMasterService } from '../service/category-master.service';

@Injectable({ providedIn: 'root' })
export class CategoryMasterRoutingResolveService implements Resolve<ICategoryMaster | null> {
  constructor(protected service: CategoryMasterService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICategoryMaster | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((categoryMaster: HttpResponse<ICategoryMaster>) => {
          if (categoryMaster.body) {
            return of(categoryMaster.body);
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
