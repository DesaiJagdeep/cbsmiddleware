import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IFarmerCategoryMaster } from '../farmer-category-master.model';
import { FarmerCategoryMasterService } from '../service/farmer-category-master.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './farmer-category-master-delete-dialog.component.html',
})
export class FarmerCategoryMasterDeleteDialogComponent {
  farmerCategoryMaster?: IFarmerCategoryMaster;

  constructor(protected farmerCategoryMasterService: FarmerCategoryMasterService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.farmerCategoryMasterService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
