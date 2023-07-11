import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CropMasterComponent } from '../list/crop-master.component';
import { CropMasterDetailComponent } from '../detail/crop-master-detail.component';
import { CropMasterUpdateComponent } from '../update/crop-master-update.component';
import { CropMasterRoutingResolveService } from './crop-master-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const cropMasterRoute: Routes = [
  {
    path: '',
    component: CropMasterComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CropMasterDetailComponent,
    resolve: {
      cropMaster: CropMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CropMasterUpdateComponent,
    resolve: {
      cropMaster: CropMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CropMasterUpdateComponent,
    resolve: {
      cropMaster: CropMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(cropMasterRoute)],
  exports: [RouterModule],
})
export class CropMasterRoutingModule {}
