import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICropCategoryMaster } from '../crop-category-master.model';
import { CropCategoryMasterService } from '../service/crop-category-master.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './crop-category-master-delete-dialog.component.html',
})
export class CropCategoryMasterDeleteDialogComponent {
  cropCategoryMaster?: ICropCategoryMaster;

  constructor(protected cropCategoryMasterService: CropCategoryMasterService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.cropCategoryMasterService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
