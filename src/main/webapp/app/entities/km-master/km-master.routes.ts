import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { KmMasterComponent } from './list/km-master.component';
import { KmMasterDetailComponent } from './detail/km-master-detail.component';
import { KmMasterUpdateComponent } from './update/km-master-update.component';
import KmMasterResolve from './route/km-master-routing-resolve.service';

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
      kmMaster: KmMasterResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: KmMasterUpdateComponent,
    resolve: {
      kmMaster: KmMasterResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: KmMasterUpdateComponent,
    resolve: {
      kmMaster: KmMasterResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default kmMasterRoute;
