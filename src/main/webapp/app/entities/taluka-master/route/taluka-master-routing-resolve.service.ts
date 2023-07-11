import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITalukaMaster } from '../taluka-master.model';
import { TalukaMasterService } from '../service/taluka-master.service';

@Injectable({ providedIn: 'root' })
export class TalukaMasterRoutingResolveService implements Resolve<ITalukaMaster | null> {
  constructor(protected service: TalukaMasterService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITalukaMaster | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((talukaMaster: HttpResponse<ITalukaMaster>) => {
          if (talukaMaster.body) {
            return of(talukaMaster.body);
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
