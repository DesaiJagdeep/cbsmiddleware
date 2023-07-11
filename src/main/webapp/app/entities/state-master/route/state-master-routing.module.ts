import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { StateMasterComponent } from '../list/state-master.component';
import { StateMasterDetailComponent } from '../detail/state-master-detail.component';
import { StateMasterUpdateComponent } from '../update/state-master-update.component';
import { StateMasterRoutingResolveService } from './state-master-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const stateMasterRoute: Routes = [
  {
    path: '',
    component: StateMasterComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: StateMasterDetailComponent,
    resolve: {
      stateMaster: StateMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: StateMasterUpdateComponent,
    resolve: {
      stateMaster: StateMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: StateMasterUpdateComponent,
    resolve: {
      stateMaster: StateMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(stateMasterRoute)],
  exports: [RouterModule],
})
export class StateMasterRoutingModule {}
