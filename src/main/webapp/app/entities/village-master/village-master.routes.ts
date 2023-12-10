import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { VillageMasterComponent } from './list/village-master.component';
import { VillageMasterDetailComponent } from './detail/village-master-detail.component';
import { VillageMasterUpdateComponent } from './update/village-master-update.component';
import VillageMasterResolve from './route/village-master-routing-resolve.service';

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
      villageMaster: VillageMasterResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: VillageMasterUpdateComponent,
    resolve: {
      villageMaster: VillageMasterResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: VillageMasterUpdateComponent,
    resolve: {
      villageMaster: VillageMasterResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default villageMasterRoute;
