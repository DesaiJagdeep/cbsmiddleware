import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IKarkhanaVasuli } from '../karkhana-vasuli.model';
import { KarkhanaVasuliService } from '../service/karkhana-vasuli.service';

@Injectable({ providedIn: 'root' })
export class KarkhanaVasuliRoutingResolveService implements Resolve<IKarkhanaVasuli | null> {
  constructor(protected service: KarkhanaVasuliService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IKarkhanaVasuli | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((karkhanaVasuli: HttpResponse<IKarkhanaVasuli>) => {
          if (karkhanaVasuli.body) {
            return of(karkhanaVasuli.body);
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
