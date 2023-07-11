import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CastCategoryMasterComponent } from '../list/cast-category-master.component';
import { CastCategoryMasterDetailComponent } from '../detail/cast-category-master-detail.component';
import { CastCategoryMasterUpdateComponent } from '../update/cast-category-master-update.component';
import { CastCategoryMasterRoutingResolveService } from './cast-category-master-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const castCategoryMasterRoute: Routes = [
  {
    path: '',
    component: CastCategoryMasterComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CastCategoryMasterDetailComponent,
    resolve: {
      castCategoryMaster: CastCategoryMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CastCategoryMasterUpdateComponent,
    resolve: {
      castCategoryMaster: CastCategoryMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CastCategoryMasterUpdateComponent,
    resolve: {
      castCategoryMaster: CastCategoryMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(castCategoryMasterRoute)],
  exports: [RouterModule],
})
export class CastCategoryMasterRoutingModule {}
