import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { KmMaganiComponent } from '../list/km-magani.component';
import { KmMaganiDetailComponent } from '../detail/km-magani-detail.component';
import { KmMaganiUpdateComponent } from '../update/km-magani-update.component';
import { KmMaganiRoutingResolveService } from './km-magani-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const kmMaganiRoute: Routes = [
  {
    path: '',
    component: KmMaganiComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: KmMaganiDetailComponent,
    resolve: {
      kmMagani: KmMaganiRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: KmMaganiUpdateComponent,
    resolve: {
      kmMagani: KmMaganiRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: KmMaganiUpdateComponent,
    resolve: {
      kmMagani: KmMaganiRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(kmMaganiRoute)],
  exports: [RouterModule],
})
export class KmMaganiRoutingModule {}
