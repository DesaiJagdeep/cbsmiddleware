import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IBankBranchMaster } from '../bank-branch-master.model';
import { BankBranchMasterService } from '../service/bank-branch-master.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './bank-branch-master-delete-dialog.component.html',
})
export class BankBranchMasterDeleteDialogComponent {
  bankBranchMaster?: IBankBranchMaster;

  constructor(protected bankBranchMasterService: BankBranchMasterService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.bankBranchMasterService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
