import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { BatchTransactionComponent } from '../list/batch-transaction.component';
import { BatchTransactionDetailComponent } from '../detail/batch-transaction-detail.component';
import { BatchTransactionUpdateComponent } from '../update/batch-transaction-update.component';
import { BatchTransactionRoutingResolveService } from './batch-transaction-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const batchTransactionRoute: Routes = [
  {
    path: '',
    component: BatchTransactionComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BatchTransactionDetailComponent,
    resolve: {
      batchTransaction: BatchTransactionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BatchTransactionUpdateComponent,
    resolve: {
      batchTransaction: BatchTransactionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BatchTransactionUpdateComponent,
    resolve: {
      batchTransaction: BatchTransactionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(batchTransactionRoute)],
  exports: [RouterModule],
})
export class BatchTransactionRoutingModule {}
