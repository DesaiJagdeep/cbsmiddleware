import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { KamalCropComponent } from '../list/kamal-crop.component';
import { KamalCropDetailComponent } from '../detail/kamal-crop-detail.component';
import { KamalCropUpdateComponent } from '../update/kamal-crop-update.component';
import { KamalCropRoutingResolveService } from './kamal-crop-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const kamalCropRoute: Routes = [
  {
    path: '',
    component: KamalCropComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: KamalCropDetailComponent,
    resolve: {
      kamalCrop: KamalCropRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: KamalCropUpdateComponent,
    resolve: {
      kamalCrop: KamalCropRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: KamalCropUpdateComponent,
    resolve: {
      kamalCrop: KamalCropRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(kamalCropRoute)],
  exports: [RouterModule],
})
export class KamalCropRoutingModule {}
