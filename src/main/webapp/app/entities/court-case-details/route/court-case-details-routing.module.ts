import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CourtCaseDetailsComponent } from '../list/court-case-details.component';
import { CourtCaseDetailsDetailComponent } from '../detail/court-case-details-detail.component';
import { CourtCaseDetailsUpdateComponent } from '../update/court-case-details-update.component';
import { CourtCaseDetailsRoutingResolveService } from './court-case-details-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const courtCaseDetailsRoute: Routes = [
  {
    path: '',
    component: CourtCaseDetailsComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CourtCaseDetailsDetailComponent,
    resolve: {
      courtCaseDetails: CourtCaseDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CourtCaseDetailsUpdateComponent,
    resolve: {
      courtCaseDetails: CourtCaseDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CourtCaseDetailsUpdateComponent,
    resolve: {
      courtCaseDetails: CourtCaseDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(courtCaseDetailsRoute)],
  exports: [RouterModule],
})
export class CourtCaseDetailsRoutingModule {}
