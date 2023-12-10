import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { KarkhanaVasuliFileComponent } from './list/karkhana-vasuli-file.component';
import { KarkhanaVasuliFileDetailComponent } from './detail/karkhana-vasuli-file-detail.component';
import { KarkhanaVasuliFileUpdateComponent } from './update/karkhana-vasuli-file-update.component';
import KarkhanaVasuliFileResolve from './route/karkhana-vasuli-file-routing-resolve.service';

const karkhanaVasuliFileRoute: Routes = [
  {
    path: '',
    component: KarkhanaVasuliFileComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: KarkhanaVasuliFileDetailComponent,
    resolve: {
      karkhanaVasuliFile: KarkhanaVasuliFileResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: KarkhanaVasuliFileUpdateComponent,
    resolve: {
      karkhanaVasuliFile: KarkhanaVasuliFileResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: KarkhanaVasuliFileUpdateComponent,
    resolve: {
      karkhanaVasuliFile: KarkhanaVasuliFileResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default karkhanaVasuliFileRoute;
