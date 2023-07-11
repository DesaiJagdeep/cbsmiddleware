import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IRelativeMaster } from '../relative-master.model';
import { RelativeMasterService } from '../service/relative-master.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './relative-master-delete-dialog.component.html',
})
export class RelativeMasterDeleteDialogComponent {
  relativeMaster?: IRelativeMaster;

  constructor(protected relativeMasterService: RelativeMasterService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.relativeMasterService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
