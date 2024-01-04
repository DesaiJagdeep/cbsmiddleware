import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICropMaster } from '../crop-master.model';
import { CropMasterService } from '../service/crop-master.service';

export const cropMasterResolve = (route: ActivatedRouteSnapshot): Observable<null | ICropMaster> => {
  const id = route.params['id'];
  if (id) {
    return inject(CropMasterService)
      .find(id)
      .pipe(
        mergeMap((cropMaster: HttpResponse<ICropMaster>) => {
          if (cropMaster.body) {
            return of(cropMaster.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default cropMasterResolve;
