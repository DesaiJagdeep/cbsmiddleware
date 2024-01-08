import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IKmDetails } from '../km-details.model';
import { KmDetailsService } from '../service/km-details.service';

export const kmDetailsResolve = (route: ActivatedRouteSnapshot): Observable<null | IKmDetails> => {
  const id = route.params['id'];
  if (id) {
    return inject(KmDetailsService)
      .find(id)
      .pipe(
        mergeMap((kmDetails: HttpResponse<IKmDetails>) => {
          if (kmDetails.body) {
            return of(kmDetails.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default kmDetailsResolve;
