import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CourtCaseSettingComponent } from '../list/court-case-setting.component';
import { CourtCaseSettingDetailComponent } from '../detail/court-case-setting-detail.component';
import { CourtCaseSettingUpdateComponent } from '../update/court-case-setting-update.component';
import { CourtCaseSettingRoutingResolveService } from './court-case-setting-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const courtCaseSettingRoute: Routes = [
  {
    path: '',
    component: CourtCaseSettingComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CourtCaseSettingDetailComponent,
    resolve: {
      courtCaseSetting: CourtCaseSettingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CourtCaseSettingUpdateComponent,
    resolve: {
      courtCaseSetting: CourtCaseSettingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CourtCaseSettingUpdateComponent,
    resolve: {
      courtCaseSetting: CourtCaseSettingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(courtCaseSettingRoute)],
  exports: [RouterModule],
})
export class CourtCaseSettingRoutingModule {}
