import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ApplicationLogHistoryComponent } from '../list/application-log-history.component';
import { ApplicationLogHistoryDetailComponent } from '../detail/application-log-history-detail.component';
import { ApplicationLogHistoryUpdateComponent } from '../update/application-log-history-update.component';
import { ApplicationLogHistoryRoutingResolveService } from './application-log-history-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const applicationLogHistoryRoute: Routes = [
  {
    path: '',
    component: ApplicationLogHistoryComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ApplicationLogHistoryDetailComponent,
    resolve: {
      applicationLogHistory: ApplicationLogHistoryRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ApplicationLogHistoryUpdateComponent,
    resolve: {
      applicationLogHistory: ApplicationLogHistoryRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ApplicationLogHistoryUpdateComponent,
    resolve: {
      applicationLogHistory: ApplicationLogHistoryRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(applicationLogHistoryRoute)],
  exports: [RouterModule],
})
export class ApplicationLogHistoryRoutingModule {}
