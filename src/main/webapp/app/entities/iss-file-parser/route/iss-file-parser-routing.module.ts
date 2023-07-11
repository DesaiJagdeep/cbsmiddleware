import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { IssFileParserComponent } from '../list/iss-file-parser.component';
import { IssFileParserDetailComponent } from '../detail/iss-file-parser-detail.component';
import { IssFileParserUpdateComponent } from '../update/iss-file-parser-update.component';
import { IssFileParserRoutingResolveService } from './iss-file-parser-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const issFileParserRoute: Routes = [
  {
    path: '',
    component: IssFileParserComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: IssFileParserDetailComponent,
    resolve: {
      issFileParser: IssFileParserRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: IssFileParserUpdateComponent,
    resolve: {
      issFileParser: IssFileParserRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: IssFileParserUpdateComponent,
    resolve: {
      issFileParser: IssFileParserRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(issFileParserRoute)],
  exports: [RouterModule],
})
export class IssFileParserRoutingModule {}
