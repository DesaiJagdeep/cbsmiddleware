import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAccountHolderMaster } from '../account-holder-master.model';
import { AccountHolderMasterService } from '../service/account-holder-master.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './account-holder-master-delete-dialog.component.html',
})
export class AccountHolderMasterDeleteDialogComponent {
  accountHolderMaster?: IAccountHolderMaster;

  constructor(protected accountHolderMasterService: AccountHolderMasterService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.accountHolderMasterService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
