import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { FarmerTypeMasterComponent } from '../list/farmer-type-master.component';
import { FarmerTypeMasterDetailComponent } from '../detail/farmer-type-master-detail.component';
import { FarmerTypeMasterUpdateComponent } from '../update/farmer-type-master-update.component';
import { FarmerTypeMasterRoutingResolveService } from './farmer-type-master-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const farmerTypeMasterRoute: Routes = [
  {
    path: '',
    component: FarmerTypeMasterComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FarmerTypeMasterDetailComponent,
    resolve: {
      farmerTypeMaster: FarmerTypeMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FarmerTypeMasterUpdateComponent,
    resolve: {
      farmerTypeMaster: FarmerTypeMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FarmerTypeMasterUpdateComponent,
    resolve: {
      farmerTypeMaster: FarmerTypeMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(farmerTypeMasterRoute)],
  exports: [RouterModule],
})
export class FarmerTypeMasterRoutingModule {}
