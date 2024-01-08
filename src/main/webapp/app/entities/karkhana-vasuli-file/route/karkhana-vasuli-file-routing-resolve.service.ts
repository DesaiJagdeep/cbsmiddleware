import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IKarkhanaVasuliFile } from '../karkhana-vasuli-file.model';
import { KarkhanaVasuliFileService } from '../service/karkhana-vasuli-file.service';

@Injectable({ providedIn: 'root' })
export class KarkhanaVasuliFileRoutingResolveService implements Resolve<IKarkhanaVasuliFile | null> {
  constructor(protected service: KarkhanaVasuliFileService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IKarkhanaVasuliFile | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((karkhanaVasuliFile: HttpResponse<IKarkhanaVasuliFile>) => {
          if (karkhanaVasuliFile.body) {
            return of(karkhanaVasuliFile.body);
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
