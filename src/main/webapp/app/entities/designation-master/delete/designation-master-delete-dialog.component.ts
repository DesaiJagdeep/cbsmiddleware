import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDesignationMaster } from '../designation-master.model';
import { DesignationMasterService } from '../service/designation-master.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './designation-master-delete-dialog.component.html',
})
export class DesignationMasterDeleteDialogComponent {
  designationMaster?: IDesignationMaster;

  constructor(protected designationMasterService: DesignationMasterService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.designationMasterService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
