import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IKamalMaryadaPatrak } from '../kamal-maryada-patrak.model';
import { KamalMaryadaPatrakService } from '../service/kamal-maryada-patrak.service';

@Injectable({ providedIn: 'root' })
export class KamalMaryadaPatrakRoutingResolveService implements Resolve<IKamalMaryadaPatrak | null> {
  constructor(protected service: KamalMaryadaPatrakService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IKamalMaryadaPatrak | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((kamalMaryadaPatrak: HttpResponse<IKamalMaryadaPatrak>) => {
          if (kamalMaryadaPatrak.body) {
            return of(kamalMaryadaPatrak.body);
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
