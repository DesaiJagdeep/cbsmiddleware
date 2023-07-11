import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IOccupationMaster } from '../occupation-master.model';
import { OccupationMasterService } from '../service/occupation-master.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './occupation-master-delete-dialog.component.html',
})
export class OccupationMasterDeleteDialogComponent {
  occupationMaster?: IOccupationMaster;

  constructor(protected occupationMasterService: OccupationMasterService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.occupationMasterService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
