import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISeasonMaster } from '../season-master.model';
import { SeasonMasterService } from '../service/season-master.service';

@Injectable({ providedIn: 'root' })
export class SeasonMasterRoutingResolveService implements Resolve<ISeasonMaster | null> {
  constructor(protected service: SeasonMasterService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISeasonMaster | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((seasonMaster: HttpResponse<ISeasonMaster>) => {
          if (seasonMaster.body) {
            return of(seasonMaster.body);
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
