import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPacsMaster } from '../pacs-master.model';
import { PacsMasterService } from '../service/pacs-master.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './pacs-master-delete-dialog.component.html',
})
export class PacsMasterDeleteDialogComponent {
  pacsMaster?: IPacsMaster;

  constructor(protected pacsMasterService: PacsMasterService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.pacsMasterService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
