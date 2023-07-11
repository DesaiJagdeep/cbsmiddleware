import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IOccupationMaster } from '../occupation-master.model';
import { OccupationMasterService } from '../service/occupation-master.service';

@Injectable({ providedIn: 'root' })
export class OccupationMasterRoutingResolveService implements Resolve<IOccupationMaster | null> {
  constructor(protected service: OccupationMasterService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IOccupationMaster | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((occupationMaster: HttpResponse<IOccupationMaster>) => {
          if (occupationMaster.body) {
            return of(occupationMaster.body);
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
