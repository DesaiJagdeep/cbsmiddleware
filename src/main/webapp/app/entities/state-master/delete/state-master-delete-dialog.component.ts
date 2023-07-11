import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IStateMaster } from '../state-master.model';
import { StateMasterService } from '../service/state-master.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './state-master-delete-dialog.component.html',
})
export class StateMasterDeleteDialogComponent {
  stateMaster?: IStateMaster;

  constructor(protected stateMasterService: StateMasterService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.stateMasterService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
