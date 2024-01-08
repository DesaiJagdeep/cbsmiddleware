import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IKarkhanaVasuliRecords } from '../karkhana-vasuli-records.model';
import { KarkhanaVasuliRecordsService } from '../service/karkhana-vasuli-records.service';

@Injectable({ providedIn: 'root' })
export class KarkhanaVasuliRecordsRoutingResolveService implements Resolve<IKarkhanaVasuliRecords | null> {
  constructor(protected service: KarkhanaVasuliRecordsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IKarkhanaVasuliRecords | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((karkhanaVasuliRecords: HttpResponse<IKarkhanaVasuliRecords>) => {
          if (karkhanaVasuliRecords.body) {
            return of(karkhanaVasuliRecords.body);
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
