import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IKmCrops } from '../km-crops.model';
import { KmCropsService } from '../service/km-crops.service';

export const kmCropsResolve = (route: ActivatedRouteSnapshot): Observable<null | IKmCrops> => {
  const id = route.params['id'];
  if (id) {
    return inject(KmCropsService)
      .find(id)
      .pipe(
        mergeMap((kmCrops: HttpResponse<IKmCrops>) => {
          if (kmCrops.body) {
            return of(kmCrops.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default kmCropsResolve;
