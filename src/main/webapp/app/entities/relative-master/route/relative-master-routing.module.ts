import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { RelativeMasterComponent } from '../list/relative-master.component';
import { RelativeMasterDetailComponent } from '../detail/relative-master-detail.component';
import { RelativeMasterUpdateComponent } from '../update/relative-master-update.component';
import { RelativeMasterRoutingResolveService } from './relative-master-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const relativeMasterRoute: Routes = [
  {
    path: '',
    component: RelativeMasterComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RelativeMasterDetailComponent,
    resolve: {
      relativeMaster: RelativeMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RelativeMasterUpdateComponent,
    resolve: {
      relativeMaster: RelativeMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RelativeMasterUpdateComponent,
    resolve: {
      relativeMaster: RelativeMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(relativeMasterRoute)],
  exports: [RouterModule],
})
export class RelativeMasterRoutingModule {}
