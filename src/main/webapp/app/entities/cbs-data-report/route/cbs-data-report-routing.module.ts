import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CbsDataReportComponent } from '../list/cbs-data-report.component';
import { CbsDataReportDetailComponent } from '../detail/cbs-data-report-detail.component';
import { CbsDataReportUpdateComponent } from '../update/cbs-data-report-update.component';
import { CbsDataReportRoutingResolveService } from './cbs-data-report-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const cbsDataReportRoute: Routes = [
  {
    path: '',
    component: CbsDataReportComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CbsDataReportDetailComponent,
    resolve: {
      cbsDataReport: CbsDataReportRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CbsDataReportUpdateComponent,
    resolve: {
      cbsDataReport: CbsDataReportRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CbsDataReportUpdateComponent,
    resolve: {
      cbsDataReport: CbsDataReportRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(cbsDataReportRoute)],
  exports: [RouterModule],
})
export class CbsDataReportRoutingModule {}
