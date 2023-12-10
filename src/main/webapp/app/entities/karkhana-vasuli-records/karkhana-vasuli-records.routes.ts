import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { KarkhanaVasuliRecordsComponent } from './list/karkhana-vasuli-records.component';
import { KarkhanaVasuliRecordsDetailComponent } from './detail/karkhana-vasuli-records-detail.component';
import { KarkhanaVasuliRecordsUpdateComponent } from './update/karkhana-vasuli-records-update.component';
import KarkhanaVasuliRecordsResolve from './route/karkhana-vasuli-records-routing-resolve.service';

const karkhanaVasuliRecordsRoute: Routes = [
  {
    path: '',
    component: KarkhanaVasuliRecordsComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: KarkhanaVasuliRecordsDetailComponent,
    resolve: {
      karkhanaVasuliRecords: KarkhanaVasuliRecordsResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: KarkhanaVasuliRecordsUpdateComponent,
    resolve: {
      karkhanaVasuliRecords: KarkhanaVasuliRecordsResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: KarkhanaVasuliRecordsUpdateComponent,
    resolve: {
      karkhanaVasuliRecords: KarkhanaVasuliRecordsResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default karkhanaVasuliRecordsRoute;
