import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { KmCropsComponent } from './list/km-crops.component';
import { KmCropsDetailComponent } from './detail/km-crops-detail.component';
import { KmCropsUpdateComponent } from './update/km-crops-update.component';
import KmCropsResolve from './route/km-crops-routing-resolve.service';

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
      kmCrops: KmCropsResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: KmCropsUpdateComponent,
    resolve: {
      kmCrops: KmCropsResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: KmCropsUpdateComponent,
    resolve: {
      kmCrops: KmCropsResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default kmCropsRoute;
