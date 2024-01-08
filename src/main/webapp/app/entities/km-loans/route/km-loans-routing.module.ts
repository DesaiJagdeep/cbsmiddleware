import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { KmLoansComponent } from '../list/km-loans.component';
import { KmLoansDetailComponent } from '../detail/km-loans-detail.component';
import { KmLoansUpdateComponent } from '../update/km-loans-update.component';
import { KmLoansRoutingResolveService } from './km-loans-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const kmLoansRoute: Routes = [
  {
    path: '',
    component: KmLoansComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: KmLoansDetailComponent,
    resolve: {
      kmLoans: KmLoansRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: KmLoansUpdateComponent,
    resolve: {
      kmLoans: KmLoansRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: KmLoansUpdateComponent,
    resolve: {
      kmLoans: KmLoansRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(kmLoansRoute)],
  exports: [RouterModule],
})
export class KmLoansRoutingModule {}
