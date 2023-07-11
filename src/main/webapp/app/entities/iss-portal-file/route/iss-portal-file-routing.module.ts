import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { IssPortalFileComponent } from '../list/iss-portal-file.component';
import { IssPortalFileDetailComponent } from '../detail/iss-portal-file-detail.component';
import { IssPortalFileUpdateComponent } from '../update/iss-portal-file-update.component';
import { IssPortalFileRoutingResolveService } from './iss-portal-file-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const issPortalFileRoute: Routes = [
  {
    path: '',
    component: IssPortalFileComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: IssPortalFileDetailComponent,
    resolve: {
      issPortalFile: IssPortalFileRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: IssPortalFileUpdateComponent,
    resolve: {
      issPortalFile: IssPortalFileRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: IssPortalFileUpdateComponent,
    resolve: {
      issPortalFile: IssPortalFileRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(issPortalFileRoute)],
  exports: [RouterModule],
})
export class IssPortalFileRoutingModule {}
