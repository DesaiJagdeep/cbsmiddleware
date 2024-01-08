import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { KarkhanaVasuliFileComponent } from '../list/karkhana-vasuli-file.component';
import { KarkhanaVasuliFileDetailComponent } from '../detail/karkhana-vasuli-file-detail.component';
import { KarkhanaVasuliFileUpdateComponent } from '../update/karkhana-vasuli-file-update.component';
import { KarkhanaVasuliFileRoutingResolveService } from './karkhana-vasuli-file-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const karkhanaVasuliFileRoute: Routes = [
  {
    path: '',
    component: KarkhanaVasuliFileComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: KarkhanaVasuliFileDetailComponent,
    resolve: {
      karkhanaVasuliFile: KarkhanaVasuliFileRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: KarkhanaVasuliFileUpdateComponent,
    resolve: {
      karkhanaVasuliFile: KarkhanaVasuliFileRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: KarkhanaVasuliFileUpdateComponent,
    resolve: {
      karkhanaVasuliFile: KarkhanaVasuliFileRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(karkhanaVasuliFileRoute)],
  exports: [RouterModule],
})
export class KarkhanaVasuliFileRoutingModule {}
