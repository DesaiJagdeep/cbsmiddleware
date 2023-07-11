import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICropMaster } from '../crop-master.model';
import { CropMasterService } from '../service/crop-master.service';

@Injectable({ providedIn: 'root' })
export class CropMasterRoutingResolveService implements Resolve<ICropMaster | null> {
  constructor(protected service: CropMasterService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICropMaster | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((cropMaster: HttpResponse<ICropMaster>) => {
          if (cropMaster.body) {
            return of(cropMaster.body);
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
