import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDesignationMaster } from '../designation-master.model';
import { DesignationMasterService } from '../service/designation-master.service';

@Injectable({ providedIn: 'root' })
export class DesignationMasterRoutingResolveService implements Resolve<IDesignationMaster | null> {
  constructor(protected service: DesignationMasterService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDesignationMaster | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((designationMaster: HttpResponse<IDesignationMaster>) => {
          if (designationMaster.body) {
            return of(designationMaster.body);
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
