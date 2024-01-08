import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { KmCropsComponent } from '../list/km-crops.component';
import { KmCropsDetailComponent } from '../detail/km-crops-detail.component';
import { KmCropsUpdateComponent } from '../update/km-crops-update.component';
import { KmCropsRoutingResolveService } from './km-crops-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const kmCropsRoute: Routes = [
  {
    path: '',
    component: KmCropsComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: KmCropsDetailComponent,
    resolve: {
      kmCrops: KmCropsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: KmCropsUpdateComponent,
    resolve: {
      kmCrops: KmCropsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: KmCropsUpdateComponent,
    resolve: {
      kmCrops: KmCropsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(kmCropsRoute)],
  exports: [RouterModule],
})
export class KmCropsRoutingModule {}
