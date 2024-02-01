import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IKamalCrop } from '../kamal-crop.model';
import { KamalCropService } from '../service/kamal-crop.service';

@Injectable({ providedIn: 'root' })
export class KamalCropRoutingResolveService implements Resolve<IKamalCrop | null> {
  constructor(protected service: KamalCropService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IKamalCrop | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((kamalCrop: HttpResponse<IKamalCrop>) => {
          if (kamalCrop.body) {
            return of(kamalCrop.body);
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
