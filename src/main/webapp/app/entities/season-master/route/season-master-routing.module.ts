import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SeasonMasterComponent } from '../list/season-master.component';
import { SeasonMasterDetailComponent } from '../detail/season-master-detail.component';
import { SeasonMasterUpdateComponent } from '../update/season-master-update.component';
import { SeasonMasterRoutingResolveService } from './season-master-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const seasonMasterRoute: Routes = [
  {
    path: '',
    component: SeasonMasterComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SeasonMasterDetailComponent,
    resolve: {
      seasonMaster: SeasonMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SeasonMasterUpdateComponent,
    resolve: {
      seasonMaster: SeasonMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SeasonMasterUpdateComponent,
    resolve: {
      seasonMaster: SeasonMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(seasonMasterRoute)],
  exports: [RouterModule],
})
export class SeasonMasterRoutingModule {}
