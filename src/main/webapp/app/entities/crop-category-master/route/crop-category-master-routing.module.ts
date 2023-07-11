import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CropCategoryMasterComponent } from '../list/crop-category-master.component';
import { CropCategoryMasterDetailComponent } from '../detail/crop-category-master-detail.component';
import { CropCategoryMasterUpdateComponent } from '../update/crop-category-master-update.component';
import { CropCategoryMasterRoutingResolveService } from './crop-category-master-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const cropCategoryMasterRoute: Routes = [
  {
    path: '',
    component: CropCategoryMasterComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CropCategoryMasterDetailComponent,
    resolve: {
      cropCategoryMaster: CropCategoryMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CropCategoryMasterUpdateComponent,
    resolve: {
      cropCategoryMaster: CropCategoryMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CropCategoryMasterUpdateComponent,
    resolve: {
      cropCategoryMaster: CropCategoryMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(cropCategoryMasterRoute)],
  exports: [RouterModule],
})
export class CropCategoryMasterRoutingModule {}
