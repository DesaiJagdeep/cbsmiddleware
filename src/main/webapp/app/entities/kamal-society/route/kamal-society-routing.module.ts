import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { KamalSocietyComponent } from '../list/kamal-society.component';
import { KamalSocietyDetailComponent } from '../detail/kamal-society-detail.component';
import { KamalSocietyUpdateComponent } from '../update/kamal-society-update.component';
import { KamalSocietyRoutingResolveService } from './kamal-society-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const kamalSocietyRoute: Routes = [
  {
    path: '',
    component: KamalSocietyComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: KamalSocietyDetailComponent,
    resolve: {
      kamalSociety: KamalSocietyRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: KamalSocietyUpdateComponent,
    resolve: {
      kamalSociety: KamalSocietyRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: KamalSocietyUpdateComponent,
    resolve: {
      kamalSociety: KamalSocietyRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(kamalSocietyRoute)],
  exports: [RouterModule],
})
export class KamalSocietyRoutingModule {}
