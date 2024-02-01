import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICropRateMaster } from '../crop-rate-master.model';
import { CropRateMasterService } from '../service/crop-rate-master.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './crop-rate-master-delete-dialog.component.html',
})
export class CropRateMasterDeleteDialogComponent {
  cropRateMaster?: ICropRateMaster;

  constructor(protected cropRateMasterService: CropRateMasterService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.cropRateMasterService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
