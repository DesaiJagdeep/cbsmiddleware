import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ILandTypeMaster } from '../land-type-master.model';
import { LandTypeMasterService } from '../service/land-type-master.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './land-type-master-delete-dialog.component.html',
})
export class LandTypeMasterDeleteDialogComponent {
  landTypeMaster?: ILandTypeMaster;

  constructor(protected landTypeMasterService: LandTypeMasterService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.landTypeMasterService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
