import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFactoryMaster } from '../factory-master.model';
import { FactoryMasterService } from '../service/factory-master.service';

export const factoryMasterResolve = (route: ActivatedRouteSnapshot): Observable<null | IFactoryMaster> => {
  const id = route.params['id'];
  if (id) {
    return inject(FactoryMasterService)
      .find(id)
      .pipe(
        mergeMap((factoryMaster: HttpResponse<IFactoryMaster>) => {
          if (factoryMaster.body) {
            return of(factoryMaster.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default factoryMasterResolve;
