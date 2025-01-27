import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IFarmerTypeMaster } from '../farmer-type-master.model';
import { FarmerTypeMasterService } from '../service/farmer-type-master.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './farmer-type-master-delete-dialog.component.html',
})
export class FarmerTypeMasterDeleteDialogComponent {
  farmerTypeMaster?: IFarmerTypeMaster;

  constructor(protected farmerTypeMasterService: FarmerTypeMasterService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.farmerTypeMasterService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
