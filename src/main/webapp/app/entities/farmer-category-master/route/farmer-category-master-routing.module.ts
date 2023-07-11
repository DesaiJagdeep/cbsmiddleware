import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { FarmerCategoryMasterComponent } from '../list/farmer-category-master.component';
import { FarmerCategoryMasterDetailComponent } from '../detail/farmer-category-master-detail.component';
import { FarmerCategoryMasterUpdateComponent } from '../update/farmer-category-master-update.component';
import { FarmerCategoryMasterRoutingResolveService } from './farmer-category-master-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const farmerCategoryMasterRoute: Routes = [
  {
    path: '',
    component: FarmerCategoryMasterComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FarmerCategoryMasterDetailComponent,
    resolve: {
      farmerCategoryMaster: FarmerCategoryMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FarmerCategoryMasterUpdateComponent,
    resolve: {
      farmerCategoryMaster: FarmerCategoryMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FarmerCategoryMasterUpdateComponent,
    resolve: {
      farmerCategoryMaster: FarmerCategoryMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(farmerCategoryMasterRoute)],
  exports: [RouterModule],
})
export class FarmerCategoryMasterRoutingModule {}
