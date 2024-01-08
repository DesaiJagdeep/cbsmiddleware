import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { FactoryMasterComponent } from '../list/factory-master.component';
import { FactoryMasterDetailComponent } from '../detail/factory-master-detail.component';
import { FactoryMasterUpdateComponent } from '../update/factory-master-update.component';
import { FactoryMasterRoutingResolveService } from './factory-master-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

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
      factoryMaster: FactoryMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FactoryMasterUpdateComponent,
    resolve: {
      factoryMaster: FactoryMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FactoryMasterUpdateComponent,
    resolve: {
      factoryMaster: FactoryMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(factoryMasterRoute)],
  exports: [RouterModule],
})
export class FactoryMasterRoutingModule {}
