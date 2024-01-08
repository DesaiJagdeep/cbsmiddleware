import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IKmMaster } from '../km-master.model';
import { KmMasterService } from '../service/km-master.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './km-master-delete-dialog.component.html',
})
export class KmMasterDeleteDialogComponent {
  kmMaster?: IKmMaster;

  constructor(protected kmMasterService: KmMasterService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.kmMasterService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
