import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { BankBranchMasterComponent } from '../list/bank-branch-master.component';
import { BankBranchMasterDetailComponent } from '../detail/bank-branch-master-detail.component';
import { BankBranchMasterUpdateComponent } from '../update/bank-branch-master-update.component';
import { BankBranchMasterRoutingResolveService } from './bank-branch-master-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const bankBranchMasterRoute: Routes = [
  {
    path: '',
    component: BankBranchMasterComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BankBranchMasterDetailComponent,
    resolve: {
      bankBranchMaster: BankBranchMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BankBranchMasterUpdateComponent,
    resolve: {
      bankBranchMaster: BankBranchMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BankBranchMasterUpdateComponent,
    resolve: {
      bankBranchMaster: BankBranchMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(bankBranchMasterRoute)],
  exports: [RouterModule],
})
export class BankBranchMasterRoutingModule {}
