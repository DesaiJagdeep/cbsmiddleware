import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IKmMaganiCrop } from '../km-magani-crop.model';
import { KmMaganiCropService } from '../service/km-magani-crop.service';

@Injectable({ providedIn: 'root' })
export class KmMaganiCropRoutingResolveService implements Resolve<IKmMaganiCrop | null> {
  constructor(protected service: KmMaganiCropService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IKmMaganiCrop | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((kmMaganiCrop: HttpResponse<IKmMaganiCrop>) => {
          if (kmMaganiCrop.body) {
            return of(kmMaganiCrop.body);
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
