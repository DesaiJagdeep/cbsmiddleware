import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IActivityType } from '../activity-type.model';
import { ActivityTypeService } from '../service/activity-type.service';

@Injectable({ providedIn: 'root' })
export class ActivityTypeRoutingResolveService implements Resolve<IActivityType | null> {
  constructor(protected service: ActivityTypeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IActivityType | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((activityType: HttpResponse<IActivityType>) => {
          if (activityType.body) {
            return of(activityType.body);
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
