import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AccountHolderMasterComponent } from '../list/account-holder-master.component';
import { AccountHolderMasterDetailComponent } from '../detail/account-holder-master-detail.component';
import { AccountHolderMasterUpdateComponent } from '../update/account-holder-master-update.component';
import { AccountHolderMasterRoutingResolveService } from './account-holder-master-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const accountHolderMasterRoute: Routes = [
  {
    path: '',
    component: AccountHolderMasterComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AccountHolderMasterDetailComponent,
    resolve: {
      accountHolderMaster: AccountHolderMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AccountHolderMasterUpdateComponent,
    resolve: {
      accountHolderMaster: AccountHolderMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AccountHolderMasterUpdateComponent,
    resolve: {
      accountHolderMaster: AccountHolderMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(accountHolderMasterRoute)],
  exports: [RouterModule],
})
export class AccountHolderMasterRoutingModule {}
