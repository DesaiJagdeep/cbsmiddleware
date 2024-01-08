import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IVillageMaster } from '../village-master.model';
import { VillageMasterService } from '../service/village-master.service';

@Injectable({ providedIn: 'root' })
export class VillageMasterRoutingResolveService implements Resolve<IVillageMaster | null> {
  constructor(protected service: VillageMasterService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IVillageMaster | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((villageMaster: HttpResponse<IVillageMaster>) => {
          if (villageMaster.body) {
            return of(villageMaster.body);
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
