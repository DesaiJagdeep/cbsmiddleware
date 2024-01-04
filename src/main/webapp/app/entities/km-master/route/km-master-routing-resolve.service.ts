import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IKmMaster } from '../km-master.model';
import { KmMasterService } from '../service/km-master.service';

export const kmMasterResolve = (route: ActivatedRouteSnapshot): Observable<null | IKmMaster> => {
  const id = route.params['id'];
  if (id) {
    return inject(KmMasterService)
      .find(id)
      .pipe(
        mergeMap((kmMaster: HttpResponse<IKmMaster>) => {
          if (kmMaster.body) {
            return of(kmMaster.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default kmMasterResolve;
