import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ActivityTypeComponent } from '../list/activity-type.component';
import { ActivityTypeDetailComponent } from '../detail/activity-type-detail.component';
import { ActivityTypeUpdateComponent } from '../update/activity-type-update.component';
import { ActivityTypeRoutingResolveService } from './activity-type-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const activityTypeRoute: Routes = [
  {
    path: '',
    component: ActivityTypeComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ActivityTypeDetailComponent,
    resolve: {
      activityType: ActivityTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ActivityTypeUpdateComponent,
    resolve: {
      activityType: ActivityTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ActivityTypeUpdateComponent,
    resolve: {
      activityType: ActivityTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(activityTypeRoute)],
  exports: [RouterModule],
})
export class ActivityTypeRoutingModule {}
