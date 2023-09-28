import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { LoanDemandKMPatrakComponent } from '../list/loan-demand-km-patrak.component';
import { LoanDemandKMPatrakDetailComponent } from '../detail/loan-demand-km-patrak-detail.component';
import { LoanDemandKMPatrakUpdateComponent } from '../update/loan-demand-km-patrak-update.component';
import { LoanDemandKMPatrakRoutingResolveService } from './loan-demand-km-patrak-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const loanDemandKMPatrakRoute: Routes = [
  {
    path: '',
    component: LoanDemandKMPatrakComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: LoanDemandKMPatrakDetailComponent,
    resolve: {
      loanDemandKMPatrak: LoanDemandKMPatrakRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LoanDemandKMPatrakUpdateComponent,
    resolve: {
      loanDemandKMPatrak: LoanDemandKMPatrakRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: LoanDemandKMPatrakUpdateComponent,
    resolve: {
      loanDemandKMPatrak: LoanDemandKMPatrakRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(loanDemandKMPatrakRoute)],
  exports: [RouterModule],
})
export class LoanDemandKMPatrakRoutingModule {}
