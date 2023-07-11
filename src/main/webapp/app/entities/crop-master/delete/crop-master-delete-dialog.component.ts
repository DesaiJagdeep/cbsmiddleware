import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICropMaster } from '../crop-master.model';
import { CropMasterService } from '../service/crop-master.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './crop-master-delete-dialog.component.html',
})
export class CropMasterDeleteDialogComponent {
  cropMaster?: ICropMaster;

  constructor(protected cropMasterService: CropMasterService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.cropMasterService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
