import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DesignationMasterComponent } from '../list/designation-master.component';
import { DesignationMasterDetailComponent } from '../detail/designation-master-detail.component';
import { DesignationMasterUpdateComponent } from '../update/designation-master-update.component';
import { DesignationMasterRoutingResolveService } from './designation-master-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const designationMasterRoute: Routes = [
  {
    path: '',
    component: DesignationMasterComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DesignationMasterDetailComponent,
    resolve: {
      designationMaster: DesignationMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DesignationMasterUpdateComponent,
    resolve: {
      designationMaster: DesignationMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DesignationMasterUpdateComponent,
    resolve: {
      designationMaster: DesignationMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(designationMasterRoute)],
  exports: [RouterModule],
})
export class DesignationMasterRoutingModule {}
