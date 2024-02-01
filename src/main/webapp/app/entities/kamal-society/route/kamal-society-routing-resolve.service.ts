import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IKamalSociety } from '../kamal-society.model';
import { KamalSocietyService } from '../service/kamal-society.service';

@Injectable({ providedIn: 'root' })
export class KamalSocietyRoutingResolveService implements Resolve<IKamalSociety | null> {
  constructor(protected service: KamalSocietyService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IKamalSociety | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((kamalSociety: HttpResponse<IKamalSociety>) => {
          if (kamalSociety.body) {
            return of(kamalSociety.body);
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
