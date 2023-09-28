import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { KamalMaryadaPatrakComponent } from '../list/kamal-maryada-patrak.component';
import { KamalMaryadaPatrakDetailComponent } from '../detail/kamal-maryada-patrak-detail.component';
import { KamalMaryadaPatrakUpdateComponent } from '../update/kamal-maryada-patrak-update.component';
import { KamalMaryadaPatrakRoutingResolveService } from './kamal-maryada-patrak-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const kamalMaryadaPatrakRoute: Routes = [
  {
    path: '',
    component: KamalMaryadaPatrakComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: KamalMaryadaPatrakDetailComponent,
    resolve: {
      kamalMaryadaPatrak: KamalMaryadaPatrakRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: KamalMaryadaPatrakUpdateComponent,
    resolve: {
      kamalMaryadaPatrak: KamalMaryadaPatrakRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: KamalMaryadaPatrakUpdateComponent,
    resolve: {
      kamalMaryadaPatrak: KamalMaryadaPatrakRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(kamalMaryadaPatrakRoute)],
  exports: [RouterModule],
})
export class KamalMaryadaPatrakRoutingModule {}
