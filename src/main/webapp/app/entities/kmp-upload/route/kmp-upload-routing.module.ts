import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { KMPUploadComponent } from '../list/kmp-upload.component';
import { KMPUploadDetailComponent } from '../detail/kmp-upload-detail.component';
import { KMPUploadUpdateComponent } from '../update/kmp-upload-update.component';
import { KMPUploadRoutingResolveService } from './kmp-upload-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const kMPUploadRoute: Routes = [
  {
    path: '',
    component: KMPUploadComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: KMPUploadDetailComponent,
    resolve: {
      kMPUpload: KMPUploadRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: KMPUploadUpdateComponent,
    resolve: {
      kMPUpload: KMPUploadRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: KMPUploadUpdateComponent,
    resolve: {
      kMPUpload: KMPUploadRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(kMPUploadRoute)],
  exports: [RouterModule],
})
export class KMPUploadRoutingModule {}
