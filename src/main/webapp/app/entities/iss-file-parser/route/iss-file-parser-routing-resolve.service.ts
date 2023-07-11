import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IIssFileParser } from '../iss-file-parser.model';
import { IssFileParserService } from '../service/iss-file-parser.service';

@Injectable({ providedIn: 'root' })
export class IssFileParserRoutingResolveService implements Resolve<IIssFileParser | null> {
  constructor(protected service: IssFileParserService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IIssFileParser | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((issFileParser: HttpResponse<IIssFileParser>) => {
          if (issFileParser.body) {
            return of(issFileParser.body);
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
