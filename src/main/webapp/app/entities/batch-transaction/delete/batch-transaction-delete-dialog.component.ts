import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IBatchTransaction } from '../batch-transaction.model';
import { BatchTransactionService } from '../service/batch-transaction.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './batch-transaction-delete-dialog.component.html',
})
export class BatchTransactionDeleteDialogComponent {
  batchTransaction?: IBatchTransaction;

  constructor(protected batchTransactionService: BatchTransactionService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.batchTransactionService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
