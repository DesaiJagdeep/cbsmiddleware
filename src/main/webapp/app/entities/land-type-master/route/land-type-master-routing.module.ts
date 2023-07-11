import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { LandTypeMasterComponent } from '../list/land-type-master.component';
import { LandTypeMasterDetailComponent } from '../detail/land-type-master-detail.component';
import { LandTypeMasterUpdateComponent } from '../update/land-type-master-update.component';
import { LandTypeMasterRoutingResolveService } from './land-type-master-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const landTypeMasterRoute: Routes = [
  {
    path: '',
    component: LandTypeMasterComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: LandTypeMasterDetailComponent,
    resolve: {
      landTypeMaster: LandTypeMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LandTypeMasterUpdateComponent,
    resolve: {
      landTypeMaster: LandTypeMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: LandTypeMasterUpdateComponent,
    resolve: {
      landTypeMaster: LandTypeMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(landTypeMasterRoute)],
  exports: [RouterModule],
})
export class LandTypeMasterRoutingModule {}
