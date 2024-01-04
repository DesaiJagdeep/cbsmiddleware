import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { KmDetailsComponent } from './list/km-details.component';
import { KmDetailsDetailComponent } from './detail/km-details-detail.component';
import { KmDetailsUpdateComponent } from './update/km-details-update.component';
import KmDetailsResolve from './route/km-details-routing-resolve.service';

const kmDetailsRoute: Routes = [
  {
    path: '',
    component: KmDetailsComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: KmDetailsDetailComponent,
    resolve: {
      kmDetails: KmDetailsResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: KmDetailsUpdateComponent,
    resolve: {
      kmDetails: KmDetailsResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: KmDetailsUpdateComponent,
    resolve: {
      kmDetails: KmDetailsResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default kmDetailsRoute;
