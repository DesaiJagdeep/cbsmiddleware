import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CropRateMasterComponent } from '../list/crop-rate-master.component';
import { CropRateMasterDetailComponent } from '../detail/crop-rate-master-detail.component';
import { CropRateMasterUpdateComponent } from '../update/crop-rate-master-update.component';
import { CropRateMasterRoutingResolveService } from './crop-rate-master-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const cropRateMasterRoute: Routes = [
  {
    path: '',
    component: CropRateMasterComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CropRateMasterDetailComponent,
    resolve: {
      cropRateMaster: CropRateMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CropRateMasterUpdateComponent,
    resolve: {
      cropRateMaster: CropRateMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CropRateMasterUpdateComponent,
    resolve: {
      cropRateMaster: CropRateMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(cropRateMasterRoute)],
  exports: [RouterModule],
})
export class CropRateMasterRoutingModule {}
