import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { BankMasterComponent } from '../list/bank-master.component';
import { BankMasterDetailComponent } from '../detail/bank-master-detail.component';
import { BankMasterUpdateComponent } from '../update/bank-master-update.component';
import { BankMasterRoutingResolveService } from './bank-master-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const bankMasterRoute: Routes = [
  {
    path: '',
    component: BankMasterComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BankMasterDetailComponent,
    resolve: {
      bankMaster: BankMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BankMasterUpdateComponent,
    resolve: {
      bankMaster: BankMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BankMasterUpdateComponent,
    resolve: {
      bankMaster: BankMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(bankMasterRoute)],
  exports: [RouterModule],
})
export class BankMasterRoutingModule {}
