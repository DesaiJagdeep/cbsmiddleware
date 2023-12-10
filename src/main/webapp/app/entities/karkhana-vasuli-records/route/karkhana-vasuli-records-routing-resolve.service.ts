import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IKarkhanaVasuliRecords } from '../karkhana-vasuli-records.model';
import { KarkhanaVasuliRecordsService } from '../service/karkhana-vasuli-records.service';

export const karkhanaVasuliRecordsResolve = (route: ActivatedRouteSnapshot): Observable<null | IKarkhanaVasuliRecords> => {
  const id = route.params['id'];
  if (id) {
    return inject(KarkhanaVasuliRecordsService)
      .find(id)
      .pipe(
        mergeMap((karkhanaVasuliRecords: HttpResponse<IKarkhanaVasuliRecords>) => {
          if (karkhanaVasuliRecords.body) {
            return of(karkhanaVasuliRecords.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default karkhanaVasuliRecordsResolve;
