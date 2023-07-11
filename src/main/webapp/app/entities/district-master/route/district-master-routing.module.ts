import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DistrictMasterComponent } from '../list/district-master.component';
import { DistrictMasterDetailComponent } from '../detail/district-master-detail.component';
import { DistrictMasterUpdateComponent } from '../update/district-master-update.component';
import { DistrictMasterRoutingResolveService } from './district-master-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const districtMasterRoute: Routes = [
  {
    path: '',
    component: DistrictMasterComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DistrictMasterDetailComponent,
    resolve: {
      districtMaster: DistrictMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DistrictMasterUpdateComponent,
    resolve: {
      districtMaster: DistrictMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DistrictMasterUpdateComponent,
    resolve: {
      districtMaster: DistrictMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(districtMasterRoute)],
  exports: [RouterModule],
})
export class DistrictMasterRoutingModule {}
