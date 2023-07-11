import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICategoryMaster } from '../category-master.model';
import { CategoryMasterService } from '../service/category-master.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './category-master-delete-dialog.component.html',
})
export class CategoryMasterDeleteDialogComponent {
  categoryMaster?: ICategoryMaster;

  constructor(protected categoryMasterService: CategoryMasterService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.categoryMasterService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
