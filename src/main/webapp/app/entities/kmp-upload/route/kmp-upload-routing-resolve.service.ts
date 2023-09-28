import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IKMPUpload } from '../kmp-upload.model';
import { KMPUploadService } from '../service/kmp-upload.service';

@Injectable({ providedIn: 'root' })
export class KMPUploadRoutingResolveService implements Resolve<IKMPUpload | null> {
  constructor(protected service: KMPUploadService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IKMPUpload | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((kMPUpload: HttpResponse<IKMPUpload>) => {
          if (kMPUpload.body) {
            return of(kMPUpload.body);
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
