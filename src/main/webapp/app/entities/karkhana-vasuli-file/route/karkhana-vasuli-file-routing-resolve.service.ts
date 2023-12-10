import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IKarkhanaVasuliFile } from '../karkhana-vasuli-file.model';
import { KarkhanaVasuliFileService } from '../service/karkhana-vasuli-file.service';

export const karkhanaVasuliFileResolve = (route: ActivatedRouteSnapshot): Observable<null | IKarkhanaVasuliFile> => {
  const id = route.params['id'];
  if (id) {
    return inject(KarkhanaVasuliFileService)
      .find(id)
      .pipe(
        mergeMap((karkhanaVasuliFile: HttpResponse<IKarkhanaVasuliFile>) => {
          if (karkhanaVasuliFile.body) {
            return of(karkhanaVasuliFile.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default karkhanaVasuliFileResolve;
