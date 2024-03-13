import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { IsCalculateTempComponent } from '../list/is-calculate-temp.component';
import { IsCalculateTempDetailComponent } from '../detail/is-calculate-temp-detail.component';
import { IsCalculateTempUpdateComponent } from '../update/is-calculate-temp-update.component';
import { IsCalculateTempRoutingResolveService } from './is-calculate-temp-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const isCalculateTempRoute: Routes = [
  {
    path: '',
    component: IsCalculateTempComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: IsCalculateTempDetailComponent,
    resolve: {
      isCalculateTemp: IsCalculateTempRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: IsCalculateTempUpdateComponent,
    resolve: {
      isCalculateTemp: IsCalculateTempRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: IsCalculateTempUpdateComponent,
    resolve: {
      isCalculateTemp: IsCalculateTempRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(isCalculateTempRoute)],
  exports: [RouterModule],
})
export class IsCalculateTempRoutingModule {}
