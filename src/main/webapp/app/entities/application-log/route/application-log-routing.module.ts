import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ApplicationLogComponent } from '../list/application-log.component';
import { ApplicationLogDetailComponent } from '../detail/application-log-detail.component';
import { ApplicationLogUpdateComponent } from '../update/application-log-update.component';
import { ApplicationLogRoutingResolveService } from './application-log-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const applicationLogRoute: Routes = [
  {
    path: '',
    component: ApplicationLogComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ApplicationLogDetailComponent,
    resolve: {
      applicationLog: ApplicationLogRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ApplicationLogUpdateComponent,
    resolve: {
      applicationLog: ApplicationLogRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ApplicationLogUpdateComponent,
    resolve: {
      applicationLog: ApplicationLogRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(applicationLogRoute)],
  exports: [RouterModule],
})
export class ApplicationLogRoutingModule {}
