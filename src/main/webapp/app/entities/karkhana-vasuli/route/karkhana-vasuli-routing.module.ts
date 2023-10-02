import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { KarkhanaVasuliComponent } from '../list/karkhana-vasuli.component';
import { KarkhanaVasuliDetailComponent } from '../detail/karkhana-vasuli-detail.component';
import { KarkhanaVasuliUpdateComponent } from '../update/karkhana-vasuli-update.component';
import { KarkhanaVasuliRoutingResolveService } from './karkhana-vasuli-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const karkhanaVasuliRoute: Routes = [
  {
    path: '',
    component: KarkhanaVasuliComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: KarkhanaVasuliDetailComponent,
    resolve: {
      karkhanaVasuli: KarkhanaVasuliRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: KarkhanaVasuliUpdateComponent,
    resolve: {
      karkhanaVasuli: KarkhanaVasuliRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: KarkhanaVasuliUpdateComponent,
    resolve: {
      karkhanaVasuli: KarkhanaVasuliRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(karkhanaVasuliRoute)],
  exports: [RouterModule],
})
export class KarkhanaVasuliRoutingModule {}
