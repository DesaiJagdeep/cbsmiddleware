import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { KmDetailsComponent } from '../list/km-details.component';
import { KmDetailsDetailComponent } from '../detail/km-details-detail.component';
import { KmDetailsUpdateComponent } from '../update/km-details-update.component';
import { KmDetailsRoutingResolveService } from './km-details-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

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
      kmDetails: KmDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: KmDetailsUpdateComponent,
    resolve: {
      kmDetails: KmDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: KmDetailsUpdateComponent,
    resolve: {
      kmDetails: KmDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(kmDetailsRoute)],
  exports: [RouterModule],
})
export class KmDetailsRoutingModule {}
