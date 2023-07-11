import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CategoryMasterComponent } from '../list/category-master.component';
import { CategoryMasterDetailComponent } from '../detail/category-master-detail.component';
import { CategoryMasterUpdateComponent } from '../update/category-master-update.component';
import { CategoryMasterRoutingResolveService } from './category-master-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const categoryMasterRoute: Routes = [
  {
    path: '',
    component: CategoryMasterComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CategoryMasterDetailComponent,
    resolve: {
      categoryMaster: CategoryMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CategoryMasterUpdateComponent,
    resolve: {
      categoryMaster: CategoryMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CategoryMasterUpdateComponent,
    resolve: {
      categoryMaster: CategoryMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(categoryMasterRoute)],
  exports: [RouterModule],
})
export class CategoryMasterRoutingModule {}
