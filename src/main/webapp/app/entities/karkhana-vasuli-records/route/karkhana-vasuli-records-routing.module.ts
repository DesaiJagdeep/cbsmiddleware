import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { KarkhanaVasuliRecordsComponent } from '../list/karkhana-vasuli-records.component';
import { KarkhanaVasuliRecordsDetailComponent } from '../detail/karkhana-vasuli-records-detail.component';
import { KarkhanaVasuliRecordsUpdateComponent } from '../update/karkhana-vasuli-records-update.component';
import { KarkhanaVasuliRecordsRoutingResolveService } from './karkhana-vasuli-records-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

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
      karkhanaVasuliRecords: KarkhanaVasuliRecordsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: KarkhanaVasuliRecordsUpdateComponent,
    resolve: {
      karkhanaVasuliRecords: KarkhanaVasuliRecordsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: KarkhanaVasuliRecordsUpdateComponent,
    resolve: {
      karkhanaVasuliRecords: KarkhanaVasuliRecordsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(karkhanaVasuliRecordsRoute)],
  exports: [RouterModule],
})
export class KarkhanaVasuliRecordsRoutingModule {}
