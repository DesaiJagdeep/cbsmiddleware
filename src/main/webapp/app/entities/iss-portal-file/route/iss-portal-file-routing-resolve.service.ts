import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IIssPortalFile } from '../iss-portal-file.model';
import { IssPortalFileService } from '../service/iss-portal-file.service';

@Injectable({ providedIn: 'root' })
export class IssPortalFileRoutingResolveService implements Resolve<IIssPortalFile | null> {
  constructor(protected service: IssPortalFileService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IIssPortalFile | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((issPortalFile: HttpResponse<IIssPortalFile>) => {
          if (issPortalFile.body) {
            return of(issPortalFile.body);
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
