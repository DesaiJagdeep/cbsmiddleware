import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICropHangam } from '../crop-hangam.model';
import { CropHangamService } from '../service/crop-hangam.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './crop-hangam-delete-dialog.component.html',
})
export class CropHangamDeleteDialogComponent {
  cropHangam?: ICropHangam;

  constructor(protected cropHangamService: CropHangamService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.cropHangamService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
