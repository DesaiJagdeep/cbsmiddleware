import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { KmMasterComponent } from '../list/km-master.component';
import { KmMasterDetailComponent } from '../detail/km-master-detail.component';
import { KmMasterUpdateComponent } from '../update/km-master-update.component';
import { KmMasterRoutingResolveService } from './km-master-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const kmMasterRoute: Routes = [
  {
    path: '',
    component: KmMasterComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: KmMasterDetailComponent,
    resolve: {
      kmMaster: KmMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: KmMasterUpdateComponent,
    resolve: {
      kmMaster: KmMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: KmMasterUpdateComponent,
    resolve: {
      kmMaster: KmMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(kmMasterRoute)],
  exports: [RouterModule],
})
export class KmMasterRoutingModule {}
