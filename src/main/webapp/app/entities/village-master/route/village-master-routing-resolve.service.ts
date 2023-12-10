import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IVillageMaster } from '../village-master.model';
import { VillageMasterService } from '../service/village-master.service';

export const villageMasterResolve = (route: ActivatedRouteSnapshot): Observable<null | IVillageMaster> => {
  const id = route.params['id'];
  if (id) {
    return inject(VillageMasterService)
      .find(id)
      .pipe(
        mergeMap((villageMaster: HttpResponse<IVillageMaster>) => {
          if (villageMaster.body) {
            return of(villageMaster.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default villageMasterResolve;
