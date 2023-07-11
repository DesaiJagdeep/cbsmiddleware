import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IBankMaster } from '../bank-master.model';
import { BankMasterService } from '../service/bank-master.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './bank-master-delete-dialog.component.html',
})
export class BankMasterDeleteDialogComponent {
  bankMaster?: IBankMaster;

  constructor(protected bankMasterService: BankMasterService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.bankMasterService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
