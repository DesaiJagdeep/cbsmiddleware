import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CropHangamComponent } from '../list/crop-hangam.component';
import { CropHangamDetailComponent } from '../detail/crop-hangam-detail.component';
import { CropHangamUpdateComponent } from '../update/crop-hangam-update.component';
import { CropHangamRoutingResolveService } from './crop-hangam-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const cropHangamRoute: Routes = [
  {
    path: '',
    component: CropHangamComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CropHangamDetailComponent,
    resolve: {
      cropHangam: CropHangamRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CropHangamUpdateComponent,
    resolve: {
      cropHangam: CropHangamRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CropHangamUpdateComponent,
    resolve: {
      cropHangam: CropHangamRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(cropHangamRoute)],
  exports: [RouterModule],
})
export class CropHangamRoutingModule {}
