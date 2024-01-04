import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ICropMaster } from '../crop-master.model';
import { CropMasterService } from '../service/crop-master.service';

@Component({
  standalone: true,
  templateUrl: './crop-master-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class CropMasterDeleteDialogComponent {
  cropMaster?: ICropMaster;

  constructor(
    protected cropMasterService: CropMasterService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.cropMasterService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
