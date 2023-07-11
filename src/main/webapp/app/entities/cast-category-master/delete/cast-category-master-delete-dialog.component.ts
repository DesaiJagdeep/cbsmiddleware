import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICastCategoryMaster } from '../cast-category-master.model';
import { CastCategoryMasterService } from '../service/cast-category-master.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './cast-category-master-delete-dialog.component.html',
})
export class CastCategoryMasterDeleteDialogComponent {
  castCategoryMaster?: ICastCategoryMaster;

  constructor(protected castCategoryMasterService: CastCategoryMasterService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.castCategoryMasterService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
