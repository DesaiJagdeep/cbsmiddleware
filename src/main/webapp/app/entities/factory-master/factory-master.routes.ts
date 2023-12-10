import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { FactoryMasterComponent } from './list/factory-master.component';
import { FactoryMasterDetailComponent } from './detail/factory-master-detail.component';
import { FactoryMasterUpdateComponent } from './update/factory-master-update.component';
import FactoryMasterResolve from './route/factory-master-routing-resolve.service';

const factoryMasterRoute: Routes = [
  {
    path: '',
    component: FactoryMasterComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FactoryMasterDetailComponent,
    resolve: {
      factoryMaster: FactoryMasterResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FactoryMasterUpdateComponent,
    resolve: {
      factoryMaster: FactoryMasterResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FactoryMasterUpdateComponent,
    resolve: {
      factoryMaster: FactoryMasterResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default factoryMasterRoute;
