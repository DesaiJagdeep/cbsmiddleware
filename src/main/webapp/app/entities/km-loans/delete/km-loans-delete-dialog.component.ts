import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IKmLoans } from '../km-loans.model';
import { KmLoansService } from '../service/km-loans.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './km-loans-delete-dialog.component.html',
})
export class KmLoansDeleteDialogComponent {
  kmLoans?: IKmLoans;

  constructor(protected kmLoansService: KmLoansService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.kmLoansService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
