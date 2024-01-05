import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { KmLoansComponent } from './list/km-loans.component';
import { KmLoansDetailComponent } from './detail/km-loans-detail.component';
import { KmLoansUpdateComponent } from './update/km-loans-update.component';
import KmLoansResolve from './route/km-loans-routing-resolve.service';

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
      kmLoans: KmLoansResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: KmLoansUpdateComponent,
    resolve: {
      kmLoans: KmLoansResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: KmLoansUpdateComponent,
    resolve: {
      kmLoans: KmLoansResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default kmLoansRoute;
