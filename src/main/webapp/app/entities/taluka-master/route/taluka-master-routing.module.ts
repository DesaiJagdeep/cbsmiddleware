import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TalukaMasterComponent } from '../list/taluka-master.component';
import { TalukaMasterDetailComponent } from '../detail/taluka-master-detail.component';
import { TalukaMasterUpdateComponent } from '../update/taluka-master-update.component';
import { TalukaMasterRoutingResolveService } from './taluka-master-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const talukaMasterRoute: Routes = [
  {
    path: '',
    component: TalukaMasterComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TalukaMasterDetailComponent,
    resolve: {
      talukaMaster: TalukaMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TalukaMasterUpdateComponent,
    resolve: {
      talukaMaster: TalukaMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TalukaMasterUpdateComponent,
    resolve: {
      talukaMaster: TalukaMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(talukaMasterRoute)],
  exports: [RouterModule],
})
export class TalukaMasterRoutingModule {}
