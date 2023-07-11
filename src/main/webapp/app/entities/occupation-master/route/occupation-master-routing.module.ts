import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { OccupationMasterComponent } from '../list/occupation-master.component';
import { OccupationMasterDetailComponent } from '../detail/occupation-master-detail.component';
import { OccupationMasterUpdateComponent } from '../update/occupation-master-update.component';
import { OccupationMasterRoutingResolveService } from './occupation-master-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const occupationMasterRoute: Routes = [
  {
    path: '',
    component: OccupationMasterComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: OccupationMasterDetailComponent,
    resolve: {
      occupationMaster: OccupationMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: OccupationMasterUpdateComponent,
    resolve: {
      occupationMaster: OccupationMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: OccupationMasterUpdateComponent,
    resolve: {
      occupationMaster: OccupationMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(occupationMasterRoute)],
  exports: [RouterModule],
})
export class OccupationMasterRoutingModule {}
