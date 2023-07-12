import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { BatchTransactionComponent } from './list/batch-transaction.component';
import { BatchTransactionDetailComponent } from './detail/batch-transaction-detail.component';
import { BatchTransactionUpdateComponent } from './update/batch-transaction-update.component';
import { BatchTransactionDeleteDialogComponent } from './delete/batch-transaction-delete-dialog.component';
import { BatchTransactionRoutingModule } from './route/batch-transaction-routing.module';

@NgModule({
  imports: [SharedModule, BatchTransactionRoutingModule],
  declarations: [
    BatchTransactionComponent,
    BatchTransactionDetailComponent,
    BatchTransactionUpdateComponent,
    BatchTransactionDeleteDialogComponent,
  ],
})
export class BatchTransactionModule {}
