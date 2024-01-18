import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { VillageMasterComponent } from '../list/village-master.component';
import { VillageMasterDetailComponent } from '../detail/village-master-detail.component';
import { VillageMasterUpdateComponent } from '../update/village-master-update.component';
import { VillageMasterRoutingResolveService } from './village-master-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const villageMasterRoute: Routes = [
  {
    path: '',
    component: VillageMasterComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: VillageMasterDetailComponent,
    resolve: {
      villageMaster: VillageMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: VillageMasterUpdateComponent,
    resolve: {
      villageMaster: VillageMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: VillageMasterUpdateComponent,
    resolve: {
      villageMaster: VillageMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(villageMasterRoute)],
  exports: [RouterModule],
})
export class VillageMasterRoutingModule {}
