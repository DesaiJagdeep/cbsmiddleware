import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PacsMasterComponent } from '../list/pacs-master.component';
import { PacsMasterDetailComponent } from '../detail/pacs-master-detail.component';
import { PacsMasterUpdateComponent } from '../update/pacs-master-update.component';
import { PacsMasterRoutingResolveService } from './pacs-master-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const pacsMasterRoute: Routes = [
  {
    path: '',
    component: PacsMasterComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PacsMasterDetailComponent,
    resolve: {
      pacsMaster: PacsMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PacsMasterUpdateComponent,
    resolve: {
      pacsMaster: PacsMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PacsMasterUpdateComponent,
    resolve: {
      pacsMaster: PacsMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(pacsMasterRoute)],
  exports: [RouterModule],
})
export class PacsMasterRoutingModule {}
