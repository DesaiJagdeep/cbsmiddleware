import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { KmMaganiCropComponent } from '../list/km-magani-crop.component';
import { KmMaganiCropDetailComponent } from '../detail/km-magani-crop-detail.component';
import { KmMaganiCropUpdateComponent } from '../update/km-magani-crop-update.component';
import { KmMaganiCropRoutingResolveService } from './km-magani-crop-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const kmMaganiCropRoute: Routes = [
  {
    path: '',
    component: KmMaganiCropComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: KmMaganiCropDetailComponent,
    resolve: {
      kmMaganiCrop: KmMaganiCropRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: KmMaganiCropUpdateComponent,
    resolve: {
      kmMaganiCrop: KmMaganiCropRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: KmMaganiCropUpdateComponent,
    resolve: {
      kmMaganiCrop: KmMaganiCropRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(kmMaganiCropRoute)],
  exports: [RouterModule],
})
export class KmMaganiCropRoutingModule {}
